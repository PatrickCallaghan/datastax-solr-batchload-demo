create keyspace if not exists datastax_patient_care_demo WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1' };

use datastax_patient_care_demo; 
 
create table users(
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

create table medications(
	user_id text,
	start_date timestamp,
	medication_name text,
	end_date timestamp,
	dose text,
	notes text,	
	PRIMARY KEY(user_id, start_date)
)WITH CLUSTERING ORDER BY (start_date desc);	

//Insert into medications(user_id, start_date, medication_name, end_date, dose, notes) //values (?,?,?,?,?,?)
	
create table gpvisits(
	user_id text,
	date_time timestamp,
	gp_name text,
	gp_address text,
	reason text,
	gp_notes text,	
	PRIMARY KEY (user_id, date_time)
) WITH CLUSTERING ORDER BY (date_time desc);

//Insert into gpvisits (user_id, date_time, gp_name, gp_address, reason, gp_notes)
//values (?,?,?,?,?,?)

create table allergies(
	user_id text,
	allergy_name text,
	allergy_type text,
	severity text,
	PRIMARY KEY (user_id, allergy_name)
);

//Insert into allergies (user_id, allergy_name, allergy_type, severity)
//values (?,?,?,?)

truncate users;
truncate medications;
truncate gpvisits;
truncate allergies;

//Updates for demo
update users set first_name = 'Eileen', last_name = 'Callaghan', email='eileen@callaghan.com', gender ='F', passport='1234567890', dob='19391227' where user_id='U1'

delete from gpvisits where user_id='U1';
delete from medications where user_id='U1';
delete from allergies where user_id='U1';

Insert into gpvisits (user_id, date_time, gp_name, gp_address, reason, gp_notes) values ('U1','20090624','Dr Fine','123 Feel Good Rd, Finglas','Cramps','Indigestion and bloating causing pain');
Insert into medications(user_id, start_date, medication_name, end_date, dose, notes) values ('U1', '20090624', 'IBS Relief', '20100304', '1 before meals', 'Check back in 3 months');
Insert into gpvisits (user_id, date_time, gp_name, gp_address, reason, gp_notes) values ('U1','20091102','Dr Different','123 Feel Better Lane, Waterford','Cramps','Send for tests');
Insert into gpvisits (user_id, date_time, gp_name, gp_address, reason, gp_notes) values ('U1','20110202','Dr Fine','123 Feel Good Rd, Finglas','Blood pressure','Blood pressure checked. Still high. Check in 1 month');
Insert into gpvisits (user_id, date_time, gp_name, gp_address, reason, gp_notes) values ('U1','20110302','Dr Fine','123 Feel Good Rd, Finglas','Blood pressure','Blood pressure still high - prescribe tablets');
Insert into medications(user_id, start_date, medication_name, end_date, dose, notes) values ('U1', '20110624', 'Blood Pressure', null, '1 in the morning', 'Check back in 6 months');
Insert into gpvisits (user_id, date_time, gp_name, gp_address, reason, gp_notes) values ('U1','20110302','Dr Fine','123 Feel Good Rd, Finglas','Cramps and Diarrhea','Test for Allergies');
Insert into gpvisits (user_id, date_time, gp_name, gp_address, reason, gp_notes) values ('U1','20110402','Dr Fine','123 Feel Good Rd, Finglas','Results of Allergy Tests','Gluten intolerance found. Suggested gluten free diet and check back in 1 month');
Insert into gpvisits (user_id, date_time, gp_name, gp_address, reason, gp_notes) values ('U1','20110502','Dr Fine','123 Feel Good Rd, Finglas','Check up','Gluten free diet going well');

Insert into allergies (user_id, allergy_name, allergy_type, severity)
values ('U1', 'Nuts', 'All nuts', 'Slight');
Insert into allergies (user_id, allergy_name, allergy_type, severity)
values ('U1', 'Coeliac', 'Gluten free diet is required', 'Moderate');
Insert into allergies (user_id, allergy_name, allergy_type, severity)
values ('U1', 'Painkillers', 'Bad reaction to strong painkillers', 'Severe');
