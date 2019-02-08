package com.cerner.gitHub.branchReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cerner.gitHub.branchReport.eMailModule.MailContent;
import com.cerner.gitHub.branchReport.model.Branch;
import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.model.Repo;
import com.cerner.gitHub.branchReport.model.User;
import com.cerner.gitHub.branchReport.service.BranchDetails;
import com.cerner.gitHub.branchReport.service.BranchService;
import com.cerner.gitHub.branchReport.service.RepoDetails;
import com.cerner.gitHub.branchReport.service.RepoService;
import com.cerner.gitHub.branchReport.service.StaleBranches;
import com.cerner.gitHub.branchReport.service.UserDetails;
import com.cerner.gitHub.branchReport.service.UserService;

public class DisplaySelectionModule {
	final static Logger logger = Logger.getLogger(DisplaySelectionModule.class);
	Scanner sc = new Scanner(System.in);

	public void displayBranchDetails(List<Repo> repoList, Organization org, String author, int stalePeriod) {

		System.out.println("Listing All Stale Branches ");

		try {
			for (Repo repo : repoList) {

				String branchData = getBranchService().branchesUrl(repo.getRepoName(), org);
				ArrayList<Branch> branchList = getBranchDetails().getBranchInfo(branchData, stalePeriod);

				if (!branchList.isEmpty()) {

					logger.info("----------------------Showing Branches for Repo :" + repo.getRepoName()
							+ "----------------");

					for (Branch b : branchList) {

						if (author == null) {
							if (!b.isFlagActive())
								getDisplayModule().displayBranch(b);
						} else if (!b.isFlagActive() && author.equals(b.getUser().getLogin())) {

							getDisplayModule().displayBranch(b);
						}
					}
				}
				logger.info("------------------------------------------------------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Repo> displayRepoList(Organization org) {

		ArrayList<Repo> repoList = new ArrayList<Repo>();

		try {

			logger.info("List All Projects(Repos)");
			String repoData = getRepoService().repoUrl(org);
			repoList = getRepoDetails().getRepoInfo(repoData);
			getDisplayModule().displayRepo(repoList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return repoList;
	}

	public String staleBranchesList(List<Repo> repoList, Organization org, int stalePeriod) {

		String mailBody = null;

		try {
			System.out.println(
					"***************************************From Main - For Mailing******************************************");

			ArrayList<Repo> repoStaleBranchList = getStaleBranches().retrieveStaleBranches(repoList, org, stalePeriod);

			if (repoStaleBranchList.isEmpty()) {
				mailBody = null;
			} else {
				mailBody = getMailContent().prepareEmailContent(repoStaleBranchList, org, stalePeriod);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return mailBody;
	}

	public void displayUsersList(List<Repo> repoList, Organization org) {

		try {
			for (Repo repo : repoList) {
				logger.info("*************** Contributors to Repo : " + repo.getRepoName() + "************");
				String userData = getUserService().userUrl(repo.getRepoName(), org);
				ArrayList<User> userList = getUserDetails().getUserInfo(userData);
				getDisplayModule().displayUsers(userList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BranchService getBranchService() {
		return new BranchService();
	}

	public BranchDetails getBranchDetails() {
		return new BranchDetails();
	}

	public UserService getUserService() {
		return new UserService();
	}

	public UserDetails getUserDetails() {
		return new UserDetails();
	}

	public DisplayModule getDisplayModule() {
		return new DisplayModule();
	}

	public RepoService getRepoService() {
		return new RepoService();
	}

	public RepoDetails getRepoDetails() {
		return new RepoDetails();
	}

	public StaleBranches getStaleBranches() {
		return new StaleBranches();
	}

	public MailContent getMailContent() {
		return new MailContent();
	}
}
