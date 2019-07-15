package com.centurylink.github.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.centurylink.github.models.GitHubResponse;

@Service
public class GithubService extends BaseService {

	public GithubService() {
		super();
	}

	public Set<GitHubResponse> retreiveAllFollowersByUserName(String userName) {

		return this.retreiveAllFollowersByUserName(userName, DEFAULT_DEPTH);			

	}

	

	private Set<GitHubResponse> retreiveAllFollowersByUserName(String userName, int depth) {

		Set<GitHubResponse> gitHubResponses = new HashSet<>();

		ResponseEntity<GitHubResponse[]> response = getRestTemplate().exchange(
				BASE_GITHUB_URL + userName + "/followers", HttpMethod.GET, getRequest(), GitHubResponse[].class);

		if (response.getStatusCode() == HttpStatus.OK) {

			GitHubResponse[] responseArray = response.getBody();

			for (int i = 0; i < responseArray.length && gitHubResponses.size() != MAX_SIZE_PER_USER; i++) {

				responseArray[i].setId(responseArray[i].getLogin());
				responseArray[i].setLevel(depth);

				gitHubResponses.add(responseArray[i]);

			}

			findChildren(gitHubResponses);

		}

		return gitHubResponses;
	}


	private void findChildren(Set<GitHubResponse> gitHubResponses) {

		// List of callables to execute in a batch
		final List<Callable<Map<String, Set<GitHubResponse>>>> callables = new ArrayList();
		
		// The map is needed to extract the matching response from the Future reposne
		final Map<String, GitHubResponse> originalMap = new HashMap<>();

		
		for (GitHubResponse g : gitHubResponses) {

			if (g.getLevel() < MAX_DEPTH) {

				Callable<Map<String, Set<GitHubResponse>>> callable = () -> {
					Map<String, Set<GitHubResponse>> temp = new HashMap<>();
					Set<GitHubResponse> resp = this.retreiveAllFollowersByUserName(g.getLogin(), g.getLevel() + 1);
					temp.put(g.getLogin(), resp);
					return temp;
				};

				originalMap.put(g.getLogin(), g);
				callables.add(callable);
			}

		}

		if (callables.size() > 0) {
			ExecutorService executorService = getExecutorService(MAX_SIZE_PER_USER);
			try {

				List<Future<Map<String, Set<GitHubResponse>>>> futures = executorService.invokeAll(callables);
				for (Future<Map<String, Set<GitHubResponse>>> f : futures) {
					if (f.get() != null) {
						Map<String, Set<GitHubResponse>> temp = f.get();
						String key = temp.keySet().iterator().next();
						if (originalMap.get(key).getFollowers() == null) {
							originalMap.get(key).setFollowers(new HashSet<>());
						}
						originalMap.get(key).getFollowers().addAll(temp.get(key));
					}

				}
			} catch (InterruptedException | ExecutionException e) {
				// Ignore the exception and let other threads work.
				e.printStackTrace();
			} finally {
				executorService.shutdown();
			}
		}

	}
}
