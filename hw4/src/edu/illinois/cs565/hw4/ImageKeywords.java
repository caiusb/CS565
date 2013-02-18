package edu.illinois.cs565.hw4;

import java.io.File;

import com.amazonaws.mturk.addon.HITProperties;
import com.amazonaws.mturk.addon.HITQuestion;
import com.amazonaws.mturk.addon.QAPValidator;
import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.service.exception.ValidationException;
import com.amazonaws.mturk.util.PropertiesClientConfig;

public class ImageKeywords {

	private RequesterService service;

	// Defining the location of the file containing the QAP and the properties
	// of the HIT
	private String rootDir = "src/edu/illinois/cs565/hw4/";
	private String questionFile = rootDir + "image_keyword.question";
	private String propertiesFile = rootDir + "image_keyword.properties";

	/**
	 * Constructor
	 * 
	 */
	public ImageKeywords() {
		service = new RequesterService(new PropertiesClientConfig(
				"mturk.properties"));
	}

	/**
	 * Check to see if your account has sufficient funds
	 * 
	 * @return true if there are sufficient funds. False if not.
	 */
	public boolean hasEnoughFund() {
		double balance = service.getAccountBalance();
		System.out.println("Got account balance: "
				+ RequesterService.formatCurrency(balance));
		return balance > 0;
	}

	/**
	 * Creates the Best Image HIT
	 * 
	 * @param previewFile
	 *            The filename of the preview file to be generated. If null, no
	 *            preview file will be generated and the HIT will be created on
	 *            Mechanical Turk.
	 */
	public void createBestImage(String previewFile) {
		try {
			HITProperties props = new HITProperties(propertiesFile);
			HITQuestion question = new HITQuestion(questionFile);
			QAPValidator.validate(question.getQuestion());

			if (previewFile != null) {
				System.out.println("--[Previewing HITs]--------");
				System.out.println("Saving preview to file: " + previewFile);

				if (rootDir != ".")
					previewFile = rootDir + "/" + previewFile;

				service.previewHIT(previewFile, null, props, question);
				System.out.println("Preview saved to: "
						+ new File(previewFile).getAbsolutePath());

			} else {

				HIT hit = service.createHIT(
						null, // HITTypeId
						props.getTitle(), //title
						props.getDescription(), //description
						props.getKeywords(), // keywords
						question.getQuestion(), //question
						props.getRewardAmount(), //reward
						props.getAssignmentDuration(), //duration (seconds)
						props.getAutoApprovalDelay(),  // auto aprroval delay
						props.getLifetime(), // get lifetime
						props.getMaxAssignments(), // get max assignments
						props.getAnnotation(), // requesterAnnotation
						props.getQualificationRequirements(), // requester qualifications
						null // responseGroup
						);

				System.out.println("Created HIT: " + hit.getHITId());

				System.out.println("You may see your HIT with HITTypeId '"
						+ hit.getHITTypeId() + "' here: ");

				System.out.println(service.getWebsiteURL()
						+ "/mturk/preview?groupId=" + hit.getHITTypeId());
			}
		} catch (ValidationException e) {
			System.err.println("QAP contains an error: "
					+ e.getLocalizedMessage());

		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ImageKeywords app = new ImageKeywords();

		if (args.length == 1 && !args[0].equals("")) {
			app.createBestImage(args[0]);
		} else if (app.hasEnoughFund()) {
			app.createBestImage(null);
		}
	}
}
