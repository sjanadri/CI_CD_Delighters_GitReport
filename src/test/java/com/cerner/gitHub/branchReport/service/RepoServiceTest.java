package com.cerner.gitHub.branchReport.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.rest.QueryGitHub;

@RunWith(MockitoJUnitRunner.class)
public class RepoServiceTest {

	RepoService testRepoService; 
	RepoService spyRepoService;
	Organization mockOrganization;
	QueryGitHub mockQueryGitHub;

	String mockRepoAPI = "https://github.cerner.com/api/v3/users/Installation-Solution/repos";
	String expectedRepoJSON = "JSON for Repo";
	
	@Before
	public void setup() throws Exception {
		mockOrganization = mock(Organization.class);
		mockQueryGitHub = mock(QueryGitHub.class);
		testRepoService = new RepoService();
		spyRepoService = spy(testRepoService);
	}

	@Test
	public void repoJsonTest() throws JSONException, IOException {
		when(spyRepoService.getQueryGitHub()).thenReturn(mockQueryGitHub);
		when(mockQueryGitHub.getListReposAPI()).thenReturn("someURL");
		doReturn("Installation-Solution").when(mockOrganization).getOrgName();
		
		when(mockQueryGitHub.retreiveGitHubData(anyString())).thenReturn(expectedRepoJSON);

		assertEquals(expectedRepoJSON, spyRepoService.repoUrl(mockOrganization));
	}

}
