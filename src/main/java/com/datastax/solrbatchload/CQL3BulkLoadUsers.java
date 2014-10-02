package com.datastax.solrbatchload;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.cassandra.exceptions.InvalidRequestException;
import org.apache.cassandra.io.sstable.CQLSSTableWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.patientcare.model.User;

/**
 * To run this code, you need to have your cluster 'cassandra.yaml' and
 * 'log4j-tools.properties' in the 'src/main/resources' directory.
 * 
 */
public class CQL3BulkLoadUsers{

	private static Logger logger = LoggerFactory.getLogger(CQL3BulkLoadUsers.class);
	
	private DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");
	private DateFormat monthYearFormat = new SimpleDateFormat("yyyyMM");

	private File filePath;
	private String keyspace = "datastax_patient_care_demo";
	private String tableName = keyspace + ".users";

	private String schema = "create table " + tableName + "( "
			+ "user_id text PRIMARY KEY,first_name text,middle_name text,last_name text, passport text, drivers_license text, dob timestamp,street_address text,"
			+ "post_code text,county_name text,state_name text,gender text,phone_number text,email text, country_code text)";
	
	private String INSERT_INTO_USER_TABLE = "INSERT into " + tableName + " (user_id,first_name,middle_name,last_name,passport,drivers_license,dob,street_address,post_code,county_name,gender,phone_number,email,country_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private CQLSSTableWriter writer;

	public CQL3BulkLoadUsers() throws IOException {
		
		logger.info("Using CQL3 Writer");
		
		createDirectories(keyspace, tableName);
		
		this.writer = CQLSSTableWriter.builder()
				.forTable(schema)
				.using(INSERT_INTO_USER_TABLE)
				.inDirectory(getFilePath().getAbsolutePath())
				.build();
	}
	
	public File getFilePath(){
		return this.filePath;
	}

	private void createDirectories(String keyspace, String tableName) {
		File directory = new File(keyspace);
		if (!directory.exists())
			directory.mkdir();
		
		filePath = new File(directory, tableName);
		if (!filePath.exists())
			filePath.mkdir();
	}

	/**
	 * 
	 */
	public void load(List<User> users)
			throws IOException {

		for (User user : users) {
			
			try {
				this.writer.addRow(user.getUserId(), 
						user.getFirstname(),
						user.getMiddlename(),
						user.getLastname(),
						user.getPassport(),
						user.getDriversLicense(),
						user.getDob(),
						user.getStreetAddress(),
						user.getPostCode(),
						user.getCountyName(),
						user.getGender(),
						user.getPhoneNumber(),
						user.getEmail(),
						user.getCountryCode());
			} catch (InvalidRequestException e) {
				e.printStackTrace();
				
				System.exit(5);
			}	
		}
	}
	
	public void finish() throws IOException {
		writer.close();
	}
}