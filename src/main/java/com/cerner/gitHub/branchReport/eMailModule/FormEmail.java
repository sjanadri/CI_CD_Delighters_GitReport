package com.cerner.gitHub.branchReport.eMailModule;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Session;

import com.cerner.gitHub.branchReport.model.Organization;

public class FormEmail {

	public void simpleEmail(String mailContent, String pathToFile, List<String> mailListfromInput,
			Set<String> mailListfromBranch , Organization org) {

		System.out.println("SimpleEmail Start");

		String smtpHostServer = "smtplb.cerner.com";

		Set<String> mailingList = new HashSet<String>();

		if (pathToFile != null) {
			mailingList.addAll(getReadFileIntoList().getMailingList(pathToFile));
		}

		if (mailListfromInput != null) {
			mailingList.addAll(mailListfromInput);
		}

		if (mailingList.isEmpty()) {

			mailingList.addAll(mailListfromBranch);
		}

		System.out.println("eMial Receivers########################################");
		for (String s : mailingList) {
			System.out.println(s);
		}

		sendEmail(smtpHostServer, mailingList, "Stale Branch Details ", mailContent , org);

	}

	void sendEmail(String smtpHostServer, Set<String> mailingList, String subject, String mailContent , Organization org ) {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHostServer);
		EmailUtil.sendEmail(Session.getInstance(props, null), mailingList, subject, mailContent , org);

	}

	ReadFileIntoList getReadFileIntoList() {
		return new ReadFileIntoList();
	}

}
