package com.cerner.GitHubReport;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.gitHub.branchReport.DisplayModule;
import com.cerner.gitHub.branchReport.DisplaySelectionModule;
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

@RunWith(MockitoJUnitRunner.class)
public class DisplaySeletionModuleTest {

	private BranchService mockBranchService;
	private BranchDetails mockBranchDetails;
	private Organization mockOrg;
	private DisplayModule mockDisplayModule;

	private RepoService mockRepoService;
	private RepoDetails mockRepoDetails;

	private UserService mockUserService;
	private UserDetails mockUserDetails;

	private StaleBranches mockStaleBranches;
	private MailContent mockMailContent;

	private DisplaySelectionModule testDisplaySeletionModule;
	private DisplaySelectionModule spyDisplaySeletionModule;

	@Before
	public void setup() throws Exception {
		mockBranchService = mock(BranchService.class);
		mockBranchDetails = mock(BranchDetails.class);
		mockOrg = mock(Organization.class);

		mockRepoService = mock(RepoService.class);
		mockRepoDetails = mock(RepoDetails.class);

		mockUserService = mock(UserService.class);
		mockUserDetails = mock(UserDetails.class);

		mockDisplayModule = mock(DisplayModule.class);
		mockStaleBranches = mock(StaleBranches.class);
		mockMailContent = mock(MailContent.class);

		testDisplaySeletionModule = new DisplaySelectionModule();
		spyDisplaySeletionModule = spy(testDisplaySeletionModule);

	}

	@Test
	public void displayBranchDetails_Success() throws Exception {

		String mockAuthor = "bc015394";
		int mockStalePeriod = 30;
		String mockBranchData = "Some JSON data as String";

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

		when(spyDisplaySeletionModule.getBranchService()).thenReturn(mockBranchService);
		when(mockBranchService.branchesUrl(anyString(), any(Organization.class))).thenReturn(mockBranchData);
		when(spyDisplaySeletionModule.getBranchDetails()).thenReturn(mockBranchDetails);
		when(mockBranchDetails.getBranchInfo(anyString(), anyInt())).thenReturn(mockStaleBranchList);

		when(spyDisplaySeletionModule.getDisplayModule()).thenReturn(mockDisplayModule);

		doNothing().when(mockDisplayModule).displayBranch(any(Branch.class));
		String Author_empty = null;
		spyDisplaySeletionModule.displayBranchDetails(mockRepoList, mockOrg, Author_empty, mockStalePeriod);
		verify(spyDisplaySeletionModule).displayBranchDetails(mockRepoList, mockOrg, Author_empty, mockStalePeriod);
		
		spyDisplaySeletionModule.displayBranchDetails(mockRepoList, mockOrg, mockAuthor, mockStalePeriod);
		verify(spyDisplaySeletionModule).displayBranchDetails(mockRepoList, mockOrg, Author_empty, mockStalePeriod);

	}

	@Test
	public void displayRepoList_Sucess() throws JSONException, IOException {

		Repo repo1 = new Repo();
		repo1.setRepoName("business_Logic_Module");

		ArrayList<Repo> mockRepoList = new ArrayList<Repo>();
		mockRepoList.add(repo1);

		when(spyDisplaySeletionModule.getRepoService()).thenReturn(mockRepoService);
		when(mockRepoService.repoUrl(any(Organization.class))).thenReturn("Repo JSON as String");
		when(spyDisplaySeletionModule.getRepoDetails()).thenReturn(mockRepoDetails);
		when(mockRepoDetails.getRepoInfo(anyString())).thenReturn(mockRepoList);
		when(spyDisplaySeletionModule.getDisplayModule()).thenReturn(mockDisplayModule);
		doNothing().when(mockDisplayModule).displayRepo(mockRepoList);

		spyDisplaySeletionModule.displayRepoList(mockOrg);
		verify(spyDisplaySeletionModule, times(1)).displayRepoList(mockOrg);
	}

	@Test
	public void displayUsersList_Success() throws IOException {
		Repo repo1 = new Repo();
		repo1.setRepoName("business_Logic_Module");

		ArrayList<Repo> mockRepoList = new ArrayList<Repo>();
		mockRepoList.add(repo1);

		User mockUser1 = new User();
		mockUser1.setLogin("bc015394");

		User mockUser2 = new User();
		mockUser2.setLogin("rk015945");

		ArrayList<User> usersList = new ArrayList<User>();

		when(spyDisplaySeletionModule.getUserService()).thenReturn(mockUserService);
		when(mockUserService.userUrl(anyString(), any(Organization.class))).thenReturn("Some_User_JSON as String");
		when(spyDisplaySeletionModule.getUserDetails()).thenReturn(mockUserDetails);
		when(mockUserDetails.getUserInfo(anyString())).thenReturn(usersList);
		when(spyDisplaySeletionModule.getDisplayModule()).thenReturn(mockDisplayModule);

		doNothing().when(mockDisplayModule).displayUsers(usersList);

		spyDisplaySeletionModule.displayUsersList(mockRepoList, mockOrg);
		verify(spyDisplaySeletionModule, times(1)).displayUsersList(mockRepoList, mockOrg);
	}

	@Test
	public void staleBranchesList_Success() throws IOException {

		User mockUser1 = new User();
		mockUser1.setLogin("bc015394");

		User mockUser2 = new User();
		mockUser2.setLogin("rk015945");

		Branch mockStaleBranch = new Branch();
		mockStaleBranch.setBranchName("BugFix_Solution");
		mockStaleBranch.setFlagActive(false);
		mockStaleBranch.setUser(mockUser1);

		Branch mockActiveBranch = new Branch();
		mockActiveBranch.setBranchName("Calculate_Age");
		mockStaleBranch.setFlagActive(true);
		mockStaleBranch.setUser(mockUser2);

		Branch mockStaleBranch2 = new Branch();
		mockStaleBranch.setBranchName("BugFix_Solutions");
		mockStaleBranch.setFlagActive(false);
		mockStaleBranch.setUser(mockUser1);

		ArrayList<Branch> mockStaleBranchList = new ArrayList<Branch>();
		mockStaleBranchList.add(mockStaleBranch);
		mockStaleBranchList.add(mockStaleBranch2);

		Repo repo1 = new Repo();
		repo1.setRepoName("business_Logic_Module");

		ArrayList<Repo> mockRepoList = new ArrayList<Repo>();
		mockRepoList.add(repo1);

		String ExpectedMailBody = "Mail Content as String";

		when(spyDisplaySeletionModule.getStaleBranches()).thenReturn(mockStaleBranches);
		when(mockStaleBranches.retrieveStaleBranches(anyList(), any(Organization.class), anyInt()))
				.thenReturn(mockRepoList);
		when(spyDisplaySeletionModule.getMailContent()).thenReturn(mockMailContent);
		when(mockMailContent.prepareEmailContent(anyList(), any(Organization.class), anyInt()))
				.thenReturn("Mail Content as String");

		assertEquals(ExpectedMailBody, spyDisplaySeletionModule.staleBranchesList(mockRepoList, mockOrg, 0));
		mockRepoList.clear();
		assertEquals(null, spyDisplaySeletionModule.staleBranchesList(mockRepoList, mockOrg, 0));

	}

}
