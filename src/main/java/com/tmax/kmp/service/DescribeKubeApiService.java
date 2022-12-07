package com.tmax.kmp.service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;
import com.tmax.kmp.constants.ApiListConstants;
import com.tmax.superobject.logger.SuperAppDefaultLogger;
import com.tmax.superobject.object.AbstractServiceObject;
import com.tmax.superobject.object.BodyObject;
import com.tmax.superobject.object.DefaultBodyObject;
import org.slf4j.Logger;

/*
{
    "header": {
        "targetServiceName": "com.tmax.kmp.service.DescribeKubeApiService",
        "messageType": "REQUEST"
    },
    "body": {
        "command" : "list"
    }
}
{
    "header": {
        "targetServiceName": "com.tmax.kmp.service.DescribeKubeApiService",
        "messageType": "REQUEST"
    },
    "body": {
        "command" : "describe",
        "apiName" : "api name to describe"
    }
}
 */

public class DescribeKubeApiService extends AbstractServiceObject{
    private Logger logger = SuperAppDefaultLogger.getInstance().getLogger(DescribeKubeApiService.class.getName());


    @Override
    public void service(BodyObject msg) {
        logger.info("START, " + DescribeKubeApiService.class.getName());

        String command;
        String apiName;

        BodyObject response = new DefaultBodyObject();
        JsonObject responseBody = new JsonObject();
        response.setJsonObject(responseBody);
        responseBody.addProperty("responseOf", "DescribeKubeApiService");

        if(!checkParameter(msg)){
            logger.error("Invalid parameter error.");
            responseBody.addProperty("errorMessage", "command parameter must be included and not be null");
            setReply(response);
            return;
        }

        JsonObject request = msg.getJsonObject();
        command = request.get("command").getAsString();

        if("list".equalsIgnoreCase(command)) {
            ArrayList<String> apiList = new ArrayList<String>();
            for (String key : ApiListConstants.CORE_API_LIST.keySet()) {
                apiList.add(key);
            }
            responseBody.addProperty("API list", String.join("\\r\\n", apiList)); 

        } else if ("describe".equalsIgnoreCase(command)) {
            // api Name check
            if(!checkDescribeParameter(msg)){
                logger.error("Invalid parameter error.");
                responseBody.addProperty("errorMessage", "apiName parameter must be included and not be null");
                setReply(response);
                return;
            }
            apiName = request.get("apiName").getAsString();

            if(!checkApiNameValidation(apiName)){
                logger.error("Invalid apiName error.");
                responseBody.addProperty("errorMessage", "Invalid apiName.");
                setReply(response);
                return;
            }
            responseBody.addProperty("API name", apiName);

            setApiParamter(responseBody, apiName);
            
        } else {
            logger.error("Invalid command error");
            responseBody.addProperty("errorMessage", "Invalid command. Command must be either \"list\" or  \"describe\"");
        }
        setReply(response);

    }

    private boolean checkParameter(BodyObject msg) {
        if(!msg.getJsonObject().has("command") || msg.getJsonObject().get("command").isJsonNull()){
            return false;
        }
        
        return true;
    }

    private boolean checkDescribeParameter(BodyObject msg) {
        if(!msg.getJsonObject().has("apiName") || msg.getJsonObject().get("apiName").isJsonNull()){
            return false;
        }
        
        return true;
    }

    private boolean checkApiNameValidation(String apiName) {
        return ApiListConstants.CORE_API_LIST.containsKey(apiName);
    }

    private void setApiParamter(JsonObject responseBody, String apiName){
        String apiDescription = ApiListConstants.CORE_API_LIST.get(apiName);
        String[] apiInfo = apiDescription.split(" ");
        String apiMethod = apiInfo[0];
        String apiPath = apiInfo[1];

        Pattern p = Pattern.compile("\\{(.*?)\\}");
        Matcher m = p.matcher(apiPath);
        int parameterNum = 1;

        while(m.find()){
            responseBody.addProperty("Parameter" + Integer.toString(parameterNum), m.group(1));
            parameterNum += 1;
        }
        if("POST".equals(apiMethod) || "PUT".equals(apiMethod) || "PATCH".equals(apiMethod)){
            responseBody.addProperty("Parameter" + Integer.toString(parameterNum), "postBody");
        }

    }
    
    @Override
    public void completeService() {
        logger.info("FINISH, " + DescribeKubeApiService.class.getName());
    }
}
