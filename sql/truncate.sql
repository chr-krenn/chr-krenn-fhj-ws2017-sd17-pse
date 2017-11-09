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
