package com.it.dnc.inspy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mmatsumoto on 18/07/16.
 */
public class JsonUtil {
	
	private static Logger logger = Logger.getLogger(JsonUtil.class.getName());
	private static ObjectMapper mapper = new ObjectMapper();

	private JsonUtil(){
		
	}
    
    public static String objectToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
        	logger.log(Level.SEVERE, e.getMessage(), e );
        	return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz)  {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e );

            return null;
        }
    }
    public static <T> T jsonToObject(String json, TypeReference reference)  {
        try {
            return mapper.readValue(json, reference);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e );
            return null;
        }
    }

    public static <T> T tryConvertJsonToObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
        	logger.log(Level.SEVERE, e.getMessage(), e );
            return null;
        }
    }

    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz) );
        } catch (IOException e) {
        	logger.log(Level.SEVERE, e.getMessage(), e );
        	return new ArrayList<>();
		}
    }

    /*public static String appendParametros(String parametrosOperadora, String addValor) {
        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
        Map<String, String> params = null;
        if(!StringUtil.isEmpty(parametrosOperadora)){
            params = jsonToObject(parametrosOperadora, typeRef);
        }else{
            params = new HashMap<>();
        }

        Map<String, String> addMap = jsonToObject(addValor, typeRef);
        params.putAll(addMap);
        return objectToJson(params);
    }*/

   /* public static String appendParametros(String parametrosOperadora, Map<String, String> addMap) {
        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};

        Map<String, String> params = null;
        if(!StringUtil.isEmpty(parametrosOperadora)){
            params = jsonToObject(parametrosOperadora, typeRef);
        }else{
            params = new HashMap<>();
        }

        params.putAll(addMap);
        return objectToJson(params);
    }*/

}
