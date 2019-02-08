package com.cerner.gitHub.branchReport.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.cerner.gitHub.branchReport.model.Branch;
import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.model.Repo;
import com.cerner.gitHub.branchReport.model.User;

public class StaleBranchesTest {

	private StaleBranches testStaleBranches;
	private StaleBranches spyStaleBranches;

	private BranchService mockBranchService;
	private BranchDetails mockBranchDetails;

	@Before
	public void setUp() {
		testStaleBranches = new StaleBranches();
		spyStaleBranches = spy(testStaleBranches);

		mockBranchService = mock(BranchService.class);
		mockBranchDetails = mock(BranchDetails.class);

	}

	@Test
	public void StaleBranches_success() throws IOException {

		int stalePeriod = 30;
		
		
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

		when(spyStaleBranches.getBranchService()).thenReturn(mockBranchService);
		when(mockBranchService.branchesUrl(anyString(), any(Organization.class))).thenReturn("branchasJson");
		when(spyStaleBranches.getBranchDetails()).thenReturn(mockBranchDetails);
		when(mockBranchDetails.getBranchInfo(anyString(), anyInt())).thenReturn(mockBranchList);

		Set<String> emailIDs = new HashSet<String>();
		emailIDs.add("anc@abc");
		emailIDs.add("xyz@xyz");
		
		when(mockBranchDetails.getEmailIDs()).thenReturn(emailIDs);
		Organization org = new Organization();
		org.setOrgName("SomeOrgName");
		ArrayList<Repo> repo_StaleBranch = spyStaleBranches.retrieveStaleBranches(mockRepoList, org, stalePeriod);
		
		assertEquals("business_Logic_Module", repo_StaleBranch.get(0).getRepoName());
	}

}