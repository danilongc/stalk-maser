package com.dnc.stalkmaster.infra;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class RestTemplateRequestInterceptor implements ClientHttpRequestInterceptor {

	private Logger logger = LogManager.getLogger(RestTemplateRequestInterceptor.class);

	private final String headerName;

	private final String headerValue;

	public RestTemplateRequestInterceptor(String headerName, String headerValue) {
		this.headerName = headerName;
		this.headerValue = headerValue;
	}


	public RestTemplateRequestInterceptor() {
		this.headerName = null;
		this.headerValue = null;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpRequest wrapper = new HttpRequestWrapper(request);

		if(headerName != null){
			wrapper.getHeaders().set(headerName, headerValue);
		}

		logger.info(String.format("CALL[%s]: ", request.getMethod().toString()) + request.getURI().toString());
		String payload = new String(body);
		if (isNotBlank(payload)) {
			logger.info(payload);
		}
		return execution.execute(wrapper, body);
	}
}