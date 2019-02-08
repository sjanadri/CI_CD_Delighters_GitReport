package com.cerner.gitHub.branchReport.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cerner.gitHub.branchReport.model.Branch;
import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.model.Repo;

public class StaleBranches {

	static Set<String> mailingList = new HashSet<String>();

	public ArrayList<Repo> retrieveStaleBranches(List<Repo> repoList, Organization org, int stalePeriod)
			throws IOException {
		ArrayList<Repo> repoStaleBranches = new ArrayList<Repo>();

		for (Repo repo : repoList) {

			ArrayList<Branch> staleBranchList = new ArrayList<Branch>();
			String branchData = getBranchService().branchesUrl(repo.getRepoName(), org);
			ArrayList<Branch> BranchList = getBranchDetails().getBranchInfo(branchData, stalePeriod);

			Set<String> emailIDs = getBranchDetails().getEmailIDs();
			mailingList.addAll(emailIDs);

			for (Branch b : BranchList) {
				if (!b.isFlagActive())
					staleBranchList.add(b);
			}
			if (!staleBranchList.isEmpty()) {

				repo.setStaleBranchList(staleBranchList);
				repoStaleBranches.add(repo);
			}

		}

		return repoStaleBranches;

	}

	public Set<String> getMailingList() {
		return mailingList;
	}

	public BranchService getBranchService() {
		return new BranchService();
	}

	public BranchDetails getBranchDetails() {
		return new BranchDetails();
	}

}
