package com.cerner.gitHub.branchReport.service;

import java.io.IOException;

import org.json.JSONException;

import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.rest.QueryGitHub;

/**
 * @author SJ064201
 *
 */
public class RepoService {

	/**
	 * @return Repo JSON Data
	 * @throws JSONException
	 * @throws IOException
	 */
	public String repoUrl(Organization org) throws JSONException, IOException {

		String repoJSONData = getQueryGitHub()
				.retreiveGitHubData(getQueryGitHub().getListReposAPI().concat(org.getOrgName()).concat("/repos"));
		return repoJSONData;

	}

	QueryGitHub getQueryGitHub() {
		return new QueryGitHub();
	}

}
