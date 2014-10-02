package com.datastax.demo.utils;

import java.util.Arrays;
import java.util.List;

public class SampleData {
	
	static public List<String> counties = Arrays.asList("Kent","Bournemouth","Lincolnshire","West Midlands","Northamptonshire",
"Southampton","Tyne & Wear","South Gloucestershire","Derbyshire","Dumfries and Galloway","West Lothian","North Somerset","Norfolk","Hampshire",
"Devon","Yorkshire", "South Denbighshire", "Surrey","Isle of Wight","West Sussex","Western Isles","North Eart Lincolnshire","Cornwall","Berkshire",
"Hertfordshire","Yorkshire East (North Humbers)","Hereford and Worcester","Shropshire","Darlington","Wiltshire",
"Cambridgeshire","West Yorkshire","Essex","Nottinghamshire","Ceredigion","East Sussex","Staffordshire","Rhondda Cynon Taff",
"Gloucestershire","South Yorkshire","Warwickshire","Cheshire","Greater Manchester","Middlesbrough","Stirling","Cumbria","Stoke-on-Trent",
"County Durham","Lancashire","Tyne and Wear","Glasgow City","Merseyside","Aberdeenshire","Dunbartonshire","North Yorkshire","Somerset",
"Bristol","Suffolk","Neath Port Talbot","City of Edinburgh","Oxfordshire","Northumberland","Moray","Yorkshire, West","Gwynedd",
"West Dunbart","Merthyr Tydfil","Dorset","Caerphilly","North Lincolnshire","Fife","Carmarthenshire","Brighton and Hove","Conwy",
"Leicestershire","North Ayrshire","Flintshire","South Lanarkshire","E Riding of Yorkshire","Isle of Anglesey","East Renfrewshire",
"York","Newport","East Lothian","Leicester","Bedfordshire","North Lanarkshire","Vale of Glamorgan","Clackmannanshire",
"East Dunbartonshire","Highland","South Ayrshire","Stockton-on-Tees","Falkirk","Inverclyde","Perth and Kinross","Dundee City","Wrexham",
"Bath Avon","East Ayrshire","Greater London","Greater London","Greater London","Greater London","Greater London","Greater London",
"Greater London","Greater London");
	
	static public List<String> allergies = Arrays.asList("Hayfever", "Penicilin", "ibprofen", "Paracetamol", "Nuts", "Dairy", "Glutcose",
			"wheat", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
	
	static public List<String> notes = Arrays.asList("Showing signs of forgetfulness",
			"Serve reaction to strong antibiotics",
			"Very Boring",
			"Always something wrong with this guy",
			"","","","","","","","","","");
	
	static public List<String> medications = Arrays.asList("Codine", "Valium", "Prozac", "Antibiotics", "Curanail", "Tension Heaches Pills,"
			+ "", "", "", "", "", "", "", "", "", "", "", "");
	
	static public List<String> doses = Arrays.asList("10mg", "50mg", "100mg");

	public static List<String> severities = Arrays.asList("Slight", "Moderate", "Severe");

	public static List<String> reasons = Arrays.asList("Headache", "Tummy Ache", "Constipation", "Stress", "Fungal infection", "Indigestion", "Cancer", "IBS", "Cold", "Flu", "Problem with medication");
	
}
