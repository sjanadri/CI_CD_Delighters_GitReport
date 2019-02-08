package com.cerner.gitHub.branchReport.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cerner.gitHub.branchReport.model.Repo;

/**
 * @author SJ064201
 *
 */
public class RepoDetails {
	ArrayList<Repo> repoList = new ArrayList<Repo>();

	/**
	 * @param repoJSONData
	 * @return List Of Repo Names
	 * @throws JSONException
	 */
	public ArrayList<Repo> getRepoInfo(String repoJSONData) throws JSONException {

		JSONArray jsonArray = new JSONArray(repoJSONData);
		for (int i = 0; i < jsonArray.length(); i++) {
			Repo repo = new Repo();
			JSONObject jsonStatusObject = jsonArray.getJSONObject(i);
			repo.setRepoName(jsonStatusObject.getString("name"));
			repoList.add(repo);
		}
		return repoList;
	}

}
