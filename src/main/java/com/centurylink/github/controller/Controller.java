package com.centurylink.github.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centurylink.github.models.GitHubResponse;
import com.centurylink.github.service.GithubService;

@RestController
@RequestMapping(path = "/api")
public class Controller {

	@Autowired
	GithubService githubService;

	@GetMapping("/{userName}/followers")
	public Set<GitHubResponse> retreiveFollowers(@PathVariable("userName") String userName) {
		 return githubService.retreiveAllFollowersByUserName(userName);
	}

}
