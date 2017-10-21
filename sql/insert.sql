insert into usertype(id, type) values (1, "admin");
insert into users(id, usertype_id, username, password) values (1, 1, 'frank', 'pass');
insert into profile(id, user_id, description) values (1, 1, "empty profile");
