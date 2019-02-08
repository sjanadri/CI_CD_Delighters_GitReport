package com.cerner.gitHub.branchReport.service;

import java.io.IOException;
import org.json.JSONObject;

import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.rest.QueryGitHub;

public class OrganisationService {
	Organization org = new Organization();

	public Organization orgUrl(String orgName) throws IOException {
		
		String orgDetailsURL = getQueryGitHub().getOrgDetailsApI();

		String orgJSONData = getQueryGitHub().retreiveGitHubData(orgDetailsURL.concat(orgName));

		JSONObject jsonObject = new JSONObject(orgJSONData);
		org.setOrgName(orgName);
		org.setOrgImage(jsonObject.getString("avatar_url"));

		return org;
	}

	QueryGitHub getQueryGitHub() {
		return new QueryGitHub();
	}

}
