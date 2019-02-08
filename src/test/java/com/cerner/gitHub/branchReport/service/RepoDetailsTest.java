package com.cerner.gitHub.branchReport.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.gitHub.branchReport.model.Repo;

@RunWith(MockitoJUnitRunner.class)
public class RepoDetailsTest {

	private RepoDetails testRepoDetails;
	private RepoDetails spyRepoDetails;

	@Before
	public void setup() throws Exception {
		testRepoDetails = new RepoDetails();
		spyRepoDetails = spy(testRepoDetails);
	}

	@Test
	public void getRepoInfo_Succes() {

		String RepoJson = "[\r\n" + "  {\r\n" + "    \"id\": 57853,\r\n"
				+ "    \"name\": \"centralized-schema-deployment\"\r\n" + "   },\r\n" + "   {\r\n"
				+ "    \"id\": 57855,\r\n" + "    \"name\": \"ui-console\"\r\n" + "   }\r\n" + " ]";

		ArrayList<Repo> expected_repo_list = new ArrayList<Repo>();
		Repo repo1 = new Repo();
		repo1.setRepoName("centralized-schema-deployment");
		Repo repo2 = new Repo();
		repo2.setRepoName("ui-console");

		expected_repo_list.add(repo1);
		expected_repo_list.add(repo2);

		assertEquals(expected_repo_list.get(0).getRepoName(),
				spyRepoDetails.getRepoInfo(RepoJson).get(0).getRepoName());
	}

}
