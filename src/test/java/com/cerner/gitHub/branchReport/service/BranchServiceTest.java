package com.cerner.gitHub.branchReport.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.rest.QueryGitHub;

@RunWith(MockitoJUnitRunner.class)
public class BranchServiceTest {

	private BranchService testBranchService;
	private BranchService spyBranchService;
	private Organization mockOrganization;
	private QueryGitHub mockQueryGitHub;

	private String mockprojectName = "Module1";

	private String mockExpectedBranchJSON = "[\r\n" + "  {\r\n" + "    \"name\": \"2062-ValidateDomain\",\r\n"
			+ "    \"commit\": {\r\n" + "      \"sha\": \"4df1fc0a69e09b1d0c6632478a14611a02b9a928\",\r\n"
			+ "      \"url\": \"https://github.cerner.com/api/v3/repos/Installation-Solution/installation-environment-controller/commits/4df1fc0a69e09b1d0c6632478a14611a02b9a928\"\r\n"
			+ "    }\r\n" + "  }]";

	private String mockCommitUrl = "https://github.cerner.com/api/v3/repos/Installation-Solution/installation-environment-controller/commits/4df1fc0a69e09b1d0c6632478a14611a02b9a928";

	@Before
	public void setup() throws Exception {
		mockQueryGitHub = mock(QueryGitHub.class);
		mockOrganization = mock(Organization.class);
		testBranchService = new BranchService();
		spyBranchService = spy(testBranchService);
	}

	@Test
	public void branchesUrl_Success() throws Exception {

		when(spyBranchService.getQueryGitHub()).thenReturn(mockQueryGitHub);
		when(mockQueryGitHub.getAllBranches_repoAPI()).thenReturn("SomeURL");
		doReturn("Installation-Solution").when(mockOrganization).getOrgName();

		when(mockQueryGitHub.retreiveGitHubData(anyString())).thenReturn(mockExpectedBranchJSON);

		assertEquals(mockExpectedBranchJSON, spyBranchService.branchesUrl(mockprojectName, mockOrganization));
	}

	@Test
	public void commitsURL_Success() throws Exception {

		when(spyBranchService.getQueryGitHub()).thenReturn(mockQueryGitHub);
		when(mockQueryGitHub.retreiveGitHubData(anyString())).thenReturn(mockExpectedBranchJSON);

		assertEquals(mockExpectedBranchJSON, spyBranchService.commitsURL(mockCommitUrl));
	}
}
