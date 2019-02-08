package com.cerner.gitHub.branchReport.eMailModule;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.cerner.gitHub.branchReport.model.Branch;
import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.model.Repo;
import com.cerner.gitHub.branchReport.model.User;

public class MailContentTest {

	MailContent testMailContent;
	MailContent spyMailContent;

	@Before
	public void setUp() {
		testMailContent = new MailContent();
		spyMailContent = spy(testMailContent);

	}

	@Test
	public void prepareEmailContent_success() {

		User mockUser1 = new User();
		mockUser1.setLogin("bc015394");

		User mockUser2 = new User();
		mockUser2.setLogin("OtherUser");

		Branch mockStaleBranch = new Branch();
		mockStaleBranch.setBranchName("BugFix_Solution");
		mockStaleBranch.setFlagActive(false);
		mockStaleBranch.setUser(mockUser1);

		Branch mockActiveBranch = new Branch();
		mockActiveBranch.setBranchName("Calculate_Age");
		mockActiveBranch.setFlagActive(true);
		mockActiveBranch.setUser(mockUser2);

		Branch mockStaleBranch2 = new Branch();
		mockStaleBranch2.setBranchName("BugFix_Solutions");
		mockStaleBranch2.setFlagActive(false);
		mockStaleBranch2.setUser(mockUser1);

		ArrayList<Branch> mockStaleBranchList = new ArrayList<Branch>();
		mockStaleBranchList.add(mockStaleBranch);
		mockStaleBranchList.add(mockStaleBranch2);

		Repo repo1 = new Repo();
		repo1.setRepoName("business_Logic_Module");
		repo1.setStaleBranchList(mockStaleBranchList);

		ArrayList<Repo> mockRepoList = new ArrayList<Repo>();
		mockRepoList.add(repo1);

		ArrayList<Branch> mockBranchList = new ArrayList<Branch>();
		mockBranchList.add(mockActiveBranch);
		mockBranchList.add(mockStaleBranch);
		mockBranchList.add(mockStaleBranch2);

		Organization org = new Organization();
		org.setOrgName("Installation-Solutions");
		int stalePeriod = 40;

		spyMailContent.prepareEmailContent(mockRepoList, org, stalePeriod);
		verify(spyMailContent, times(1)).prepareEmailContent(mockRepoList, org, stalePeriod);
	}

}
