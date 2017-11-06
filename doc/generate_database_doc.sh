#!/bin/sh
java -jar ../lib/schemaSpy_5.0.0.jar -t mysql -db pse -host localhost -u student -p student -o ./db_schema -dp ../lib/mysql-connector-java-5.1.44-bin.jar -s public -noads
cp db_schema/diagrams/summary//relationships.real.large.png .
