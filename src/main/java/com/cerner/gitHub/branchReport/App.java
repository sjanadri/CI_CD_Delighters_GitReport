package com.cerner.gitHub.branchReport;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.cerner.gitHub.branchReport.eMailModule.FormEmail;
import com.cerner.gitHub.branchReport.model.Organization;
import com.cerner.gitHub.branchReport.model.Repo;
import com.cerner.gitHub.branchReport.service.OrganisationService;
import com.cerner.gitHub.branchReport.service.StaleBranches;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * CI-CD Delighter Application main thread 
 * 
 * 
 * 
 * @author SJ064201
 */
public class App {

	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		final MainCLIParams mainArguments = new MainCLIParams();

		App application = new App();
		JCommander jCommander = new JCommander(mainArguments);
		jCommander.setProgramName("GitStaleBranchReport");

		try {
			jCommander.parse(args);
		} catch (ParameterException exception) {
			System.out.println(exception.getMessage());
			showUsage(jCommander);
		}

		if (mainArguments.getOrg().isEmpty()) {
			showUsage(jCommander);
		}

		application.run(mainArguments);

		if (mainArguments.isHelp()) {
			showUsage(jCommander);
		}

	}

	static void showUsage(JCommander jCommander) {
		jCommander.usage();
		System.exit(0);
	}

	public void run(MainCLIParams mainArguments) {
		try (Scanner sc = new Scanner(System.in)) {

			Organization org = getOrganisationService().orgUrl(mainArguments.getOrg());
			logger.info("Working on Org " + mainArguments.getOrg());

			int stalePeriod = mainArguments.getStalePeriod();

			if (stalePeriod == 0) {
				stalePeriod = 30;
			}

			ArrayList<Repo> repoList = getDisplaySelectionModule().displayRepoList(org);

			System.out.println("Printing - mailinglist from input as argument");
			if (!mainArguments.getEmailIds().isEmpty()) {
				for (String id : mainArguments.getEmailIds()) {
					System.out.println(id);
				}
			}

			String mailBody = getDisplaySelectionModule().staleBranchesList(repoList, org, stalePeriod);

			Set<String> mailListfromBranch = getStaleBranches().getMailingList();
			System.out.println("Printing all mailid retrived from branch data");
			for (String id : mailListfromBranch) {

				System.out.println(id);

			}
			System.out.println("--------------------------------------------------------");

			if (mailBody != null) {
				getFormEmail().simpleEmail(mailBody, mainArguments.getMailingListFile(), mainArguments.getEmailIds(),
						mailListfromBranch , org);
			}
			System.out.println("________________________________________________________________________________");

			getDisplaySelectionModule().displayUsersList(repoList, org);
			getDisplaySelectionModule().displayBranchDetails(repoList, org, mainArguments.getAuthor(), stalePeriod);

		} catch (

		JSONException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {

		}
	}

	public FormEmail getFormEmail() {
		return new FormEmail();
	}

	public OrganisationService getOrganisationService() {
		return new OrganisationService();
	}

	public StaleBranches getStaleBranches() {
		return new StaleBranches();
	}

	public DisplaySelectionModule getDisplaySelectionModule() {
		return new DisplaySelectionModule();
	}
}
