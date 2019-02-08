package com.cerner.gitHub.branchReport.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author SJ064201
 */
public class QueryGitHub {

	/**
	 * @param url
	 * @return JSON Data
	 * @throws IOException
	 */

	private final String listReposAPI = "https://github.cerner.com/api/v3/users/";
	private final String listUsersAPI = "https://github.cerner.com/api/v3/repos/";
	private final String allBranches_repoAPI = "https://github.cerner.com/api/v3/repos/";
	private final String orgDetailsApI = "https://github.cerner.com/api/v3/users/";

	public String retreiveGitHubData(String url) throws IOException {

		URL mainUrl = new URL(url);
		HttpsURLConnection urlConn = (HttpsURLConnection) mainUrl.openConnection();
		urlConn.setRequestProperty("Content-type", "application/json");
		urlConn.setRequestProperty("accept", "application/json");
		urlConn.setRequestMethod("GET");
		urlConn.setAllowUserInteraction(false);
		InputStream inStream = urlConn.getInputStream();
		@SuppressWarnings("resource")
		String data = new Scanner(inStream).useDelimiter("\\A").next();
		return data;

	}

	public String getListReposAPI() {
		return listReposAPI;
	}

	public String getListUsersAPI() {
		return listUsersAPI;
	}

	public String getOrgDetailsApI() {
		return orgDetailsApI;
	}

	public String getAllBranches_repoAPI() {
		return allBranches_repoAPI;
	}

}
