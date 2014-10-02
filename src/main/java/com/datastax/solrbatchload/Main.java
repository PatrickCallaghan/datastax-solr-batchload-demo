package com.datastax.solrbatchload;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.demo.utils.PropertyHelper;
import com.datastax.demo.utils.Timer;
import com.datastax.sampledata.CreateSSTables;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);
	private static int ONE_MILLION = 1000000;
	private static CQL3BulkLoadUsers bulkLoaderUsers;
	private String urlString = "http://localhost:8983/solr/datastax_patient_care_demo.users";

	public Main() {

		String host = PropertyHelper.getProperty("jmxhost", "localhost");
		int port = Integer.parseInt(PropertyHelper.getProperty("jmxport", "7199"));
		int noOfRows = Integer.parseInt(PropertyHelper.getProperty("noOfRows", "100000"));
		int batches = 0;

		// Initialise loaders
		try {
			bulkLoaderUsers = new CQL3BulkLoadUsers();
		} catch (IOException e) {

			e.printStackTrace();
			System.exit(1);
		}

		if (noOfRows > ONE_MILLION) {
			batches = noOfRows / ONE_MILLION;

			logger.info("Running in " + batches + " batches");

			for (int i = 0; i < batches; i++) {
				logger.info("Running batch " + (i + 1));
				runLoaders(host, port, ONE_MILLION);
			}
			logger.info("Running last batch with " + noOfRows % ONE_MILLION + " rows");
		} else {

			runLoaders(host, port, noOfRows);
		}
		
		//Start indexing with solrJ
		Timer timerIndex = new Timer();
		
		SolrServer solr = new HttpSolrServer(urlString);
		try {
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		
		timerIndex.end();
		logger.info("Finished Indexing in " + timerIndex.getTimeTakenMillis() + " secs for " + noOfRows + " rows.");

		System.exit(0);
	}

	private void runLoaders(String host, int port, int noOfRows) {

		if (noOfRows < 1)
			return;

		createUsers(host, port, noOfRows);
	}

	private void createUsers(String host, int port, int noOfRows) {
		try {
			Timer timer = new Timer();
			timer.start();

			JmxBulkLoader jmxLoader = new JmxBulkLoader(host, port);
			jmxLoader.deleteFiles(bulkLoaderUsers.getFilePath().getAbsolutePath());

			logger.info("Creating SSTables for Users");
			CreateSSTables.createSSTablesUsers(bulkLoaderUsers, noOfRows);
			logger.info("Finished creating SSTables");

			logger.info("Running Bulk Load via JMX for " + bulkLoaderUsers.getFilePath().getAbsolutePath());
			jmxLoader.bulkLoad(bulkLoaderUsers.getFilePath().getAbsolutePath());
			timer.end();
			logger.info("Finished Bulk Load in " + timer.getTimeTakenSeconds() + " secs.");

			jmxLoader.close();
		} catch (Exception e) {
			logger.error("Could not process Users, JMX Loader due to error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}
