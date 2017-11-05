-- create users
INSERT INTO users(username, password, role) VALUES ('admin', 'pass', 'admin');
INSERT INTO users(username, password, role) VALUES ('bob', 'pass', 'user');
INSERT INTO users(username, password, role) VALUES ('alice', 'pass', 'user');
INSERT INTO users(username, password, role) VALUES ('frank', 'pass', 'portaladmin');

-- admin has contacts bob, alice
INSERT INTO contact(user_id, contact_id) VALUES (1,2);
INSERT INTO contact(user_id, contact_id) VALUES (1,3);
-- bob has contacts admin, alice, frank
INSERT INTO contact(user_id, contact_id) VALUES (2,1);
INSERT INTO contact(user_id, contact_id) VALUES (2,3);
INSERT INTO contact(user_id, contact_id) VALUES (2,4);
-- alice has contact frank
INSERT INTO contact(user_id, contact_id) VALUES (3,4);
-- frank has contact alice
INSERT INTO contact(user_id, contact_id) VALUES (4,3);

-- write a pm from admin to alice
INSERT INTO private_message(from_id, to_id, text) VALUES (1,3, 'This is a private message.');

-- create communities SWD15 and FH Joanneum
INSERT INTO community(name,status,description) VALUES ('SWD15', 'test', 'test');
INSERT INTO community(name,status,description) VALUES ('FH Joanneum', 'test', 'test');

-- admin posts on community SWD15
INSERT INTO post(user_id, community_id,text) VALUES (1,1,'First post from admin!');

-- admin talks to himself..
INSERT INTO post(user_id, community_id,parent_post_id, text) VALUES (1,1,1,'This is a reply from admin.');

-- bob posts on community SWD15, alice posts a reply
INSERT INTO post(user_id, community_id,text) VALUES (2,1,'This is a post from bob on SWD15!');
INSERT INTO post(user_id, community_id,parent_post_id,text) VALUES (3,1,3,'This is a reply from alice to bob.');

-- create user profiles for admin, bob, alice and frank
INSERT INTO userprofile (user_id, firstname, lastname, email, phone, mobile, description)
VALUES (1, 'Homer', 'Simpson', 'ho.j.simpson@example.com', '555-1234', '31337', 'Technician');

INSERT INTO userprofile (user_id, firstname, lastname, email, phone, mobile, description)
VALUES (2, 'Bob', 'Carter', 'bob.carter@example.com', '555-1235', '065762', 'CIO');

INSERT INTO userprofile (user_id, firstname, lastname, email, phone, mobile, description)
VALUES (3, 'Alice', 'Carroll', 'a.carroll@example.com', '555-1236', '065762', 'Accounting');

INSERT INTO userprofile (user_id, firstname, lastname, email, phone, mobile, description)
VALUES (4, 'Frank', 'Morlar', 'fm@example.com', '555-1237', '065762', 'HR');

-- add status to enumeration
INSERT INTO enumeration (name) VALUES ('OPEN');
INSERT INTO enumeration (name) VALUES ('CLOSED');
INSERT INTO enumeration (name) VALUES ('BLOCKED');
INSERT INTO enumeration (name) VALUES ('INSPECTION');
INSERT INTO enumeration (name) VALUES ('VERIFICATION');

-- add status open to communitys
INSERT INTO enumeration_item (enumeration_id,post_id,user_id,community_id) VALUES (2,NULL,NULL,1);
INSERT INTO enumeration_item (enumeration_id,post_id,user_id,community_id) VALUES (1,NULL,NULL,2);
