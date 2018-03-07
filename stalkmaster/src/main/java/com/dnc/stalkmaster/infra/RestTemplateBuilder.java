package com.dnc.stalkmaster.infra;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public  class RestTemplateBuilder {

	public static RestTemplate buildRestTemplate(int maxConnection, int maxPerRoute, String appKey) {

		//Pool Configuration
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(maxConnection);
		connManager.setDefaultMaxPerRoute(maxPerRoute);
		connManager.closeIdleConnections(300, TimeUnit.SECONDS);

//		CookieStore cookieStore = new MyCookieStore();

		//Build Client
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connManager)
				.setRedirectStrategy(new LaxRedirectStrategy())
//				.setDefaultCookieStore(cookieStore)
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
				.build();

		//Sprinh Http Component
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectionRequestTimeout(300000);
		requestFactory.setConnectTimeout(300000);
		requestFactory.setReadTimeout(300000);

		RestTemplate restTemplate = new RestTemplate(requestFactory);

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        RestTemplateRequestInterceptor restTemplateRequestInterceptor  = new RestTemplateRequestInterceptor();
        interceptors.add(restTemplateRequestInterceptor);
		restTemplate.setInterceptors(interceptors);

		return restTemplate;
		
	}



	
}
