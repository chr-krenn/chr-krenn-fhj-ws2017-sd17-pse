
CREATE DATABASE pse;
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER ON `pse`.* TO 'student'@'localhost';
use pse;
source sql/create.sql
source sql/insert.sql
