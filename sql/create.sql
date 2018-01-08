--<ScriptOptions statementTerminator=";"/>

ALTER TABLE enumeration DROP PRIMARY KEY;

ALTER TABLE userprofile DROP PRIMARY KEY;

ALTER TABLE post DROP PRIMARY KEY;

ALTER TABLE private_message DROP PRIMARY KEY;

ALTER TABLE community DROP PRIMARY KEY;

ALTER TABLE users DROP PRIMARY KEY;

ALTER TABLE contact DROP PRIMARY KEY;

ALTER TABLE post DROP INDEX FKjsft64pkupfvvrs6bd4kyokwi;

ALTER TABLE user_community DROP INDEX FKvx9uvin2jevvy9sychijjx5d;

ALTER TABLE user_community DROP INDEX FK4mv0y3ugdd9ogy9x4ii08ld6c;

ALTER TABLE likes DROP INDEX FKnvx9seeqqyy71bij291pwiwrg;

ALTER TABLE likes DROP INDEX FKowd6f4s7x9f3w50pvlo6x3b41;

ALTER TABLE users DROP INDEX UK_r43af9ap4edm43mmtq01oddj6;

ALTER TABLE post DROP INDEX FKg4x80cty3ha7v00q85djomq6d;

ALTER TABLE users DROP INDEX FKcmombhhasank864ums4swd519;

ALTER TABLE contact DROP INDEX FKbxl6anxo14q097g8cd2e51v55;

ALTER TABLE community DROP INDEX FKk7o792d66bqgrp4x310en9a98;

ALTER TABLE private_message DROP INDEX FKmjbhmvq38xehsknjxd2jl7r0u;

ALTER TABLE enumeration_item DROP INDEX FK2a64bbexb95mu9n8c769rrl0k;

ALTER TABLE post DROP INDEX FK7u19g6f38sqcrn2o2ot9l3c4s;

ALTER TABLE likes DROP INDEX FK5f7eh2n1ynlotnmvjq3a69gwo;

ALTER TABLE private_message DROP INDEX FK9jb3g7xe0cy4sjav6fk34dkhg;

ALTER TABLE enumeration_item DROP INDEX FK9fjj5j3di6uvh1nylihdj0fh2;

DROP TABLE users;

DROP TABLE post;

DROP TABLE contact;

DROP TABLE likes;

DROP TABLE userprofile;

DROP TABLE enumeration_item;

DROP TABLE community;

DROP TABLE enumeration;

DROP TABLE private_message;

DROP TABLE user_community;

CREATE TABLE users (
	id INT NOT NULL,
	password VARCHAR(255),
	username VARCHAR(255) NOT NULL,
	fk_userprofile INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE post (
	id INT NOT NULL,
	created DATETIME,
	text VARCHAR(1024),
	fk_community_id INT,
	parent_post_id INT,
	fk_user_id INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE contact (
	id INT NOT NULL,
	fk_contact_id INT,
	user_id INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE likes (
	enumeration_id INT NOT NULL,
	user_id INT NOT NULL,
	post_id INT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE userprofile (
	id INT NOT NULL,
	address VARCHAR(255),
	city VARCHAR(255),
	country VARCHAR(255),
	description VARCHAR(255),
	email VARCHAR(255),
	firstname VARCHAR(255),
	lastname VARCHAR(255),
	mobile VARCHAR(255),
	phone VARCHAR(255),
	picture MEDIUMBLOB,
	plz VARCHAR(255),
	room VARCHAR(255),
	team VARCHAR(255),
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE enumeration_item (
	enumeration_id INT NOT NULL,
	users_id INT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE community (
	id INT NOT NULL,
	description LONGTEXT,
	name VARCHAR(255) NOT NULL,
	picture TINYBLOB,
	enumeration_id INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE enumeration (
	id INT NOT NULL,
	name VARCHAR(255),
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE private_message (
	id INT NOT NULL,
	text VARCHAR(255),
	fk_user_id_receiver INT,
	fk_user_id_sender INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE user_community (
	community_id INT NOT NULL,
	users_id INT NOT NULL
) ENGINE=InnoDB;

CREATE INDEX FKjsft64pkupfvvrs6bd4kyokwi ON post (fk_community_id ASC);

CREATE INDEX FKvx9uvin2jevvy9sychijjx5d ON user_community (community_id ASC);

CREATE INDEX FK4mv0y3ugdd9ogy9x4ii08ld6c ON user_community (users_id ASC);

CREATE INDEX FKnvx9seeqqyy71bij291pwiwrg ON likes (user_id ASC);

CREATE INDEX FKowd6f4s7x9f3w50pvlo6x3b41 ON likes (post_id ASC);

CREATE UNIQUE INDEX UK_r43af9ap4edm43mmtq01oddj6 ON users (username ASC);

CREATE INDEX FKg4x80cty3ha7v00q85djomq6d ON post (fk_user_id ASC);

CREATE INDEX FKcmombhhasank864ums4swd519 ON users (fk_userprofile ASC);

CREATE INDEX FKbxl6anxo14q097g8cd2e51v55 ON contact (user_id ASC);

CREATE INDEX FKk7o792d66bqgrp4x310en9a98 ON community (enumeration_id ASC);

CREATE INDEX FKmjbhmvq38xehsknjxd2jl7r0u ON private_message (fk_user_id_receiver ASC);

CREATE INDEX FK2a64bbexb95mu9n8c769rrl0k ON enumeration_item (enumeration_id ASC);

CREATE INDEX FK7u19g6f38sqcrn2o2ot9l3c4s ON post (parent_post_id ASC);

CREATE INDEX FK5f7eh2n1ynlotnmvjq3a69gwo ON likes (enumeration_id ASC);

CREATE INDEX FK9jb3g7xe0cy4sjav6fk34dkhg ON private_message (fk_user_id_sender ASC);

CREATE INDEX FK9fjj5j3di6uvh1nylihdj0fh2 ON enumeration_item (users_id ASC);

