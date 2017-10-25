-- create users
INSERT INTO users(id, username, password, role) VALUES (1, 'frank', 'pass', 'admin');
INSERT INTO users(id, username, password, role) VALUES (2, 'bob', 'pass', 'user');
INSERT INTO users(id, username, password, role) VALUES (3, 'alice', 'pass', 'user');

-- create some contacts for users, user 1 has contacts 2,3
INSERT INTO contact(id, user_id, contact_id) VALUES (1,1,2);
INSERT INTO contact(id, user_id, contact_id) VALUES (2,1,3);
-- user 2 has contacts 1,3
INSERT INTO contact(id, user_id, contact_id) VALUES (3,2,1);
INSERT INTO contact(id, user_id, contact_id) VALUES (4,2,3);

-- write a pm from admin to alice
INSERT INTO private_message(id, from_id, to_id, text) VALUES (1,1,3, 'this is a private message');

-- create community SWD15
INSERT INTO community(id,name) VALUES (1, 'SWD15');

-- admin posts on community SWD15
INSERT INTO post(id, user_id, community_id,text) VALUES (1,1,1,'first post from admin!');

-- admin talks to himself..
INSERT INTO post(id, user_id, community_id,parent_post_id, text) VALUES (2,1,1,1,'This is a reply from admin');

-- bob posts on community SWD15, alice posts a reply
INSERT INTO post(id, user_id, community_id,text) VALUES (3,2,1,'This is a post from bob!');
INSERT INTO post(id, user_id, community_id,text) VALUES (4,3,1,'This is a reply from alice to bob.');

