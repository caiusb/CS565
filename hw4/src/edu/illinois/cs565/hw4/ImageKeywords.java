package edu.illinois.cs565.hw4;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

import com.amazonaws.mturk.addon.HITProperties;
import com.amazonaws.mturk.addon.HITQuestion;
import com.amazonaws.mturk.addon.QAPValidator;
import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.service.exception.ValidationException;
import com.amazonaws.mturk.util.PropertiesClientConfig;

public class ImageKeywords {

	private RequesterService service;

	private String rootDir = "src/edu/illinois/cs565/hw4";
	private String questionFile = rootDir + "/best_image.question";
	private String propertiesFile = rootDir + "/best_image.properties";

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

	@SuppressWarnings("resource")
	public void createBestImage() {
		try {

			HITProperties props = new HITProperties(propertiesFile);
			HITQuestion question = new HITQuestion(questionFile);
			QAPValidator.validate(question.getQuestion());

			HIT hit = service.createHIT(
					null, // HITTypeId
					props.getTitle(),
					props.getDescription(),
					props.getKeywords(), // keywords
					question.getQuestion(), props.getRewardAmount(),
					props.getAssignmentDuration(),
					props.getAutoApprovalDelay(), props.getLifetime(),
					props.getMaxAssignments(), props.getAnnotation(), // requesterAnnotation
					props.getQualificationRequirements(), null // responseGroup
					);

			System.out.println("Created HIT: " + hit.getHITId());

			System.out.println("You may see your HIT with HITTypeId '"
					+ hit.getHITTypeId() + "' here: ");

			System.out.println(service.getWebsiteURL()
					+ "/mturk/preview?groupId=" + hit.getHITTypeId());

			File successFile = new File("hw4.success");
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					successFile));
			writer.write("hitid\thittypeid\n");
			writer.write(hit.getHITId() + "\t" + hit.getHITTypeId() + "\n");
			writer.close();

		} catch (ValidationException e) {
			// The validation exceptions will provide good insight into where in
			// the QAP has errors.
			// However, it is recommended to use other third party XML schema
			// validators to make
			// it easier to find and fix issues.
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
		app.createBestImage();
	}
}
