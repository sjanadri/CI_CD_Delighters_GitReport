package com.cerner.GitHubReport;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.gitHub.branchReport.DisplayModule;
import com.cerner.gitHub.branchReport.model.Branch;
import com.cerner.gitHub.branchReport.model.Repo;
import com.cerner.gitHub.branchReport.model.User;

@RunWith(MockitoJUnitRunner.class)
public class DisplayModuleTest {

	DisplayModule testDisplayModule;
	DisplayModule spyDisplayModule;

	@Before
	public void setup() throws Exception {
		testDisplayModule = new DisplayModule();
		spyDisplayModule = spy(testDisplayModule);
	}

	@Test
	public void displayRepo_succes() {
		
		ArrayList<Repo> repoList = new ArrayList<Repo>();
		Repo repo1 = new Repo();
		repo1.setRepoName("repo1");
		repoList.add(repo1);
		spyDisplayModule.displayRepo(repoList);
		verify(spyDisplayModule, times(1)).displayRepo(repoList);

	}

	@Test
	public void displayUsers_success() {
		
		ArrayList<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setLogin("AA001122");
		userList.add(user1);
		spyDisplayModule.displayUsers(userList);
		verify(spyDisplayModule, times(1)).displayUsers(userList);

	}
	
	@Test
	public void displayBranch_succes() {
		
		Branch branch = new Branch();
		
		User user1 = new User();
		user1.setLogin("AA001122");
		user1.setEmail("abc@abc");
		user1.setName("UserA");
		
		branch.setBranchName("BranchA");
		branch.setFlagActive(false);
		branch.setUser(user1);
		branch.setDate("2016-12-02T18:31:34Z");
		branch.setTimeStamp("6 Months 26 Days");
		
		spyDisplayModule.displayBranch(branch);
		verify(spyDisplayModule , times(1)).displayBranch(branch);
	}
}
