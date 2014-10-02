//Start cassandra in Solr mode. 

Start by creating the schema

mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaSetupSingle"

Within the project structure in 

src/main/resources/solr 

there is a 

commands.txt 

file which lists the commands needs to create a Solr Core for the Users table in Cassandra. Note - the IP's addresses will be different depending on your installation. These commands need to run from a terminal from the 

src/main/resources/solr 

directory. You can also use the browser if you are comfortable doing it that way. Running these commands in order will allow us to query the users table in Cassandra. For demo purposes this can be done through the Solr Web Admin interface at 

http://localhost:8983/solr/#/

Solr is now started with with autocommit pretty much off. 

Now batch load the data and commit to Solr. 

mvn clean compile exec:java -Dexec.mainClass="com.datastax.solrbatchload.Main"