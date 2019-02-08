package com.cerner.gitHub.branchReport.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;

public class UserDetailsTest {

	private UserDetails testUserDetails;
	private UserDetails spyUserDetails;

	@Before
	public void setUp() {
		testUserDetails = new UserDetails();
		spyUserDetails = spy(testUserDetails);
	}

	@Test
	public void getUserInfo_succes() {

		String UserJson = "[\r\n" + "  {\r\n" + "    \"login\": \"cernzs\", \r\n" + "	},\r\n" + "	{\r\n"
				+ "    \"login\": \"GA044433\",\r\n" + "	}\r\n" + "]";

		assertEquals("cernzs", spyUserDetails.getUserInfo(UserJson).get(0).getLogin());
		

	}
}
