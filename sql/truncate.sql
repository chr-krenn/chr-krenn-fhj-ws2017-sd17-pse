-- Truncate SQL
-- emtpys tables
-- be careful

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE enumeration;
TRUNCATE enumeration_item;
TRUNCATE privateMessage;
TRUNCATE contact;
TRUNCATE post;
TRUNCATE user_community;
TRUNCATE userprofile;
TRUNCATE users;
TRUNCATE community;
SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE community AUTO_INCREMENT = 1; 
ALTER TABLE contact AUTO_INCREMENT = 1;  
ALTER TABLE enumeration AUTO_INCREMENT = 1; 
ALTER TABLE enumeration_item AUTO_INCREMENT = 1; 
ALTER TABLE post AUTO_INCREMENT = 1; 
ALTER TABLE privateMessage AUTO_INCREMENT = 1; 
ALTER TABLE user_community AUTO_INCREMENT = 1; 
ALTER TABLE userprofile AUTO_INCREMENT = 1; 
ALTER TABLE users AUTO_INCREMENT = 1; 

