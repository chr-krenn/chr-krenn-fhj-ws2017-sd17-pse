-- tables
-- Table: community
CREATE TABLE community (
    id int AUTO_INCREMENT,
    name varchar(256) NOT NULL,
    status varchar(64) NOT NULL,
    description varchar(256),
    CONSTRAINT community_pk PRIMARY KEY (id)
);

-- Table: contact
CREATE TABLE contact (
    id int AUTO_INCREMENT,
    user_id int NOT NULL,
    contact_id int NOT NULL,
    CONSTRAINT contact_pk PRIMARY KEY (id)
);

-- Table: post
CREATE TABLE post (
    id int AUTO_INCREMENT,
    user_id int NOT NULL,
    community_id int NOT NULL,
    parent_post_id int NULL,
    text varchar(1024) NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT post_pk PRIMARY KEY (id)
);

-- Table: private_message
CREATE TABLE private_message (
    id int AUTO_INCREMENT,
    from_id int NOT NULL,
    to_id int NOT NULL,
    text varchar(1024) NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT private_message_pk PRIMARY KEY (id)
);

-- Table: user_community
CREATE TABLE user_community (
    id int AUTO_INCREMENT,
    user_id int NOT NULL,
    community_id int NOT NULL,
    CONSTRAINT user_community_pk PRIMARY KEY (id)
);

-- Table: userprofile
CREATE TABLE userprofile (
    id int AUTO_INCREMENT,
    firstname varchar(64) NOT NULL,
    lastname varchar(64) NOT NULL,
    email varchar(256) NOT NULL,
    phone varchar(256) NOT NULL,
    mobile varchar(256) NOT NULL,
    description varchar(1024) NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT userprofile_pk PRIMARY KEY (id)
);

-- Table: users
CREATE TABLE users (
    id int AUTO_INCREMENT,
    username varchar(64) NOT NULL,
    password varchar(64) NOT NULL,
    role varchar(64) NOT NULL,
    fk_userprofile int,
    created timestamp NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

-- Table: enumeration
CREATE TABLE enumeration (
	id int AUTO_INCREMENT,
	name varchar(64) NOT NULL,
	CONSTRAINT enumeration_pk PRIMARY KEY (id)
);

-- Table: enumeration_item
CREATE TABLE enumeration_item (
	id int AUTO_INCREMENT,
	enumeration_id int NOT NULL,
	post_id int,
	user_id int,
	community_id int,
	CONSTRAINT enumeration_item_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: contact_users (table: contact)
ALTER TABLE contact ADD CONSTRAINT contact_users FOREIGN KEY contact_users (user_id)
    REFERENCES users (id);

-- Reference: post_community (table: post)
ALTER TABLE post ADD CONSTRAINT post_community FOREIGN KEY post_community (community_id)
    REFERENCES community (id);

-- Reference: post_post (table: post)
ALTER TABLE post ADD CONSTRAINT post_post FOREIGN KEY post_post (parent_post_id)
    REFERENCES post (id);

-- Reference: posting_user (table: post)
ALTER TABLE post ADD CONSTRAINT posting_user FOREIGN KEY posting_user (user_id)
    REFERENCES users (id);

-- Reference: private_message_users (table: private_message)
ALTER TABLE private_message ADD CONSTRAINT private_message_users FOREIGN KEY private_message_users (from_id)
    REFERENCES users (id);

-- Reference: user_community_community (table: user_community)
ALTER TABLE user_community ADD CONSTRAINT user_community_community FOREIGN KEY user_community_community (community_id)
    REFERENCES community (id);

-- Reference: user_community_users (table: user_community)
ALTER TABLE user_community ADD CONSTRAINT user_community_users FOREIGN KEY user_community_users (user_id)
    REFERENCES users (id);

-- Reference: userprofile_users (table: users)
ALTER TABLE users ADD CONSTRAINT user_userprofile FOREIGN KEY users (fk_userprofile) 
	REFERENCES userprofile (id);

-- Reference: enumeration_item
ALTER TABLE enumeration_item ADD CONSTRAINT enumeration_item_enumeration 
	FOREIGN KEY enumeration_item_enumeration (enumeration_id) REFERENCES enumeration (id);
	
ALTER TABLE enumeration_item ADD CONSTRAINT enumeration_item_user 
	FOREIGN KEY enumeration_item_user (user_id) REFERENCES users (id);
	
ALTER TABLE enumeration_item ADD CONSTRAINT enumeration_item_post 
	FOREIGN KEY enumeration_item_post (post_id) REFERENCES post (id);

ALTER TABLE enumeration_item ADD CONSTRAINT enumeration_item_community 
	FOREIGN KEY enumeration_item_community (community_id) REFERENCES community (id);
    
-- End of file.

