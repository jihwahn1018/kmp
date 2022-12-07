/*
 *  https://github.com/kubernetes-client/java
 */
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
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.google.gson.JsonObject;
import com.tmax.kmp.constants.ApiListConstants;
import com.tmax.superobject.logger.SuperAppDefaultLogger;
import com.tmax.superobject.manager.DbcpConnectionManager;
import com.tmax.superobject.object.AbstractServiceObject;
import com.tmax.superobject.object.BodyObject;
import com.tmax.superobject.object.DefaultBodyObject;
import org.slf4j.Logger;

import com.tmax.kmp.constants.CertConstants;
/*
{
    "header": {
        "targetServiceName": "com.tmax.kmp.service.KubernetesApiService",
        "messageType": "REQUEST"
    },
    "body": {
        "clusterName" : "your cluster name",
        "apiServerIp" : "your cluster's kube-apiserver ip address",
        "apiName" : "api name to request",
        "postBody" : "api request's body" // optional
    }
}
 */

public class KubernetesApiService extends AbstractServiceObject{
    private String CLUSTER_NAME;
    private String KUBE_APISERVER_IP;
    private final Integer KUBE_APISERVER_PORT = 6443;
    private String API_NAME;
    private String POST_BODY;
    private Logger logger = SuperAppDefaultLogger.getInstance().getLogger(KubernetesApiService.class.getName());
    private String apiMethod;
    private String apiUrl;
    private String token;

    @Override
    public void service(BodyObject msg) {
        logger.info("START, " + KubernetesApiService.class.getName());

        // String clusterName;
        // String kubeApiServerIp;
        // String apiName;
        // String postBody;
        // String apiMethod;
        // String apiUrl;
        // String token;
        // TODO url para check

        // para check
        BodyObject response = new DefaultBodyObject();
        JsonObject responseBody = new JsonObject();
        response.setJsonObject(responseBody);
        responseBody.addProperty("responseOf", "KubernetesApiService");

        if(!checkParameter(msg)){
            logger.error("Invalid parameter.");
            responseBody.addProperty("errorMessage", "clusterName, apiServerIp, and apiName parameter must be included and not be null");
            setReply(response);
            return;
        }
        // get req and make cluster id
        JsonObject req = msg.getJsonObject();
        CLUSTER_NAME = req.get("clusterName").getAsString();
        KUBE_APISERVER_IP = req.get("apiServerIp").getAsString();
        API_NAME = req.get("apiName").getAsString();

        // cluster api exist check
        if(!checkApiName()){
            logger.error("Invalid API.");
            responseBody.addProperty("errorMessage", "Invalid API. Please check your API name.");
            setReply(response);
            return;
        }
        getApiUrlAndMethod();

        // check api body
        if(!checkApiBody(msg)){
            logger.error("Invalid paramter.");
            responseBody.addProperty("errorMessage", API_NAME + " requires postBody parameter.");
            setReply(response);
            return;
        }
        
        
        // make request url
        createRequestUrl(req);

        Connection conn = null;
        try {
            conn = DbcpConnectionManager.getInstance().getConnection("kmp");
        }catch (SQLException e) {
            logger.error("DB connection error.");
            throw new RuntimeException(e);
        }
        // get token
        getToken(conn);

        // request
        if(!requestApiServer(responseBody)){
            logger.error("Request paramter.");
            responseBody.addProperty("errorMessage", "Request error.");
            setReply(response);
            return;
        }

        // response
        // responseBody.addProperty("responseMessage", "Api request is successfully finished.");
        setReply(response);
        
    }
    
    
    @Override
    public void completeService() {
        logger.info("FINISH, " + KubernetesApiService.class.getName());
    }

    private boolean checkParameter(BodyObject msg) {
        if(!msg.getJsonObject().has("clusterName") || !msg.getJsonObject().has("apiServerIp")
                || !msg.getJsonObject().has("apiName")){
            return false;
        }
        
        if (msg.getJsonObject().get("clusterName").isJsonNull() || msg.getJsonObject().get("apiServerIp").isJsonNull()
                || msg.getJsonObject().get("apiName").isJsonNull()){
            return false;
        } 
        return true;
    }
    
    private boolean checkApiName(){
        return ApiListConstants.CORE_API_LIST.keySet().contains(API_NAME);
    }

    private void getApiUrlAndMethod(){
        String apiValue = ApiListConstants.CORE_API_LIST.get(API_NAME);
        String[] values = apiValue.split(" ");
        apiMethod = values[0];
        apiUrl = values[1];
    }

    private boolean checkApiBody(BodyObject msg){
        if(apiMethod.equals("POST") || apiMethod.equals("PATCH") || apiMethod.equals("PUT")){
            if(!msg.getJsonObject().has("postBody")){
                return false;
            }
            if (msg.getJsonObject().get("postBody").isJsonNull()){
                return false;
            }
            JsonObject req = msg.getJsonObject();
            POST_BODY = req.get("postBody").getAsString();
        }
        return true;
    }

    private String createRequestUrl(JsonObject req){
        Pattern p = Pattern.compile("\\{(.*?)\\}");
        Matcher m = p.matcher(apiUrl);
        String parameter;

        //make url
        apiUrl = "https://" + KUBE_APISERVER_IP + ":" + KUBE_APISERVER_PORT + apiUrl;
        // System.out.println("url : " + apiUrl);
        // System.out.println("url make");

        while(m.find()){
            parameter = req.get(m.group(1)).getAsString();
            // System.out.println("m.group(1):" + m.group(1));
            // System.out.println("parameter:" + parameter);
            //responseBody.addProperty("Parameter" + Integer.toString(parameterNum), m.group(1));
            //Parameters.put(m.group(1), parameter);

            apiUrl = apiUrl.replace("{" + m.group(1) + "}", parameter);
        }
        // System.out.println("url : " + apiUrl);
        // System.out.println("method : " + apiMethod);

        return apiUrl;
    }
    private boolean getToken(Connection conn){
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT TOKEN FROM CLUSTERS WHERE CLUSTER_NAME=? AND KUBE_APISERVER_IP=?");
            pstmt.setString(1, CLUSTER_NAME);
            pstmt.setString(2, KUBE_APISERVER_IP);
            ResultSet res = pstmt.executeQuery();
            if(res.next()) {
                token = res.getString("TOKEN");
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean requestApiServer(JsonObject responseBody){
        try {
            URL url = new URL(apiUrl);          
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
            if("POST".equals(apiMethod) || "PUT".equals(apiMethod) || "PATCH".equals(apiMethod)){
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type","application/yaml");
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(POST_BODY);
                outputStream.flush();
                outputStream.close();
            }

            int responseCode = connection.getResponseCode();
            // System.out.println("response code : " + responseCode);
	    	// System.out.println("response msg : " + connection.getResponseMessage());
            responseBody.addProperty("responseCode", responseCode);
            responseBody.addProperty("responseMessage", connection.getResponseMessage());
            

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
                responseBody.addProperty("apiResult", apiResult);
                return true;
	    	} else {
                return false;
	    	}
        } catch (Exception e) {
            logger.error("Request error.");
            e.printStackTrace();
            responseBody.addProperty("errorMessage", "Request error.");
            return false;
        }

    }
}
