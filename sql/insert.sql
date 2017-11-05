-- create users
INSERT INTO users(id, username, password, role) VALUES (1, 'admin', 'pass', 'admin');
INSERT INTO users(id, username, password, role) VALUES (2, 'bob', 'pass', 'user');
INSERT INTO users(id, username, password, role) VALUES (3, 'alice', 'pass', 'user');
INSERT INTO users(id, username, password, role) VALUES (4, 'frank', 'pass', 'portaladmin');

-- admin has contacts bob, alice
INSERT INTO contact(id, user_id, contact_id) VALUES (1,1,2);
INSERT INTO contact(id, user_id, contact_id) VALUES (2,1,3);
-- bob has contacts admin, alice, frank
INSERT INTO contact(id, user_id, contact_id) VALUES (3,2,1);
INSERT INTO contact(id, user_id, contact_id) VALUES (4,2,3);
INSERT INTO contact(id, user_id, contact_id) VALUES (5,2,4);
-- alice has contact frank
INSERT INTO contact(id, user_id, contact_id) VALUES (6,3,4);
-- frank has contact alice
INSERT INTO contact(id, user_id, contact_id) VALUES (7,4,3);

-- write a pm from admin to alice
INSERT INTO private_message(id, from_id, to_id, text) VALUES (1,1,3, 'This is a private message.');

-- create communities SWD15 and FH Joanneum
INSERT INTO community(id,name) VALUES (1, 'SWD15');
INSERT INTO community(id,name) VALUES (2, 'FH Joanneum');

-- admin posts on community SWD15
INSERT INTO post(id, user_id, community_id,text) VALUES (1,1,1,'First post from admin!');

-- admin talks to himself..
INSERT INTO post(id, user_id, community_id,parent_post_id, text) VALUES (2,1,1,1,'This is a reply from admin.');

-- bob posts on community SWD15, alice posts a reply
INSERT INTO post(id, user_id, community_id,text) VALUES (3,2,1,'This is a post from bob on SWD15!');
INSERT INTO post(id, user_id, community_id,parent_post_id,text) VALUES (4,3,1,3,'This is a reply from alice to bob.');

-- create user profiles for admin, bob, alice and frank
INSERT INTO userprofile (id, user_id, firstname, lastname, email, phone, mobile, description) 
VALUES (1,1, 'Homer', 'Simpson', 'ho.j.simpson@example.com', '555-1234', '31337', 'Technician');

INSERT INTO userprofile (id, user_id, firstname, lastname, email, phone, mobile, description)
VALUES (2,2, 'Bob', 'Carter', 'bob.carter@example.com', '555-1235', '31338', 'CIO');

INSERT INTO userprofile (id, user_id, firstname, lastname, email, phone, mobile, description)
VALUES (3,3, 'Alice', 'Carroll', 'a.carroll@example.com', '555-1236','31338',  'Accounting');

INSERT INTO userprofile (id, user_id, firstname, lastname, email, phone, mobile, description)
VALUES (4,4, 'Frank', 'Morlar', 'fm@example.com', '555-1237', '31338', 'HR');

-- add status to enumeration
INSERT INTO enumeration (id,name) VALUES (1,'OPEN');
INSERT INTO enumeration (id,name) VALUES (2,'CLOSED');
INSERT INTO enumeration (id,name) VALUES (3,'BLOCKED');
INSERT INTO enumeration (id,name) VALUES (4,'INSPECTION');
INSERT INTO enumeration (id,name) VALUES (5,'VERIFICATION');

-- add status open to communitys
INSERT INTO enumeration_item (id,enumeration_id,post_id,user_id,community_id) VALUES (1,2,NULL,NULL,1);
INSERT INTO enumeration_item (id,enumeration_id,post_id,user_id,community_id) VALUES (2,1,NULL,NULL,2);
