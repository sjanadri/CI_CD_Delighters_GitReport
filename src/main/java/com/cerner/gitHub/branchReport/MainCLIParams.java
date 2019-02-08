package com.cerner.gitHub.branchReport;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class MainCLIParams {

	@Parameter(names = { "-o", "--organisation" }, description = "Team/Org Name as displayed in GitHub", required = true)
	private String org;

	@Parameter(names = { "-t",
			"--staleAfterPeriod" }, description = "if the branch has no commits after specified date it will be marked as stale branch", required = false)
	private int stalePeriod;

	@Parameter(names = { "-a",
			"--author" }, description = "name to fetch Commits from particular Team Member", required = false)
	private String author;

	@Parameter(names = { "-d",
			"--directoryTeamMail" }, required = false, description = "Absolute path to file containing archive profile which lists specifically what and what not to archive in all users home directory.")
	private String mailingListFile;

	@Parameter(names = { "-e",
			"--emailIDs" }, splitter = SemiColonSplitter.class, required = false, description = "List of team E mail IDs" )
	private List<String> emailIds;

	@Parameter(names = { "-h", "--help" }, help = true, description = "Displays help for input information")
	private boolean help;
	
	public int getStalePeriod() {
		return stalePeriod;
	}

	public String getOrg() {
		return org;
	}

	public List<String> getEmailIds() {
		return emailIds;
	}

	public String getAuthor() {
		return author;
	}

	public String getMailingListFile() {
		return mailingListFile;
	}

	public boolean isHelp() {
		return help;
	}

}