package com.cerner.gitHub.branchReport.model;

/**
 * @author SJ064201
 */
public class Branch {

	private String date;
	private String branchName;
	private User user;
	private boolean flagActive;
	private String timeStamp;
	private String branchLink;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isFlagActive() {
		return flagActive;
	}

	public void setFlagActive(boolean flagActive) {
		this.flagActive = flagActive;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getBranchLink() {
		return branchLink;
	}

	public void setBranchLink(String branchLink) {
		this.branchLink = branchLink;
	}

}
