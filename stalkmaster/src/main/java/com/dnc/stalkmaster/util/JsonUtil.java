package com.dnc.stalkmaster.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	
	private static Logger logger = Logger.getLogger(JsonUtil.class.getName());
	private static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

    private JsonUtil() {}
    
    public static String objectToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
        	logger.error( e.getMessage(), e );
        	return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz)  {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage(), e );

            return null;
        }
    }
    public static <T> T jsonToObject(String json, TypeReference<?> reference)  {
        try {
            return mapper.readValue(json, reference);
        } catch (IOException e) {
            logger.error(e.getMessage(), e );
            return null;
        }
    }

    public static <T> T tryConvertJsonToObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
        	logger.error(e.getMessage(), e );
            return null;
        }
    }

    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz) );
        } catch (IOException e) {
        	logger.error(e.getMessage(), e );
        	return new ArrayList<>();
		}
    }

    public static String appendParametros(String parametrosOperadora, String addValor) {
        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
        Map<String, String> params = null;
        if(!StringUtils.isEmpty(parametrosOperadora)){
            params = jsonToObject(parametrosOperadora, typeRef);
        }else{
            params = new HashMap<>();
        }

        Map<String, String> addMap = jsonToObject(addValor, typeRef);
        params.putAll(addMap);
        return objectToJson(params);
    }

    public static String appendParametros(String parametrosOperadora, Map<String, String> addMap) {
        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};

        Map<String, String> params = null;
        if(!StringUtils.isEmpty(parametrosOperadora)){
            params = jsonToObject(parametrosOperadora, typeRef);
        }else{
            params = new HashMap<>();
        }

        params.putAll(addMap);
        return objectToJson(params);
    }

}
