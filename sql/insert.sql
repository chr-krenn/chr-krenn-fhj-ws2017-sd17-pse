-- reset sequence generator + truncate for fresh db
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE enumeration;
TRUNCATE enumeration_item;
TRUNCATE private_message;
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
ALTER TABLE private_message AUTO_INCREMENT = 1; 
ALTER TABLE user_community AUTO_INCREMENT = 1; 
ALTER TABLE userprofile AUTO_INCREMENT = 1; 
ALTER TABLE users AUTO_INCREMENT = 1; 

-- add status to enumeration
INSERT INTO enumeration (name) VALUES ('PENDING');		-- 1
INSERT INTO enumeration (name) VALUES ('APPROVED');		-- 2
INSERT INTO enumeration (name) VALUES ('REFUSED'); 		-- 3
INSERT INTO enumeration (name) VALUES ('ADMIN'); 		-- 4
INSERT INTO enumeration (name) VALUES ('PORTALADMIN'); 	-- 5
INSERT INTO enumeration (name) VALUES ('USER'); 		-- 6
INSERT INTO enumeration (name) VALUES ('LIKE'); 		-- 7

-- create user profiles for admin, bob, alice and frank
INSERT INTO userprofile (firstname, lastname, picture, address, plz, city, country, room, team, email, phone, mobile, description)
VALUES ('Homer', 'Simpson', null , 'Greenstreet 8', '8010', 'USA','Springfield', 'USA', '7-G', 'Sierra' 'ho.j.simpson@example.com', '555-1234', '31337', 'Technician');

INSERT INTO userprofile (firstname, lastname, picture, address, plz, city, country, room, team, email, phone, mobile, description)
VALUES ('Bob', 'Carter', null , 'Abbey 12', '51E232', 'London', 'England', '4A','Alpha','bob.carter@example.com', '555-1235', '065762', 'CIO');

INSERT INTO userprofile (firstname, lastname, picture, address, plz, city, country, room, team, email, phone, mobile, description)
VALUES ('Alice', 'Carroll', null, 'Neuholdgasse 123', '1130', 'Vienna', 'Austria', '24B 12','Dev', 'a.carroll@example.com', '555-1236', '065762', 'Accounting');

INSERT INTO userprofile (firstname, lastname, picture, address, plz, city, country, room, team, email, phone, mobile, description)
VALUES ('Frank', 'Morlar', null, 'ølmek', '83ne1', 'Olso', 'Norway', '103','Tester','fm@example.com', '555-1237', '065762', 'HR');

INSERT INTO userprofile (firstname, lastname, picture, address, plz, city, country, room, team, email, phone, mobile, description)
VALUES ('Maria', 'Datenbank', null , 'Decentralization', '00000', 'Kansas', 'USA','404', 'DB', 'gmail@hotmail.gmx', '555-666666', '066000', 'Datenbank');

-- create users
INSERT INTO users(username, password, fk_userprofile) VALUES ('admin', 'pass', 1);
INSERT INTO users(username, password, fk_userprofile) VALUES ('bob', 'pass', 2);
INSERT INTO users(username, password, fk_userprofile) VALUES ('alice', 'pass', 3);
INSERT INTO users(username, password, fk_userprofile) VALUES ('frank', 'pass', 4);
INSERT INTO users(username, password, fk_userprofile) VALUES ('superadmin', 'pass', 5);

-- admin has contacts bob, alice
INSERT INTO contact(user_id, fk_contact_id) VALUES (1,2);
INSERT INTO contact(user_id, fk_contact_id) VALUES (1,3);
-- bob has contacts admin, alice, frank
INSERT INTO contact(user_id, fk_contact_id) VALUES (2,1);
INSERT INTO contact(user_id, fk_contact_id) VALUES (2,3);
INSERT INTO contact(user_id, fk_contact_id) VALUES (2,4);
-- alice has contact frank
INSERT INTO contact(user_id, fk_contact_id) VALUES (3,4);
-- frank has contact alice
INSERT INTO contact(user_id, fk_contact_id) VALUES (4,3);

-- write a pm from admin to alice
INSERT INTO private_message(fk_user_id_sender, fk_user_id_receiver, text) VALUES (1,3, 'This is a private message.'); -- reconsider naming (e.g. private_message, fk_user_receiver_id...)


-- State, in progress
-- create communities SWD15 and FH Joanneum
INSERT INTO community(name, description, enumeration_id, picture) VALUES ('SWD15', 'test', 1, 'randompicture');
INSERT INTO community(name, description, enumeration_id, picture) VALUES ('FH Joanneum', 'test', 2, 'randompicture');
INSERT INTO community(name, description, enumeration_id, picture) VALUES ('PSWEng', 'test', 2, 'randompicture');
INSERT INTO community(name, description, enumeration_id, picture) VALUES ('Social Web', 'test', 3, 'randompicture');

-- set relationship from users and communities
INSERT INTO user_community(users_id, community_id) VALUES (1,1);
INSERT INTO user_community(users_id, community_id) VALUES (2,1);
INSERT INTO user_community(users_id, community_id) VALUES (3,1);
INSERT INTO user_community(users_id, community_id) VALUES (4,1);
INSERT INTO user_community(users_id, community_id) VALUES (1,2);
INSERT INTO user_community(users_id, community_id) VALUES (2,2);
INSERT INTO user_community(users_id, community_id) VALUES (3,2);
INSERT INTO user_community(users_id, community_id) VALUES (4,3);
INSERT INTO user_community(users_id, community_id) VALUES (1,4);


-- admin posts on community SWD15
INSERT INTO post(fk_user_id, fk_community_id,text) VALUES (1,1,'First post from admin!');

-- admin talks to himself..
INSERT INTO post(fk_user_id, fk_community_id,parent_post_id, text) VALUES (1,1,1,'This is a reply from admin.');

-- bob posts on community SWD15, alice posts a reply
INSERT INTO post(fk_user_id, fk_community_id,text) VALUES (2,1,'This is a post from bob on SWD15!');
INSERT INTO post(fk_user_id, fk_community_id,parent_post_id,text) VALUES (3,1,3,'This is a reply from alice to bob.');

-- add roles to users
INSERT INTO enumeration_item (enumeration_id,users_id) VALUES (4,1);
INSERT INTO enumeration_item (enumeration_id,users_id) VALUES (5,2);
INSERT INTO enumeration_item (enumeration_id,users_id) VALUES (6,3);
INSERT INTO enumeration_item (enumeration_id,users_id) VALUES (6,4);
INSERT INTO enumeration_item (enumeration_id,users_id) VALUES (4,5);

-- add likes
INSERT INTO likes (user_id,enumeration_id,post_id) VALUES (2,7,1);
INSERT INTO likes (user_id,enumeration_id,post_id) VALUES (3,7,1);
INSERT INTO likes (user_id,enumeration_id,post_id) VALUES (1,7,2);
INSERT INTO likes (user_id,enumeration_id,post_id) VALUES (3,7,2);
