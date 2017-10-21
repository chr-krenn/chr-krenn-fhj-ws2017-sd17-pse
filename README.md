# chr-krenn-fhj-ws2017-sd17-pse
Students SW-Project

## Links
* Virtual Machine: ftp://mesen.fh-joanneum.at/common/Teiniker-Fedora-VMs/Fedora-26-XFCE-2017-09-30.ova
* SCRUM and Task-Management: https://tree.taiga.io/project/p0intr-swd15-pswengi/
## Setup
As normal user (student):
<pre>
cd ~/eclipse-workspace
git clone https://github.com/chr-krenn/chr-krenn-fhj-ws2017-sd17-pse
cd chr-krenn-fhj-ws2017-sd17-pse/
systemctl start mariadb.service
</pre>
Use MySQL shell to set up the database and grant privileges:
<pre>
mysql -u root -p
CREATE DATABASE pse;
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER ON `pse`.* TO 'student'@'localhost';
exit
</pre>
<pre>
mysql pse -u student -p < sql/create.sql
</pre>

## Database Design
![Database overview](doc/database_overview.png)
