package com.datastax.patientcare.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.patientcare.model.User;

public class PatientCareDao {

	private static Logger logger = LoggerFactory.getLogger( PatientCareDao.class );	
	private Session session;
	
	private DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
	private static String keyspaceName = "datastax_patient_care_demo";
	private static String userTable = keyspaceName + ".users";
	private static String gpVisitsTable = keyspaceName + ".gpvisits";
	private static String medicationsTable = keyspaceName + ".medications";
	private static String allergiesTable = keyspaceName + ".allergies";
	
	private String SELECT_USER = "select  * from " + userTable + " where user_id = ?";
	private String SELECT_GPVISITS = "select  * from " + gpVisitsTable + " where user_id = ?";
	private String SELECT_MEDICATIONS = "select  * from " + medicationsTable + " where user_id = ?";
	private String SELECT_ALLERGIES = "select  * from " + allergiesTable + " where user_id = ?";
		
	private PreparedStatement selectUserStmt;
	private PreparedStatement selectGPVisitsStmt;
	private PreparedStatement selectMedicationsStmt;
	private PreparedStatement selectAllergiesStmt;
	
	public PatientCareDao(String[] contactPoints) {

		Cluster cluster = Cluster.builder()				
				.addContactPoints(contactPoints)
				.build();
		
		this.session = cluster.connect();

		this.selectUserStmt = session.prepare(SELECT_USER);
		this.selectGPVisitsStmt = session.prepare(SELECT_GPVISITS);
		this.selectMedicationsStmt = session.prepare(SELECT_MEDICATIONS);
		this.selectAllergiesStmt = session.prepare(SELECT_ALLERGIES);
		
	}
/*
 * 	first_name text,
	middle_name text,
	last_name text,
	passport text, 
	drivers_license text, 
	dob timestamp,
	street_address text,
	post_code text,
	county_name text,
	gender text,
	phone_number text,
	email text,
	country_code text
	
 */
	public User getUser(String userId) {
		
		BoundStatement boundStmt = new BoundStatement(selectUserStmt);
		ResultSet resultSet = session.execute(boundStmt.bind(userId));
				
		Row row = resultSet.one();
		
		User user = new User();
	  	user.setUserId(userId);
		user.setFirstname(row.getString("first_name"));
		user.setMiddlename(row.getString("middle_name"));
		user.setLastname(row.getString("last_name"));
		user.setPassport(row.getString("passport"));
		user.setDriversLicense(row.getString("drivers_license"));
		user.setDob(row.getDate("dob"));
		user.setStreetAddress(row.getString("street_address"));
		user.setPostCode(row.getString("post_code"));
		user.setCountyName(row.getString("county_name"));
		user.setGender(row.getString("gender"));
		user.setPhoneNumber(row.getString("phone_number"));
		user.setEmail(row.getString("email"));
		user.setCountryCode(row.getString("country_code"));
		
		return user;
	}
}
