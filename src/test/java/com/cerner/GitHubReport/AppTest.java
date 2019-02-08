package com.cerner.GitHubReport;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.beust.jcommander.JCommander;
import com.cerner.gitHub.branchReport.App;
import com.cerner.gitHub.branchReport.DisplaySelectionModule;
import com.cerner.gitHub.branchReport.MainCLIParams;
import com.cerner.gitHub.branchReport.eMailModule.FormEmail;
import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.service.OrganisationService;
import com.cerner.gitHub.branchReport.service.StaleBranches;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

	private App spyAppication;
	private App testApplication;

	FormEmail mockFormEmail;
	OrganisationService mockOrganisationService;
	StaleBranches mockStaleBranches;
	DisplaySelectionModule mockDisplaySelectionModule;
	JCommander mockJCommander;

	MainCLIParams mockmainArguments;
	Organization mockOrg;

	@Before
	public void setup() throws Exception {
		mockFormEmail = mock(FormEmail.class);
		mockOrganisationService = mock(OrganisationService.class);
		mockStaleBranches = mock(StaleBranches.class);
		mockmainArguments = mock(MainCLIParams.class);
		mockOrg = mock(Organization.class);
		mockDisplaySelectionModule = mock(DisplaySelectionModule.class);
		mockJCommander = mock(JCommander.class);

		testApplication = new App();
		spyAppication = spy(testApplication);
	}

	@Test
	public void run_Success() throws IOException {

		when(spyAppication.getOrganisationService()).thenReturn(mockOrganisationService);
		when(mockmainArguments.getOrg()).thenReturn("SomeOrgName");
		when(mockOrganisationService.orgUrl(anyString())).thenReturn(mockOrg);

		when(mockmainArguments.getStalePeriod()).thenReturn(0);

		when(spyAppication.getDisplaySelectionModule()).thenReturn(mockDisplaySelectionModule);
		when(mockmainArguments.getEmailIds()).thenReturn(Arrays.asList("email1", "email2", "email3"));
		when(mockDisplaySelectionModule.staleBranchesList(anyList(), any(Organization.class), anyInt()))
				.thenReturn("mailBody_as_String");
		when(spyAppication.getStaleBranches()).thenReturn(mockStaleBranches);
		Set<String> hash_Set = new HashSet<String>();
		hash_Set.add("id1");
		hash_Set.add("id2");
		hash_Set.add("id3");
		when(mockStaleBranches.getMailingList()).thenReturn(hash_Set);
		when(spyAppication.getFormEmail()).thenReturn(mockFormEmail);
		doNothing().when(mockDisplaySelectionModule).displayUsersList(anyList(), any(Organization.class));
		
		spyAppication.run(mockmainArguments);
		verify(spyAppication, times(1)).run(mockmainArguments);

	}

	

}
