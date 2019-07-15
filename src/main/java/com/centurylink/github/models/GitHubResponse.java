package com.centurylink.github.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class GitHubResponse {

	@JsonProperty(access = Access.WRITE_ONLY)
	private String login;
	
	@JsonIgnore
	private int level;
	
	private String id;
	
	private Set<GitHubResponse> followers;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	
	public Set<GitHubResponse> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<GitHubResponse> followers) {
		this.followers = followers;
	}

	@Override
	public String toString() {
		return "GitHubResponse [login=" + login + "]";
	}
	
	
}
