package com.cerner.gitHub.branchReport.rest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class QueryGitHubTest {

	private QueryGitHub testQueryGitHub;

	@Before
	public void setUp() throws Exception {
		testQueryGitHub = new QueryGitHub();

	}

	@Test
	public void retreiveGitHubData_Success() throws Exception {

		String result = testQueryGitHub.retreiveGitHubData(
				"https://github.cerner.com/api/v3/repos/Installation-Solution/installation-environment-controller/branches");

		assertTrue(result.contains("[{\"name\":\"2062-ValidateDomain\",\"commit\":{"));
	}

}
