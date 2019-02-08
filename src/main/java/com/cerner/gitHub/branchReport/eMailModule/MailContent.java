package com.cerner.gitHub.branchReport.eMailModule;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.cerner.gitHub.branchReport.model.Branch;
import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.model.Repo;

public class MailContent {

	ResourceBundle messageBundle = ResourceBundle.getBundle("MessageBundle", Locale.getDefault());

	public String prepareEmailContent(List<Repo> repoStaleBranchList, Organization org, int stalePeriod) {
		StringBuilder mailBody = new StringBuilder("<html><h4>");

		mailBody.append("<img src=" + org.getOrgImage() + "alt=" + " " + "height=\"25\" width=\"25\" />")
				.append("&nbsp&nbsp");
		mailBody.append(messageBundle.getString("heading_one")).append(" : ").append(org.getOrgName())
				.append(" </h4><h5>").append(messageBundle.getString("heading_two")).append("&nbsp-&nbsp" + stalePeriod)
				.append("&nbsp").append(messageBundle.getString("Days")).append("</h5> <body>");
		mailBody.append("<div bgcolor=\"#666\">");
		mailBody.append("<table border=\"1\" bgcolor=\"#ffe5e5\"  padding = \"6px\"  style='border:black'>");
		mailBody.append("<thead>");
		mailBody.append("<tr>");

		mailBody.append("<th max-width = \"10px\"> ").append(messageBundle.getString("branch_col_heading"))
				.append("</th>");
		mailBody.append("<th>").append(messageBundle.getString("age_col_heading")).append("</th>");
		mailBody.append("<th>").append(messageBundle.getString("author_col_heading")).append("</th>");
		mailBody.append("</tr>").append("</thead>");

		for (Repo repo : repoStaleBranchList) {

			mailBody.append("<tr>");
			mailBody.append("<td colspan = \"3\"> <b> ").append(messageBundle.getString("Repository")).append(" : ")
					.append("&nbsp" + repo.getRepoName() + "&nbsp").append("</b></td>");
			mailBody.append("</tr>");
			for (Branch staleBranch : repo.getStaleBranchList()) {
				mailBody.append("<tr>");
				mailBody.append("<td><a href =" + staleBranch.getBranchLink() + ">").append(staleBranch.getBranchName())
						.append("</a></td>");
				mailBody.append("<td>").append("&nbsp").append(staleBranch.getTimeStamp()).append("</a></td>");
				mailBody.append("<td align=\"center\">")
						.append("<img src=" + staleBranch.getUser().getAvatar() + "alt="
								+ staleBranch.getUser().getLogin() + "height=\"18\" width=\"18\" />&nbsp")
						.append(" ").append(staleBranch.getUser().getLogin()).append("</td>");
				mailBody.append("</tr>");
			}

		}

		mailBody.append("</table><br/>");
		mailBody.append("</div>");
		mailBody.append("</body></html>");

		return mailBody.toString();

	}
}
