package com.tmax.kmp.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.json.JSONObject;
import org.slf4j.Logger;

import com.google.gson.JsonObject;
import com.tmax.superobject.manager.DbcpConnectionManager;
import com.tmax.superobject.object.AbstractServiceObject;
import com.tmax.superobject.object.DefaultBodyObject;
import com.tmax.superobject.object.BodyObject;
import com.tmax.superobject.logger.SuperAppDefaultLogger;
import com.tmax.superobject.object.AbstractServiceObject;
import com.tmax.superobject.object.BodyObject;

import com.tmax.kmp.constants.CertConstants;

/*
{
    "header": {
        "targetServiceName": "com.tmax.kmp.service.MonitorKubeResourceService",
        "messageType": "REQUEST"
    },
    "body": {
        "clusterName" : "your cluster name",
        "resourceName" : "resource to monitor"
    }
}
 */
public class MonitorKubeResourceService extends AbstractServiceObject {
    private Logger logger = SuperAppDefaultLogger.getInstance().getLogger(MonitorKubeResourceService.class.getName());
    
    @Override
    public void service(BodyObject msg) {
        logger.info("START, " + MonitorKubeResourceService.class.getName());

        String clusterName;
        String resourceName;
        String kubeApiServerIp;
        String token;
        String resourceVersion;

        BodyObject response = new DefaultBodyObject();
        JsonObject responseBody = new JsonObject();
        response.setJsonObject(responseBody);
        responseBody.addProperty("responseOf", "SetMacPoolService");

        if(!checkParameter(msg)){
            logger.error("Invalid parameter.");
            responseBody.addProperty("errorMessage", "clusterName and resourceName parameter must be included and not be null");
            setReply(response);
            return;
        }
        JsonObject req = msg.getJsonObject();
        clusterName = req.get("clusterName").getAsString();
        resourceName = req.get("resourceName").getAsString();

        Connection conn = null;
        try {
            conn = DbcpConnectionManager.getInstance().getConnection("kmp");
        }catch (SQLException e) {
            logger.error("DB connection error.");
            throw new RuntimeException(e);
        }
        // get cluster info
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM CLUSTERS WHERE CLUSTER_NAME = ?");
            pstmt.setString(1, clusterName);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
    
            kubeApiServerIp = rs.getString("KUBE_APISERVER_IP");
            token = rs.getString("TOKEN");
        } catch (SQLException e) {
            logger.error("SQL error is occurred.");
            e.printStackTrace();
            responseBody.addProperty("errorMessage", "SQL error.");
            setReply(response);
            return;
        }
        //get resourceVersion
        resourceVersion = getResourceVersion(resourceName, kubeApiServerIp, token);
        if(!resourceVersion.isEmpty()) {
            // start monitoirng
            startResourceWatch(resourceName, kubeApiServerIp, token, resourceVersion);
        }

        responseBody.addProperty("responseMessage", "Monitoring " + resourceName + " start.");
        setReply(response);
        
    }

    private boolean checkParameter(BodyObject msg) {
        if(!msg.getJsonObject().has("resourceName") || !msg.getJsonObject().has("clusterName")){
            return false;
        }
        
        if (msg.getJsonObject().get("resourceName").isJsonNull() || msg.getJsonObject().get("clusterName").isJsonNull()){
            return false;
        } 
        return true;
    }

    private String getResourceVersion(String resourceName, String kubeApiServerIp, String token){
        String apiMethod = "GET";
        String requesthURL = createRequestUrl(resourceName, kubeApiServerIp);
        try {
            URL url = new URL(requesthURL);          
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            FileInputStream Fin = new FileInputStream(CertConstants.DEFAULT_KEYSTORE_PATH);
            keyStore.load(Fin, CertConstants.DEFAULT_KEYSTORE_PASSWORD.toCharArray());


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());

            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setRequestMethod(apiMethod);
            connection.setRequestProperty("Authorization","Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("response code : " + responseCode);
	    	System.out.println("response msg : " + connection.getResponseMessage());
            // responseBody.addProperty("responseCode", responseCode);
            // responseBody.addProperty("responseMessage", connection.getResponseMessage());
            

            InputStream in = null;
            BufferedReader reader = null;
            String line = null;

            
            if (200 <= responseCode && responseCode < 300) {
                String apiResult = "";
                in = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                while ((line = reader.readLine()) != null) {
                    // System.out.printf("%s\n", line);
                    apiResult += line;
                }
                // responseBody.addProperty("apiResult", apiResult);
                return parseResourceVersion(apiResult);
	    	} else {
                return null;
	    	}
        } catch (Exception e) {
            logger.error("Request error.");
            e.printStackTrace();
            // responseBody.addProperty("errorMessage", "Request error.");
            return null;
        }
        
    }

    private boolean startResourceWatch(String resourceName, String kubeApiServerIp, String token, String resourceVersion){
        String apiMethod = "GET";
        String watchURL = createWatchUrl(resourceName, kubeApiServerIp, resourceVersion);
        System.out.println("watchurl : " + watchURL);
        try {
            URL url = new URL(watchURL);          
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            FileInputStream Fin = new FileInputStream(CertConstants.DEFAULT_KEYSTORE_PATH);
            keyStore.load(Fin, CertConstants.DEFAULT_KEYSTORE_PASSWORD.toCharArray());


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());

            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setRequestMethod(apiMethod);
            connection.setRequestProperty("Authorization","Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("response code : " + responseCode);
	    	System.out.println("response msg : " + connection.getResponseMessage());
            // responseBody.addProperty("responseCode", responseCode);
            // responseBody.addProperty("responseMessage", connection.getResponseMessage());
            

            InputStream in = null;
            BufferedReader reader = null;
            String line = null;

            
            if (200 <= responseCode && responseCode < 300) {
                // String apiResult = "";
                in = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                while ((line = reader.readLine()) != null) {
                    System.out.printf("%s\n", line);
                    String apiResult = line;
                    parseResource(apiResult);
                }
                // responseBody.addProperty("apiResult", apiResult);
                // parseResource(apiResult);
                return true;
	    	} else {
                return false;
	    	}
        } catch (Exception e) {
            logger.error("Request error.");
            e.printStackTrace();
            // responseBody.addProperty("errorMessage", "Request error.");
            return false;
        }

    }

    private String createRequestUrl(String resourceName, String apiServerIp){
        return "https://" + apiServerIp +":6443/api/v1/" + resourceName + "s";
    }

    private String createWatchUrl(String resourceName, String apiServerIp, String resourceVersion){
        return "https://" + apiServerIp +":6443/api/v1/" + resourceName + "s?watch=true&resourceversion=" + resourceVersion;
    }
    
    private static String parseResourceVersion(String result){
        String resourceVersion;

        JSONObject jo = new JSONObject(result);
        JSONObject jo2 = jo.getJSONObject("metadata");
        resourceVersion = jo2.getString("resourceVersion");
        return resourceVersion;
    }
    private static void parseResource(String result) {
        String type;
        String kind;
        String name;


        JSONObject jo = new JSONObject(result);
        type = jo.getString("type");
        System.out.println("type:" + type);

        JSONObject jo2 = jo.getJSONObject("object");
        kind = jo2.getString("kind");
        System.out.println("kind:" + kind);


        JSONObject jo3 = jo2.getJSONObject("metadata");
        name = jo3.getString("name");
        System.out.println("name:" + name);

    }

    @Override
    public void completeService() {
        logger.info("FINISH, " + MonitorKubeResourceService.class.getName());
    }

}
