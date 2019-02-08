package com.cerner.gitHub.branchReport.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cerner.gitHub.branchReport.model.Branch;
import com.cerner.gitHub.branchReport.model.User;

/**
 * @author SJ064201
 *
 */
public class BranchDetails {
	ResourceBundle messageBundle = ResourceBundle.getBundle("MessageBundle",Locale.getDefault());
	static Set<String> emailIDs = new HashSet<>();

	/**
	 * @param repoJSONData
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public ArrayList<Branch> getBranchInfo(String branchJSONData , int stalePeriod) throws JSONException, IOException {

		JSONArray jsonArray = new JSONArray(branchJSONData);
		ArrayList<Branch> branchList = new ArrayList<Branch>();

		for (int i = 0; i < jsonArray.length(); i++) {

			Branch branch = new Branch();
			User user = new User();
			JSONObject jsonStatusObject = jsonArray.getJSONObject(i);

			branch.setBranchName(jsonStatusObject.getString("name"));
			String branchCommitsUrl = jsonStatusObject.getJSONObject("commit").getString("url");
			String commitJSONData = getBranchService().commitsURL(branchCommitsUrl);
			JSONObject jsonCommitObject = new JSONObject(commitJSONData);
			branch.setBranchLink(jsonCommitObject.getString("html_url"));
			String emailID = jsonCommitObject.getJSONObject("commit").getJSONObject("author").getString("email");
			user.setEmail(emailID);


			if (emailID.contains("@cerner.com")) {
				emailIDs.add(emailID);
			}

			if (branch.getBranchName().equals("master"))

				continue;

			user.setName(jsonCommitObject.getJSONObject("commit").getJSONObject("author").getString("name"));
			branch.setDate(jsonCommitObject.getJSONObject("commit").getJSONObject("author").getString("date"));

			caclulateBranchAge(branch , stalePeriod);

			if (!jsonCommitObject.isNull("author")) {
				user.setLogin(jsonCommitObject.getJSONObject("author").getString("login"));
				user.setAvatar(jsonCommitObject.getJSONObject("author").getString("avatar_url"));

			}else if(!jsonCommitObject.isNull("committer")) {
				user.setLogin(jsonCommitObject.getJSONObject("committer").getString("login"));
				user.setAvatar(jsonCommitObject.getJSONObject("committer").getString("avatar_url"));
			}else
			 {
				user.setLogin("InvalidUser");
			}
			branch.setUser(user);
			branchList.add(branch);                

		}
		return branchList;

	}

	public Set<String> getEmailIDs() {
		return emailIDs;
	}

	private void caclulateBranchAge(Branch branch, int stalePeriod) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate today = LocalDate.now();
		LocalDate commitDate = LocalDate.parse(branch.getDate().substring(0, 10), formatter);
		Period p = Period.between(commitDate, today);

		long p2 = ChronoUnit.DAYS.between(commitDate, today);

		StringBuilder age = new StringBuilder(" ");
		if (p.getYears() != 0)
			age.append(p.getYears() + " " +messageBundle.getString("Years"));
		if (p.getMonths() != 0)
			age.append(p.getMonths() + " " +messageBundle.getString("Months"));
		if (p.getDays() != 0)
			age.append(p.getDays() + " " +messageBundle.getString("Days"));

		branch.setTimeStamp(age.toString());

		if (p2 >= stalePeriod) {
			branch.setFlagActive(false);
		} else {
			branch.setFlagActive(true);
		}

	}

	BranchService getBranchService() {
		return new BranchService();
	}

}
