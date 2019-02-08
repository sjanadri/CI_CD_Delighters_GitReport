package com.cerner.gitHub.branchReport.eMailModule;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class ReadFileIntoList {
	public  List<String> readFileInList(String fileName) {

		List<String> lines = Collections.emptyList();
		try {

			lines = getLinesinFile(fileName);

		}

		catch (IOException e) {

			e.printStackTrace();
		}
		return lines;
	}

	public List<String> getLinesinFile(String fileName) throws IOException {

		return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
	}

	public Set<String> getMailingList(String pathToFile) {
		List<String> l = readFileInList(pathToFile);

		Set<String> mailAddresses = new HashSet<String>();

		Iterator<String> itr = l.iterator();
		while (itr.hasNext()) {
			mailAddresses.add(itr.next());

		}

		for (String mail : mailAddresses) {
			System.out.println(mail);
		}

		return mailAddresses;
	}
}
