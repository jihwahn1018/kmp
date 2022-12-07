package com.tmax.kmp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import com.tmax.superobject.logger.SuperAppDefaultLogger;
import com.tmax.superobject.manager.DbcpConnectionManager;
import com.tmax.superobject.object.AbstractServiceObject;
import com.tmax.superobject.object.BodyObject;
import com.tmax.superobject.object.DefaultBodyObject;
import org.slf4j.Logger;

/*
{
    "header": {
        "targetServiceName": "com.tmax.kmp.service.UpdateApiServerTokenService",
        "messageType": "REQUEST"
    },
    "body": {
        "clusterName" : "your cluster name",
        "token" : "your cluster SA JWT token"
    }
}
 */
public class UpdateApiServerTokenService extends AbstractServiceObject {
    private Logger logger = SuperAppDefaultLogger.getInstance().getLogger(UpdateApiServerTokenService.class.getName());

    @Override
    public void service(BodyObject msg) {
        logger.info("START, " + UpdateApiServerTokenService.class.getName());

        String clusterName;
        String token;

        //parameter check
        BodyObject response = new DefaultBodyObject();
        JsonObject responseBody = new JsonObject();
        response.setJsonObject(responseBody);
        responseBody.addProperty("responseOf", "UpdateApiServerTokenService");

        if(!checkParameter(msg)){
            logger.error("Invalid parameter.");
            responseBody.addProperty("errorMessage", "clusterName and token parameter must be included and not be null");
            setReply(response);
            return;
        }

        JsonObject req = msg.getJsonObject();
        clusterName = req.get("clusterName").getAsString();
        token = req.get("token").getAsString();

        Connection conn = null;
        try {
            conn = DbcpConnectionManager.getInstance().getConnection("kmp");
        }catch (SQLException e) {
            logger.error("DB connection error.");
            throw new RuntimeException(e);
        }

        try {
            //exist check
            if(!checkClusterApiServerExist(conn, clusterName)){
                logger.error("Cluster " + clusterName + " does not exist.");
                responseBody.addProperty("errorMessage", "Cluster " + clusterName + " does not exist.");
                setReply(response);
                return;
            }
            //update
            updateToken(conn, token, clusterName);

        } catch (SQLException e) {
            logger.error("SQL error.");
            e.printStackTrace();
            responseBody.addProperty("errorMessage", "SQL error.");
            setReply(response);
            return;
        }

        //response
        responseBody.addProperty("responseMessage", "Token update is successfully finished.");
        setReply(response);
    }

    private boolean checkParameter(BodyObject msg) {
        if(!msg.getJsonObject().has("clusterName") || !msg.getJsonObject().has("token")){
            return false;
        }
        
        if (msg.getJsonObject().get("clusterName").isJsonNull() || msg.getJsonObject().get("token").isJsonNull()){
            return false;
        } 
        return true;
    }

    private boolean checkClusterApiServerExist(Connection conn, String clusterName) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM CLUSTERS WHERE CLUSTER_NAME=?");
        pstmt.setString(1, clusterName);
        ResultSet res = pstmt.executeQuery();
        if(res.next()) {
            return true;
        }

        return false;
    }

    private void updateToken(Connection conn, String token, String clusterName) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("UPDATE CLUSTERS SET TOKEN = ? WHERE CLUSTER_NAME=?");
        pstmt.setString(1, token);
        pstmt.setString(2, clusterName);

        pstmt.executeQuery();
    }

    @Override
    public void completeService() {
        logger.info("FINISH, " + UpdateApiServerTokenService.class.getName());
    }
}
