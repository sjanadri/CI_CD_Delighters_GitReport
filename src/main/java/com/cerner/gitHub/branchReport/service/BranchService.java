package com.cerner.gitHub.branchReport.service;

import java.io.IOException;
import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.rest.QueryGitHub;

/**
 * @author SJ064201
 *
 */
public class BranchService {
	/**
	 * @param ProjectName
	 * @return Lists all Branches in Projects
	 * @throws IOException
	 * 
	 */
	public String branchesUrl(String projectName, Organization org) throws IOException {

		String listBranchesURL = getQueryGitHub().getAllBranches_repoAPI();
		String listAllRepoBranches = listBranchesURL.concat(org.getOrgName()).concat("/").concat(projectName)
				.concat("/branches");
		String branchJSONData = getQueryGitHub().retreiveGitHubData(listAllRepoBranches);
		return branchJSONData;
	}

	public String commitsURL(String commitUrl) throws IOException {
		String commitJSONData = getQueryGitHub().retreiveGitHubData(commitUrl);
		return commitJSONData;
	}

	QueryGitHub getQueryGitHub() {
		return new QueryGitHub();
	}

}
