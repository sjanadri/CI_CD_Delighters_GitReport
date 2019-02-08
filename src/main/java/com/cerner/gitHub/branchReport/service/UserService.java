package com.cerner.gitHub.branchReport.service;

import java.io.IOException;

import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.rest.QueryGitHub;

/**
 * @author SJ064201
 *
 */
public class UserService {
	/**
	 * @param projectName
	 * @return User JSON Data
	 * @throws IOException
	 */
	public String userUrl(String projectName, Organization org) throws IOException {

		String listReposURL = getQueryGitHub().getListUsersAPI();
		String listProjectUsersURL = listReposURL
				.concat(org.getOrgName())
				.concat("/")
				.concat(projectName)
				.concat("/contributors");
		String userJSONData = getQueryGitHub().retreiveGitHubData(listProjectUsersURL);
		return userJSONData;

	}
	
	public QueryGitHub getQueryGitHub() {
		return new QueryGitHub();
	}
	
}
