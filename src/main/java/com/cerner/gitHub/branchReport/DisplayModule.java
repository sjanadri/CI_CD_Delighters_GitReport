package com.cerner.gitHub.branchReport;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.cerner.gitHub.branchReport.model.Branch;
import com.cerner.gitHub.branchReport.model.Repo;
import com.cerner.gitHub.branchReport.model.User;

public class DisplayModule {

	final static Logger logger = Logger.getLogger(DisplayModule.class);

	public void displayRepo(ArrayList<Repo> repoList) {
		for (Repo repo : repoList) {
			logger.info("Repo Name : " + repo.getRepoName());
		}
	}

	public void displayUsers(ArrayList<User> userList) {
		for (User user : userList) {
			logger.info("User Name : " + user.getLogin());
		}
	}

	public void displayBranch(Branch b) {
		logger.info("Branch Name : " + b.getBranchName());
		logger.info(b.getTimeStamp() + "---- branch Status : " + b.isFlagActive() + "----" + b.getUser().getName()
				+ "----" + b.getDate() + "----" + b.getUser().getEmail() + "----" + b.getUser().getLogin());
		logger.info("");
	}

}
