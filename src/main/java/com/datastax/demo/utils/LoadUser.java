package com.datastax.demo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.datastax.patientcare.model.User;

public class LoadUser {

	//User example - 
	private static final String COMMA = ",";
	private static final Logger LOG = Logger.getLogger(LoadUser.class);
	private static DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

	
	public static List<User> processUserFile(String filename){
		List<User> users = new ArrayList<User>();	
		
		List<String> readFileIntoList = FileUtils.readFileIntoList(filename);
		
		User user;
		
		for (String row : readFileIntoList){
						
			String county = SampleData.counties.get(new Double(Math.random() * SampleData.counties.size()).intValue());			
			String[] items = row.split(COMMA);
			
			user = new User();
			user.setUserId(UUID.randomUUID().toString());
			user.setFirstname(items[1].trim());
			user.setMiddlename(items[2].trim());
			user.setLastname(items[3].trim());
			user.setDob(LoadUser.parseDate(user.getUserId(), items[4].trim()));
			user.setStreetAddress(items[5].trim());
			user.setPostCode(items[6].trim());
			user.setCountyName(county);
			user.setGender(items[9].trim());
			user.setPhoneNumber(items[10].trim());
			user.setEmail(items[11].trim());
			user.setCountryCode("UK");
			
			users.add(user);
		}	
		LOG.info("Loaded : " + filename + " with " + users.size() + " users.");
		
		return users;
	}
	
	private static Date parseDate(String userId, String dob) {
		try{
			return dateFormatter.parse(dob);
		}catch (Exception e){
			LOG.warn("Dob :" + dob + " for user :" + userId + " is not a valid date");
			return new Date();
		}
	}	
}
