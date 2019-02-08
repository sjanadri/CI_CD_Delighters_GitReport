package com.cerner.gitHub.branchReport.eMailModule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.gitHub.branchReport.model.Organization;

@RunWith(MockitoJUnitRunner.class)
public class FormEmailTest {

	FormEmail testFormEmail;
	FormEmail spyFormEmail;

	ReadFileIntoList mockReadFileIntoList;
	Properties mockProperties;
	EmailUtil mockEmailUtil;

	@Before
	public void setUp() {
		testFormEmail = new FormEmail();
		spyFormEmail = spy(testFormEmail);

		mockReadFileIntoList = mock(ReadFileIntoList.class);
	}

	@Test
	public void simpleEmail_success() {
		String mailContent = "Text_to_be_sent";
		String pathToFile = "filePath_as_String";
		List<String> mailListfromInput = new ArrayList<>();
		mailListfromInput.add("abc@cerner.com");
		mailListfromInput.add("xyz@cerner.com");

		Organization org= new Organization();
		org.setOrgName("Some_Name");
		
		Set<String> mailListfromBranch = new HashSet<>();
		mailListfromBranch.add("abc@cerner.com");
		mailListfromBranch.add("xyz@cerner.com");

		when(spyFormEmail.getReadFileIntoList()).thenReturn(mockReadFileIntoList);
		when(mockReadFileIntoList.getMailingList(anyString())).thenReturn(mailListfromBranch);

		doNothing().when(spyFormEmail).sendEmail(anyString(), anySet(), anyString(), anyString() , any(Organization.class));

		spyFormEmail.simpleEmail(mailContent, pathToFile, mailListfromInput, mailListfromBranch , org);
		verify(spyFormEmail, times(1)).simpleEmail(mailContent, pathToFile, mailListfromInput, mailListfromBranch ,org);

		mailListfromInput.clear();
		mailListfromBranch.clear();
		spyFormEmail.simpleEmail(mailContent, pathToFile, mailListfromInput, mailListfromBranch , org);
		verify(spyFormEmail, times(2)).simpleEmail(mailContent, pathToFile, mailListfromInput, mailListfromBranch , org);

	}
}
