# chr-krenn-fhj-ws2017-sd17-pse
Students SW-Project

## Links
* Virtual Machine: ftp://mesen.fh-joanneum.at/common/Teiniker-Fedora-VMs/Fedora-26-XFCE-2017-09-30.ova
* SCRUM and Task-Management: https://tree.taiga.io/project/p0intr-swd15-pswengi/
## Setup
Clone Repository
<pre>
cd ~/eclipse-workspace
git clone https://github.com/chr-krenn/chr-krenn-fhj-ws2017-sd17-pse
cd chr-krenn-fhj-ws2017-sd17-pse/
</pre>
Use MySQL shell to set up the database, grant privileges, and insert test data:
<pre>
systemctl start mariadb.service
mysql -u root -p
CREATE DATABASE pse;
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER ON `pse`.* TO 'student'@'localhost';
source sql/create.sql
source sql/insert.sql 
exit
</pre>
Set up management user to access WildFly management console at http://localhost:9990
<pre>
~/install/wildfly-10.1.0.Final/bin/add-user.sh
</pre>
## Run
Start WildFly server
<pre>
~/install/wildfly-10.1.0.Final/bin/standalone.sh
</pre>
Deploy App
<pre>
mvn wildfly:deploy
</pre>

## Database Design
![Database overview](doc/database_overview.png)
