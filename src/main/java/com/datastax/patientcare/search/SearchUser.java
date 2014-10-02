package com.datastax.patientcare.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.patientcare.model.User;

public class SearchUser {

	private static Logger logger = LoggerFactory.getLogger(SearchUser.class);

	private SolrServer solr;
	
	public SearchUser() {
		String urlString = "http://localhost:8983/solr/datastax_patient_care_demo.users";
		this.solr = new HttpSolrServer(urlString);
	}
	
	public List<User> getUsers(String type, String text){
		
		List<User> users = new ArrayList<User>();
		
		String queryString = type + ":*" + text;

		SolrQuery query = new SolrQuery();
		query.set("q", queryString);
		query.addFacetField("county_name");

		QueryResponse response;
		try {
			response = solr.query(query);
			SolrDocumentList list = response.getResults();

			logger.info(list.toString());
			
			for (SolrDocument document : list) {
				
				logger.info("User ID : " + document.getFieldValue("user_id"));
				User user = new User();
				user.setUserId((String) document.getFieldValue("user_id"));
				user.setFirstname((String) document.getFieldValue("first_name"));
				user.setLastname((String) document.getFieldValue("last_name"));
				user.setPassport((String) document.getFieldValue("passport"));
				
				users.add(user);
			}
			
			List<FacetField> facetFields = response.getFacetFields();
		
			for (FacetField facetField : facetFields){
				List<Count> values = facetField.getValues();
				
				for (Count count : values){
					logger.info(count.getName() + " " + count.getCount());
				}
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return users;
	}
}
