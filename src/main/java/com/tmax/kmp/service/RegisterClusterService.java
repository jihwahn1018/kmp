package com.tmax.kmp.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.google.gson.JsonObject;
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
        "targetServiceName": "com.tmax.kmp.service.RegisterClusterService",
        "messageType": "REQUEST"
    },
    "body": {
        "clusterName" : "your cluster name",
        "apiServerIp" : "your cluster's kube-apiserver ip address",
        "caCert" : "your cluster's ca cert",
        "token" : "your cluster SA JWT token"
    }
}
 */

public class RegisterClusterService extends AbstractServiceObject {
    // private String CLUSTER_ID;
    // private String CLUSTER_NAME;
    // private String KUBE_APISERVER_IP;
    // private String CA_CERT;
    // private String TOKEN;
    private Logger logger = SuperAppDefaultLogger.getInstance().getLogger(RegisterClusterService.class.getName());
    // String apiMethod = "GET";


    @Override
    public void service(BodyObject msg) {
        logger.info("START, " + RegisterClusterService.class.getName());

        String clusterId;
        String clusterName;
        String kubeApiServerIp;
        String caCert;
        String token;
        // String apiMethod = "GET";

        BodyObject response = new DefaultBodyObject();
        JsonObject responseBody = new JsonObject();
        response.setJsonObject(responseBody);
        responseBody.addProperty("responseOf", "RegisterClusterService");
        
        //check cluster name, ip, cacert not null and client cert or token not null
        if(!checkParameter(msg)){
            logger.error("Invalid parameter.");
            responseBody.addProperty("errorMessage", "clusterName, apiServerIp, caCert, and token parameter must be included and not be null");
            setReply(response);
            return;
        }

        // get req and make cluster id
        JsonObject req = msg.getJsonObject();
        clusterId = UUID.randomUUID().toString();
        clusterName = req.get("clusterName").getAsString();
        kubeApiServerIp = req.get("apiServerIp").getAsString();
        caCert = req.get("caCert").getAsString();
        token = req.get("token").getAsString();

        // System.out.println("=========================CA_CERT:" + CA_CERT);
        // System.out.println("=========================TOKEN:" + TOKEN);

        
        Connection conn = null;
        try {
            conn = DbcpConnectionManager.getInstance().getConnection("kmp");
        }catch (SQLException e) {
            logger.error("DB connection error.");
            throw new RuntimeException(e);
        }
        //unique test
        if(!checkClusterApiServerUnique(conn, clusterName, kubeApiServerIp)){
            logger.error("Cluster kube-apiserver unique check fail.");
            responseBody.addProperty("errorMessage", "Cluster with "+ kubeApiServerIp +" is already existed.");
            setReply(response);
            return;
        }


        // connection test
        if(!testConnectionToApiServer(kubeApiServerIp, caCert, token)){
            logger.error("Kube-apiserver Connection test fail.");
            responseBody.addProperty("errorMessage", "Kube-apiserver connection test is failed. Please check your parameters.");
            setReply(response);
            return;
        }

        // register cert and save token
        if(!updateToken(conn, clusterId, clusterName, token, kubeApiServerIp)){
            logger.error("Token update fail.");
            responseBody.addProperty("errorMessage", "Token update fail.");
            setReply(response);
            return;
        }

        if(!registerCert(caCert, clusterName)){
            logger.error("Cert registration fail.");
            responseBody.addProperty("errorMessage", "Cert registration fail.");
            setReply(response);
            return;
        }
        
        //response
        responseBody.addProperty("responseMessage", "Cluster registration is successfully finished.");
        setReply(response);
        
    }
    
    @Override
    public void completeService() {
        logger.info("FINISH, " + RegisterClusterService.class.getName());
    }

    private boolean checkParameter(BodyObject msg) {
        if(!msg.getJsonObject().has("clusterName") || !msg.getJsonObject().has("apiServerIp")
                || !msg.getJsonObject().has("caCert") || !msg.getJsonObject().has("token")){
            return false;
        }
        
        if (msg.getJsonObject().get("clusterName").isJsonNull() || msg.getJsonObject().get("apiServerIp").isJsonNull()
                || msg.getJsonObject().get("caCert").isJsonNull() || msg.getJsonObject().get("token").isJsonNull()){
            return false;
        } 
        return true;
    }

    private boolean checkClusterApiServerUnique(Connection conn, String clusterName, String kubeApiServerIp){
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM CLUSTERS WHERE CLUSTER_NAME=? AND KUBE_APISERVER_IP=?");
            pstmt.setString(1, clusterName);
            pstmt.setString(2, kubeApiServerIp);
            ResultSet res = pstmt.executeQuery();
            if(res.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean testConnectionToApiServer(String kubeApiServerIp, String caCert, String token){
        String apiMethod = "GET";
        String testUrl = createTestUrl(kubeApiServerIp);
        try {
            URL url = new URL(testUrl);          
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream is = new ByteArrayInputStream(caCert.getBytes());
            Certificate ca = cf.generateCertificate(is);
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("test", ca);


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());

            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setRequestMethod(apiMethod);
            connection.setRequestProperty("Authorization","Bearer " + token);

            int responseCode = connection.getResponseCode();

            // InputStream in = null;
            // BufferedReader reader = null;
            // String line = null;

            
            if (responseCode == HttpsURLConnection.HTTP_OK) {
	    		// in = connection.getInputStream();
                // reader = new BufferedReader(new InputStreamReader(in));
                // while ((line = reader.readLine()) != null) {
                //     System.out.printf("%s\n", line);
                // }
                return true;
	    	} else {
	    		// in = connection.getErrorStream();
                // reader = new BufferedReader(new InputStreamReader(in));
	    	    // while ((line = reader.readLine()) != null) {
	    	    // 	System.out.printf("%s\n", line);
	    	    // }
                return false;
	    	}
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }

    private String createTestUrl(String apiServerIp){
        return "https://" + apiServerIp +":6443";
    }

    private Boolean registerCert(String caCert, String clusterName){
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream is = new ByteArrayInputStream(caCert.getBytes());
            Certificate ca = cf.generateCertificate(is);

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);

            FileInputStream Fin = new FileInputStream(CertConstants.DEFAULT_KEYSTORE_PATH);
            // System.out.println("==========" + CertConstants.DEFAULT_KEYSTORE_PATH);
            keyStore.load(Fin, CertConstants.DEFAULT_KEYSTORE_PASSWORD.toCharArray());

            keyStore.setCertificateEntry(clusterName+"KubeClusterCa", ca);

            FileOutputStream Fout = new FileOutputStream(CertConstants.DEFAULT_KEYSTORE_PATH);
            keyStore.store(Fout,CertConstants.DEFAULT_KEYSTORE_PASSWORD.toCharArray());
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    private Boolean updateToken(Connection conn, String clusterId, String clusterName, String token, String kubeApiServerIp){
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO CLUSTERS (ID, CLUSTER_NAME, TOKEN, KUBE_APISERVER_IP) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, clusterId);
            pstmt.setString(2, clusterName);
            pstmt.setString(3, token);
            pstmt.setString(4, kubeApiServerIp);
            pstmt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
