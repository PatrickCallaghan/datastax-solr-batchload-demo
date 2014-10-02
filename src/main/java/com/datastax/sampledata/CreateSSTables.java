package com.datastax.sampledata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.demo.utils.LoadUser;
import com.datastax.patientcare.model.User;
import com.datastax.solrbatchload.CQL3BulkLoadUsers;

public class CreateSSTables {

	private static Logger logger = LoggerFactory.getLogger(CreateSSTables.class);
	private static Long userId = 1l;
	private static int batch = 5000;

	public static void createSSTablesUsers(CQL3BulkLoadUsers bulkLoader, int totalUsers) throws IOException {

		int cycles = totalUsers / batch;

		List<User> mainBatch = LoadUser.processUserFile("user_list_final.csv");

		logger.info("Starting " + cycles + " cycles.");
		for (int i = 0; i < cycles; i++) {

			List<User> users = randomize(mainBatch);
			bulkLoader.load(users);

			logger.info("Wrote " + (i+1) + " of " + cycles + " cycles. Batch size : " + batch);
		}
		bulkLoader.finish();

		logger.info("Finished file with " + totalUsers + " users.");
	}

	private static List<User> randomize(List<User> mainBatch) {
		List<User> results = new ArrayList<User>();

		int batchSize = mainBatch.size();

		for (int i = 0; i < batchSize; i++) {

			String first = mainBatch.get((new Double(Math.random() * batchSize).intValue())).getFirstname();
			String middleName = mainBatch.get((new Double(Math.random() * batchSize).intValue())).getMiddlename();
			String last = mainBatch.get((new Double(Math.random() * batchSize).intValue())).getLastname();
			String county = mainBatch.get((new Double(Math.random() * batchSize).intValue())).getCountyName();
			String country = mainBatch.get((new Double(Math.random() * batchSize).intValue())).getCountryCode();
			Date dob = randomDateInLastNYears(100);
			String streetAddress = mainBatch.get((new Double(Math.random() * batchSize).intValue())).getStreetAddress();
			String email = mainBatch.get((new Double(Math.random() * batchSize).intValue())).getEmail();
			String gender = mainBatch.get((new Double(Math.random() * batchSize).intValue())).getGender();
			String postCode = mainBatch.get((new Double(Math.random() * batchSize).intValue())).getPostCode();

			String passport = (buffer(userId));
			String driversLicense = "" + dob.getTime();

			User user = new User();
			user.setUserId(UUID.randomUUID().toString());
			user.setFirstname(first);
			user.setMiddlename(middleName);
			user.setLastname(last);
			user.setPassport(passport);
			user.setDriversLicense(driversLicense);
			user.setStreetAddress(streetAddress);
			user.setEmail(email);
			user.setCountryCode(country);
			user.setCountyName(county);
			user.setDob(dob);
			user.setGender(gender);
			user.setPhoneNumber(new Double(100000000 + Math.random() * 900000000).toString());
			user.setPostCode(postCode);

			results.add(user);
			userId++;
		}

		return results;
	}

	private static String buffer(Long userId) {

		String result = "" + userId;

		while (result.length() < 12) {
			result = "0" + result;
		}

		return result;
	}

	private static Date randomDateInLastNYears(int years) {

		DateTime dateTime = new DateTime();
		dateTime = dateTime.minusYears(new Double(Math.random() * years).intValue());
		dateTime = dateTime.minusMonths(new Double(Math.random() * 12).intValue());
		dateTime = dateTime.minusDays(new Double(Math.random() * 30).intValue());

		dateTime = dateTime.minusHours(new Double(Math.random() * 24).intValue());
		dateTime = dateTime.minusMinutes(new Double(Math.random() * 60).intValue());
		dateTime = dateTime.minusSeconds(new Double(Math.random() * 60).intValue());

		return dateTime.toDate();
	}
}
