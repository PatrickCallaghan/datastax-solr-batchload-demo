create keyspace if not exists datastax_patient_care_demo WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1' };

use datastax_patient_care_demo; 
 
create table if not exists users(
	user_id text PRIMARY KEY,
	first_name text,
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
);

truncate users;

