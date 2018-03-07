package com.dnc.stalkmaster.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public abstract class BaseService {

    private Logger logger = LogManager.getLogger(BaseService.class);

    @Autowired
    protected RestTemplate restTemplate;

    @Value("${instagram.endpoint}")
    private String instagramEndPoint;

    public String getInstagramEndPoint() {
        return instagramEndPoint;
    }

    public String getAccessToken(){
        //TODO: Buscar token de forma dinamica
        return "1989964257.227f806.e40115244d8e493087e656bb7d7d0c15";
    }

    public Map getAccessTokenParam(){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", getAccessToken());
        return params;
    }

    public String getUrl(String servicePath){
        return this.getInstagramEndPoint() + servicePath;
    }

    protected abstract HttpHeaders getHeaders();

    protected Object executePost(String url, Object postObject, Class<?> responseEntityClass) {
        return executePostPut(MediaType.APPLICATION_JSON_UTF8_VALUE, HttpMethod.POST, url, postObject, responseEntityClass, null, null);
    }

    protected Object executePost(String url, Object postObject, Class<?> responseEntityClass, Map<String, String> extractHeaders) {
        return executePostPut(MediaType.APPLICATION_JSON_UTF8_VALUE, HttpMethod.POST, url, postObject, responseEntityClass, extractHeaders, null);
    }

    protected Object executePost(String url, Object postObject, Map<String, String> queryParams, Class<?> responseEntityClass) {
        return executePostWithTemplate(url, null, postObject, queryParams, false, responseEntityClass);
    }

    protected Object executePostWithTemplate(String url, Map<String, String> templates, Object postObject,
                                             Map<String, String> queryParams, boolean doubleEncodeParams,
                                             Class<?> responseEntityClass) {

        return executePostPutWithTemplate(HttpMethod.POST, url, templates, postObject, queryParams, doubleEncodeParams, responseEntityClass);
    }

    protected Object executeWithTemplate(HttpMethod method, String url, Object postObject, Object responseEntityClass) {
        return executePostPutWithTemplate(method, url, null, postObject, null, false, responseEntityClass, true, null);
    }


    protected Object executePostPut(String mediaType, HttpMethod method, String url, Object postObject, Class<?> responseEntityClass, Map<String, String> extractHeader, Map<String, String> inputHeaders ) {

        // Definindo Headers
        HttpHeaders headers = getHeaders();
        if(headers == null){
            headers = new HttpHeaders();
        }

        if(inputHeaders != null &&  inputHeaders.size() > 0){
            headers.setAll(inputHeaders);
        }

        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, mediaType);

        // Definir URL
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);

        // Entidade do Request
        HttpEntity<?> entity = new HttpEntity<>(postObject, headers);

        // Request - POST

        HttpEntity<?> restResponse = restTemplate.exchange(urlBuilder.build().encode().toUri(), method, entity, responseEntityClass);


        if (extractHeader != null && extractHeader.size() > 0) {
            extractHeader.keySet().forEach(c -> extractHeader.put(c, restResponse.getHeaders().getFirst(c)));
        }

        return restResponse.getBody();
    }

    protected Object executePostPutWithTemplate(HttpMethod method, String url, Map<String, String> templates,
                                                Object postObject, Map<String, String> queryParams,
                                                boolean doubleEncodeParams, Object responseEntityClass) {

        return executePostPutWithTemplate(method, url, templates,postObject,queryParams,doubleEncodeParams,responseEntityClass, false, null);

    }

    /***
     *
     * @param method
     * @param url
     * @param templates
     * @param postObject
     * @param queryParams
     * @param doubleEncodeParams
     * @param responseEntityClass
     * @param parameterizedTypeReference
     * @param inputHeaders
     * @return
     */
	protected Object executePostPutWithTemplate(HttpMethod method, String url, Map<String, String> templates,
                                                Object postObject, Map<String, String> queryParams, boolean doubleEncodeParams,
                                                Object responseEntityClass, boolean parameterizedTypeReference, Map<String, String> inputHeaders ) {

        // Definindo Headers
        HttpHeaders headers = getHeaders();
        if(headers == null){
            headers = new HttpHeaders();
        }

        if(inputHeaders != null &&  inputHeaders.size() > 0){
            headers.setAll(inputHeaders);
        }

        // Definir parametros do path
        UriComponentsBuilder urlBuilder = null;
        if (templates != null && templates.size() > 0) {
            for (String key : templates.keySet()) {
                url = url.replace(key, templates.get(key));
            }
        }

        // Definir parametros da query string
        if (queryParams != null && queryParams.size() > 0) {
            url = url + "?";
            for (String key : queryParams.keySet()) {
                if (doubleEncodeParams) {

                    try {
                        url = url + key + "=" + URLEncoder.encode(queryParams.get(key), "UTF-8") + "&";
                    }catch (UnsupportedEncodingException e){
                        logger.error(e.getMessage(), e);
                    }
                } else {
                    url = url + key + "=" + queryParams.get(key) + "&";
                }
            }
        }

        url = StringUtils.removeEnd(url, "&");
        urlBuilder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> entity = new HttpEntity<>(postObject, headers);
        HttpEntity<?> restResponse = null;
        if (parameterizedTypeReference) {
            restResponse = restTemplate.exchange(urlBuilder.build().encode().toUri(), method, entity, (ParameterizedTypeReference<?>) responseEntityClass);
        } else {
            restResponse = restTemplate.exchange(urlBuilder.build().encode().toUri(), method, entity, (Class<?>) responseEntityClass);
        }

        return restResponse.getBody();
    }


    protected Object executeGet(String url, Class<?> responseEntityClass, Map<String, String> headers) {
        return executeGetWithTemplate(url, null, null, responseEntityClass, false, headers);
    }

    protected Object executeGet(String url, Class<?> responseEntityClass) {
        return executeGetWithTemplate(url, null, null, responseEntityClass, false, null);
    }

    protected Object executeGet(String url, ParameterizedTypeReference<?> typeReference) {
        return executeGetWithTemplate(url, null, null, typeReference, true, null);
    }

    protected Object executeGet(String url, ParameterizedTypeReference<?> typeReference, Map<String, String> headers) {
        return executeGetWithTemplate(url, null, null, typeReference, true, headers);
    }

    protected Object executeGetWithTemplate(String url, Map<String, String> templates, Object type) {
        return executeGetWithTemplate(url, templates, null, type, false, null);
    }

	protected Object executeGetWithDecodedParams(String url, Map<String, String> queryParams, Class<?> responseEntityClass) {

		HttpHeaders headers = getHeaders();
		if (headers == null) {
			headers = new HttpHeaders();
		}

		// Definir parametros da query string
		if (queryParams != null && !queryParams.isEmpty()) {
			url = url.concat("?");
			for (String key : queryParams.keySet()) {
				url = url.concat(key) + "=" + queryParams.get(key) + "&";
			}
		}

		url = StringUtils.removeEnd(url, "&");
		String gatewayUrl = UriComponentsBuilder.fromHttpUrl(url).build().toUriString();

		HttpEntity<?> entity = new HttpEntity<>(null, headers);
		HttpEntity<?> restResponse = restTemplate.exchange(gatewayUrl, HttpMethod.GET, entity, (Class<?>) responseEntityClass);
		return restResponse.getBody();
	}

    protected Object executeGetWithTemplate(String url, Map<String, String> templates, Map<String, String> queryParams, Object type, boolean parameterizedTypeReference, Map<String, String> extractHeader) {
        return executeGetWithTemplate(url, templates, queryParams,type,parameterizedTypeReference, extractHeader, getHeaders());
    }

        @SuppressWarnings({"unchecked", "rawtypes"})
    protected Object executeGetWithTemplate(String url, Map<String, String> templates, Map<String, String> queryParams, Object type, boolean parameterizedTypeReference, Map<String, String> extractHeader, HttpHeaders headers) {

        //Definindo headers
        if(headers == null){
            headers = new HttpHeaders();
        }

        // Definir parametros do path
        UriComponentsBuilder urlBuilder = null;
        if (templates != null && templates.size() > 0) {
            for (String key : templates.keySet()) {
                url = url.replace(key, templates.get(key));
            }
        }

        // Definir parametros da query string
        if (queryParams != null && queryParams.size() > 0) {
            url = url + "?";
            for (String key : queryParams.keySet()) {
                url = url + key + "=" + queryParams.get(key) + "&";
            }
        }

        url = StringUtils.removeEnd(url, "&");
        urlBuilder = UriComponentsBuilder.fromHttpUrl(url);

//        headers.add("debug", "1");

        HttpEntity<?> restResponse = null;
        HttpEntity<?> entity = new HttpEntity<>(null, headers);



        if (parameterizedTypeReference) {
            restResponse = restTemplate.exchange(urlBuilder.build().encode().toUri(), HttpMethod.GET, entity, (ParameterizedTypeReference) type);
        } else {
            if(type == null) {
                type = String.class;
            }
            restResponse = restTemplate.exchange(urlBuilder.build().encode().toUri(), HttpMethod.GET, entity, (Class<?>) type);
        }

        HttpHeaders responseHeaders = restResponse.getHeaders();
        if (extractHeader != null && extractHeader.size() > 0) {
            extractHeader.keySet().forEach(c -> extractHeader.put(c, responseHeaders.getFirst(c)));
        }
        return restResponse.getBody();
    }

}
