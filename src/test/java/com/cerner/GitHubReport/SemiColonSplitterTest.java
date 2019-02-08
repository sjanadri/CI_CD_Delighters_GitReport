package com.cerner.GitHubReport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.gitHub.branchReport.SemiColonSplitter;

@RunWith(MockitoJUnitRunner.class)
public class SemiColonSplitterTest {
	
	SemiColonSplitter testSemiColonSplitter;
	SemiColonSplitter spySemiColonSplitter;
	
	
	@Before
	public void setUp() {
		testSemiColonSplitter = new SemiColonSplitter();
		spySemiColonSplitter = spy(testSemiColonSplitter);
	}
	
	@Test
	public void split_success() {
		
		String input = "abc@cerner.com;xyz@cerner.com"; 
		
		List<String> outputArray = spySemiColonSplitter.split(input);
		
		List<String> expectedArray = new ArrayList<>();
		expectedArray.add("abc@cerner.com");
		expectedArray.add("xyz@cerner.com");
		
		assertEquals(expectedArray, outputArray);
		
	}
	
}
