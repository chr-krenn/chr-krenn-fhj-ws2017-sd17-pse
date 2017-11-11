-- create user profiles for admin, bob, alice and frank
INSERT INTO userprofile (firstname, lastname, location, team, email, phone, mobile, description)
VALUES ('Homer', 'Simpson', 'Springfield', '7-G', 'ho.j.simpson@example.com', '555-1234', '31337', 'Technician');

INSERT INTO userprofile (firstname, lastname, location, team, email, phone, mobile, description)
VALUES ('Bob', 'Carter', 'England','Alpha','bob.carter@example.com', '555-1235', '065762', 'CIO');

INSERT INTO userprofile (firstname, lastname, location, team, email, phone, mobile, description)
VALUES ('Alice', 'Carroll', 'Austria', 'Dev', 'a.carroll@example.com', '555-1236', '065762', 'Accounting');

INSERT INTO userprofile (firstname, lastname, location, team, email, phone, mobile, description)
VALUES ('Frank', 'Morlar', 'Norway', 'Tester','fm@example.com', '555-1237', '065762', 'HR');

INSERT INTO userprofile (firstname, lastname, location, team, email, phone, mobile, description)
VALUES ('Maria', 'Datenbank', 'Decentralization', 'DB', 'gmail@hotmail.gmx', '555-666666', '066000', 'Datenbank');

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
INSERT INTO privateMessage(FK_UserID_sender, FK_UserID_receiver, text) VALUES (1,3, 'This is a private message.'); -- reconsider naming (e.g. private_message, fk_user_receiver_id...)

-- create communities SWD15 and FH Joanneum
INSERT INTO community(name,description) VALUES ('SWD15', 'test');
INSERT INTO community(name,description) VALUES ('FH Joanneum', 'test');

-- admin posts on community SWD15
INSERT INTO post(fk_user_id, fk_community_id,text) VALUES (1,1,'First post from admin!');

-- admin talks to himself..
INSERT INTO post(fk_user_id, fk_community_id,parent_post_id, text) VALUES (1,1,1,'This is a reply from admin.');

-- bob posts on community SWD15, alice posts a reply
INSERT INTO post(fk_user_id, fk_community_id,text) VALUES (2,1,'This is a post from bob on SWD15!');
INSERT INTO post(fk_user_id, fk_community_id,parent_post_id,text) VALUES (3,1,3,'This is a reply from alice to bob.');

-- add status to enumeration
INSERT INTO enumeration (name) VALUES ('OPEN'); -- 1
INSERT INTO enumeration (name) VALUES ('CLOSED'); -- 2
INSERT INTO enumeration (name) VALUES ('BLOCKED'); -- 3
INSERT INTO enumeration (name) VALUES ('INSPECTION'); -- 4
INSERT INTO enumeration (name) VALUES ('VERIFICATION'); -- 5
INSERT INTO enumeration (name) VALUES ('ADMIN'); -- 6
INSERT INTO enumeration (name) VALUES ('PORTALADMIN'); -- 7
INSERT INTO enumeration (name) VALUES ('USER'); -- 8

-- add status open to communitys
INSERT INTO enumeration_item (enumeration_id,post_id,user_id,community_id) VALUES (2,NULL,NULL,1);
INSERT INTO enumeration_item (enumeration_id,post_id,user_id,community_id) VALUES (1,NULL,NULL,2);

-- add status to users
INSERT INTO enumeration_item (enumeration_id,post_id,user_id,community_id) VALUES (6,NULL,1,NULL);
INSERT INTO enumeration_item (enumeration_id,post_id,user_id,community_id) VALUES (8,NULL,2,NULL);
INSERT INTO enumeration_item (enumeration_id,post_id,user_id,community_id) VALUES (8,NULL,3,NULL);
INSERT INTO enumeration_item (enumeration_id,post_id,user_id,community_id) VALUES (7,NULL,4,NULL);
