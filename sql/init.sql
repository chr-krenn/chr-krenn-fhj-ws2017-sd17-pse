
CREATE DATABASE pse;
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER ON `pse`.* TO 'student'@'localhost';
CREATE DATABASE pse_test;
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER ON `pse_test`.* TO 'student'@'localhost';
use pse;
source sql/create.sql;
source sql/insert.sql;