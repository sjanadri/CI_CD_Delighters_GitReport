package com.cerner.gitHub.branchReport.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.rest.QueryGitHub;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	UserService spyUserService;
	UserService testUserService;

	QueryGitHub mockQueryGitHub;
	Organization mockOrg;

	String expectedUserJSON = "abc";
	String mockprojectName = "installation-environment-control";

	@Before
	public void setup() throws Exception {
		mockQueryGitHub = mock(QueryGitHub.class);
		mockOrg = mock(Organization.class);

		testUserService = new UserService();
		spyUserService = spy(testUserService);
	}

	@Test
	public void userUrl_Success() throws IOException {
		when(spyUserService.getQueryGitHub()).thenReturn(mockQueryGitHub);
		when(mockQueryGitHub.getListUsersAPI()).thenReturn("SomeURL");
		doReturn("Installation-Solutioin").when(mockOrg).getOrgName();
		when(mockQueryGitHub.retreiveGitHubData(anyString())).thenReturn(expectedUserJSON);

		assertEquals(expectedUserJSON, spyUserService.userUrl(mockprojectName, mockOrg));

	}
}