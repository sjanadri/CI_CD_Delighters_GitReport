package com.cerner.gitHub.branchReport.model;

import java.util.ArrayList;

/**
 * @author SJ064201
 *
 */
public class Repo {

	private String repoName;
	ArrayList<Branch> staleBranchList;

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public ArrayList<Branch> getStaleBranchList() {
		return staleBranchList;
	}

	public void setStaleBranchList(ArrayList<Branch> staleBranchList) {
		this.staleBranchList = staleBranchList;
	}

}
