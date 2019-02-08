package com.cerner.gitHub.branchReport.eMailModule;

import java.util.Date;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cerner.gitHub.branchReport.model.Organization;

public class EmailUtil {

	/**
	 * Utility method to send simple HTML email
	 *
	 * @param session
	 * @param mailingList
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, Set<String> mailingList, String subject, String body , Organization org) {
		try {
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("no_reply@example.com", org.getOrgName()));

			msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

			msg.setSubject(subject, "UTF-8");

			msg.setContent(body, "text/html");

			msg.setSentDate(new Date());

			for (String mailID : mailingList) {

				msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mailID, false));

			}

			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
