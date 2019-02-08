package com.cerner.gitHub.branchReport.eMailModule;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReadFileIntoListTest {
	ReadFileIntoList testReadFileIntoList;
	ReadFileIntoList spyReadFileIntoList;
	ReadFileIntoList mockReadFileIntoList;

	@Before
	public void setUp() {
		testReadFileIntoList = new ReadFileIntoList();
		spyReadFileIntoList = spy(testReadFileIntoList);

		mockReadFileIntoList = mock(ReadFileIntoList.class);
	}

	@Test
	public void getMailingList_success() throws IOException {

		String pathToFile = "C://filder1/file.txt";

		List<String> lines = new ArrayList<>();
		lines.add("abc@cerner.com");
		lines.add("xyz@cerner.com");

		doReturn(lines).when(spyReadFileIntoList).getLinesinFile(anyString());

		Set<String> expectedlist = new HashSet<>();
		expectedlist.add("abc@cerner.com");
		expectedlist.add("xyz@cerner.com");

		Set<String> actualList = spyReadFileIntoList.getMailingList(pathToFile);

		assertEquals(expectedlist, actualList);

	}

}
