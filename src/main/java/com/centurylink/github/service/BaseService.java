package com.centurylink.github.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BaseService {

	final protected int DEFAULT_DEPTH = 1;
	final protected int MAX_DEPTH = 3;
	final protected int MAX_SIZE_PER_USER = 5;

	// Github API restricts call without token. Hence the token.
	final protected String AUth_TOKEN = "token d2297d4f1ad473410a233e18a8eb5782e3d270d1";
	protected HttpEntity<String> request;

	protected final String BASE_GITHUB_URL = "https://api.github.com/users/";

	public BaseService() {

	}

	protected ExecutorService getExecutorService(int poolSize) {
		return Executors.newFixedThreadPool(poolSize);
	}

	protected HttpEntity<String> getRequest() {

		if (this.request == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", AUth_TOKEN);
			request = new HttpEntity<String>(headers);
		}

		return request;
	}

	protected RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
