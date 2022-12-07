package com.tmax.kmp.service;



import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.Attach;
import io.kubernetes.client.util.Streams;
import io.netty.handler.ssl.SslContextBuilder;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.bouncycastle.jcajce.provider.keystore.PKCS12;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * 쿠버네티스 클러스터 외부의 애플리케이션에서 Java API를 사용하는 방법에 대한 간단한 예
 *
 * <p>이것을 실행하는 가장 쉬운 방법: mvn exec:java
 * -Dexec.mainClass="io.kubernetes.client.examples.KubeConfigFileClientExample"
 *
 */
public class KubeConfigFileClientExample {
    public static void main(String[] args) throws IOException, ApiException {
        System.out.println("test");
    //System.setProperty( "jdk.tls.client.protocols", "TLSv1.2" );

    // KubeConfig의 파일 경로
    //String kubeConfigPath = "C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\libs\\config";
    // String kubeConfigPath = "C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\libs\\kubeconfig";

    
    // // 파일시스템에서 클러스터 외부 구성인 kubeconfig 로드
    // ApiClient client =
    //     ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();

    // // 전역 디폴트 api-client를 위에서 정의한 클러스터 내 클라이언트로 설정
    // Configuration.setDefaultApiClient(client);

    // // CoreV1Api는 전역 구성에서 디폴트 api-client를 로드
    // CoreV1Api api = new CoreV1Api();

    // // CoreV1Api 클라이언트를 호출한다
    // V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
    // System.out.println("Listing all pods: ");
    // for (V1Pod item : list.getItems()) {
    //   System.out.println(item.getMetadata().getName());
    // }
    // String result = api.connectDeleteNamespacedPodProxy("test", "test", null);
    // URL url = new URL("http://www.google.com");
    // URLConnection connection = url.openConnection();
    // //connection.setRequestMethod("GET");

    // try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
    //     String line;
    //     while ((line = in.readLine()) != null) {
    //         System.out.println(line);
    //     }
    // }catch(Exception e) {
    //   e.printStackTrace();

    // }https://192.168.9.123:6443/api/v1/pods
        try {
            // String urlString = "https://172.21.7.2:6443";
            //String urlString = "https://172.21.7.2:6443/api/v1/nodes";
            String urlString = "https://172.21.7.2:6443/api/v1/namespaces";
            //String urlString = "https://172.21.7.5:6443/api/v1/pods";
            //String urlString = "https://google.com";
            String apiMethod = "GET";
            //String apiMethod = "POST";
            //String token = "####"
	    String token = "####"
	    URL url = new URL(urlString);
            String line = null;
	    	InputStream in = null;
	    	BufferedReader reader = null; 
            
            System.out.println("=========================================urltest");

            //HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            System.out.println("==========================================connection : " + connection);


            //System.setProperty( "javax.net.ssl.trustStore", "C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\service");
            System.out.println(System.getProperty( "javax.net.ssl.trustStore"));
            // // Set Hostname verification
	    	// connection.setHostnameVerifier(new HostnameVerifier() {
	    	// 	@Override
	    	// 	public boolean verify(String hostname, SSLSession session) {
	    	// 		// Ignore host name verification. It always returns true.
	    	// 		return true;
	    	// 	}
	    	// });
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    //return new java.security.cert.X509Certificate[] {};
                    return null;
                }
            
                public void checkClientTrusted(X509Certificate[] certs, String authType){
                }
            
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    // try {
                    //     KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    //     String cacertsPath = "C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\constants\\7_2_ca.pem";
                    //     //String cacertsPath = "C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\constants";
                    //     //String cacertsPath = "test";
                    //     trustStore.load(new FileInputStream(cacertsPath), "changeit".toCharArray());
                    //     TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    //     tmf.init(trustStore);
                    //     TrustManager[] tms = tmf.getTrustManagers();
                    //     ((X509TrustManager)tms[0]).checkServerTrusted(certs, authType);
                        
                    // } catch (Exception e) {
                    //     e.printStackTrace();
                    // }
                }
            } };
            //--------------------------------------------------------
            // System.setProperty("javax.net.ssl.keyStore","C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\constants\\7_2_client.p12");
            // System.setProperty("javax.net.ssl.keyStorePassword", "tmax@3");
            // System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
            //---------------------------------------------------------
            // char[] password = "####".toCharArray();
            // FileInputStream fin = new FileInputStream("C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\constants\\7_2_client.p12");
            // KeyStore ks = KeyStore.getInstance("PKCS12");
            // ks.load(fin, password);

            // KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            // kmf.init(ks, password);
            // //SslContext sc = SslContextBuilder.forClient().keyManager(kmf).build();
            // TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            // tmf.init(ks);
            //--------------------------------------------------------- trust manager setting
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            FileInputStream fin = new FileInputStream("C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\constants\\7_2_ca.crt");
            Certificate ca = cf.generateCertificate(fin);
            String keyStoreType = KeyStore.getDefaultType();
            System.out.println("========================="+keyStoreType);
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            //keyStore.load(null, null);
            FileInputStream testin = new FileInputStream("C:\\Users\\tmax\\Desktop\\SAS_Home\\test_keystore");
            keyStore.load(testin, "####".toCharArray());
            keyStore.setCertificateEntry("test", ca);
            FileOutputStream testout = new FileOutputStream("C:\\Users\\tmax\\Desktop\\SAS_Home\\test_keystore");
            keyStore.store(testout,"####".toCharArray());


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            //--------------------------------------------------------client cert key setting
            CertificateFactory client_cf = CertificateFactory.getInstance("X.509");
            FileInputStream client_fin = new FileInputStream("C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\constants\\7_2_client.crt");
            Certificate client_ca = client_cf.generateCertificate(client_fin);
            String client_keyStoreType = KeyStore.getDefaultType();
            System.out.println("========================="+client_keyStoreType);
            KeyStore client_keyStore = KeyStore.getInstance(client_keyStoreType);
            //keyStore.load(null, null);
            FileInputStream client_testin = new FileInputStream("C:\\Users\\tmax\\Desktop\\SAS_Home\\test_keystore");
            client_keyStore.load(client_testin, "####".toCharArray());
            //client_keyStore.setCertificateEntry("test", client_ca);
            Certificate[] client_ca_array = {client_ca};

            // KeyFactory client_keyf = KeyFactory.getInstance("X.509");
            FileInputStream client_key_fin = new FileInputStream("C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\constants\\7_2_client.key");
            // openssl pkcs8 -topk8 -inform PEM -outform DER -in key.pem -out key2.pem -nocrypt 형식에 맞게
            File file = new File("C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\constants\\client3.key");
            
            // Key client_key = client_keyf.generatePublic(null);
            // Key client_key = client_keyf.get
            byte[] keyBytes = Files.readAllBytes(file.toPath());
            //byte[] keyBytes = client_key_fin.readAllBytes();
            //-- by common key
            String temp = new String(keyBytes);
            String header = temp.replace("-----BEGIN PRIVATE KEY-----\r\n", "");
            header = header.replace("-----END PRIVATE KEY-----", "");
            System.out.println("heaeder:" + header);
            byte[] decoded = Base64.getMimeDecoder().decode(header);
            System.out.println("decoded:" + decoded);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            
            //--by der key
            //PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            // X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory client_keyf = KeyFactory.getInstance("RSA");
            PrivateKey key = client_keyf.generatePrivate(spec);
            // PublicKey key = client_keyf.generatePublic(spec);

            client_keyStore.setKeyEntry("test_client_key", key, "####".toCharArray(), client_ca_array);
            FileOutputStream client_testout = new FileOutputStream("C:\\Users\\tmax\\Desktop\\SAS_Home\\test_keystore");
            keyStore.store(client_testout,"####".toCharArray());


            String kmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(kmfAlgorithm);
            kmf.init(client_keyStore, "####".toCharArray());
            //-----------------------------------------client p12
            // FileInputStream client_cert = new FileInputStream("C:\\Users\\tmax\\Desktop\\projects\\k8s-api-service\\src\\main\\java\\com\\tmax\\kmp\\constants\\7_2_client.p12");
            // KeyStore ks = KeyStore.getInstance("PKCS12");
            // ks.load(client_cert, "####".toCharArray());

            // KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            // kmf.init(ks, "####".toCharArray());



            //---------------------------------------------------------
            SSLContext sc = SSLContext.getInstance("TLS");
            //sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new java.security.SecureRandom());
            sc.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());
            //sc.init(null, trustAllCerts, new java.security.SecureRandom());
            //sc.init(null,null,new java.security.SecureRandom());
            //HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            //----------------------------------------
            connection.setSSLSocketFactory(sc.getSocketFactory());
            System.out.println("ssltest");

            connection.setDoInput(true);// Caches setting
	    	connection.setUseCaches(false);

	    	// Read Timeout Setting
	    	connection.setReadTimeout(1000);
	    	// Connection Timeout setting
	    	connection.setConnectTimeout(1000);

            connection.setRequestMethod(apiMethod);
            connection.setRequestProperty("Authorization","Bearer " + token);
            

            if("POST".equals(apiMethod) || "PUT".equals(apiMethod) || "PATCH".equals(apiMethod)){
                connection.setDoOutput(true);
                //connection.setRequestProperty("Content-Type","application/json");
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes("test");
                outputStream.flush();
                outputStream.close();
            }

            int responseCode = connection.getResponseCode();
            System.out.println("response code : " + responseCode);
	    	System.out.println("response msg : " + connection.getResponseMessage());
            
            System.out.println("connectiontest");

            // // Connect to host
	    	// connection.connect();
	    	// connection.setInstanceFollowRedirects(true);
            
            System.out.println("connectiondone");
        
	    	// Print response from host
	    	if (responseCode == HttpsURLConnection.HTTP_OK) { // 정상 호출 200
	    		in = connection.getInputStream();
	    	} else { // 에러 발생
	    		in = connection.getErrorStream();
	    	}
	    	reader = new BufferedReader(new InputStreamReader(in));
            String result = "";
	    	while ((line = reader.readLine()) != null) {
	    		System.out.printf("%s\n", line);
                result += line;
	    	}

            System.out.println("resourceVersion: "+parse(result));
	    	reader.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    
    }
    private static String parse(String result){
        String resourceVersion;


        JSONObject jo = new JSONObject(result);
        JSONObject jo2 = jo.getJSONObject("metadata");
        resourceVersion = jo2.getString("resourceVersion");
        return resourceVersion;
    }
    // public static String parse(String responseBody) {
    //     JSONArray ja = new JSONArray(responseBody);
    //     for (int i = 0 ; i < ja.length(); i++) {
    //         JSONObject album = ja.getJSONObject(i);			
    //         int userId = album.getInt("userId");
    //         int id = album.getInt("id");
    //         String title = album.getString("title");
    //         System.out.println(id+" "+title+" "+userId);
    //     }
    //     return null;
    // }
}   

