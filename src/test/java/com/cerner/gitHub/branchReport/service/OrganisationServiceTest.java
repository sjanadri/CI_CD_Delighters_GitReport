package com.cerner.gitHub.branchReport.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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
public class OrganisationServiceTest {

	OrganisationService spyOrganisationService;
	OrganisationService testOrganisationService;

	QueryGitHub mockQueryGitHub;

	String mockOrgName = "Installation-Solution";
	String mockOrgJSON = "{\r\n" + "  \"login\": \"Installation-Solution\",\r\n" + "  \"id\": 2441,\r\n"
			+ "  \"avatar_url\": \"https://avatars.github.cerner.com/u/2441?\"}";

	@Before
	public void setup() throws Exception {
		mockQueryGitHub = mock(QueryGitHub.class);
		testOrganisationService = new OrganisationService();
		spyOrganisationService = spy(testOrganisationService);
	}

	@Test
	public void orgUrl_Success() throws Exception {
		Organization expectedOrg = new Organization();
		expectedOrg.setOrgName(mockOrgName);
		expectedOrg.setOrgImage("https://avatars.github.cerner.com/u/2441?");

		when(spyOrganisationService.getQueryGitHub()).thenReturn(mockQueryGitHub);
		when(mockQueryGitHub.getOrgDetailsApI()).thenReturn("API_to_Repo");
		when(mockQueryGitHub.retreiveGitHubData(anyString())).thenReturn(mockOrgJSON);

		Organization actualOrg = spyOrganisationService.orgUrl(mockOrgName);

		assertEquals(expectedOrg.getOrgImage(), actualOrg.getOrgImage());

	}

}
