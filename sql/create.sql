-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-10-25 07:57:56.446

-- tables
-- Table: community
CREATE TABLE community (
    id int NOT NULL,
    community_id int NOT NULL,
    user_id int NOT NULL,
    CONSTRAINT community_pk PRIMARY KEY (id)
);

-- Table: contact
CREATE TABLE contact (
    id int NOT NULL,
    user_id int NOT NULL,
    contact_id int NOT NULL,
    CONSTRAINT contact_pk PRIMARY KEY (id)
);

-- Table: post
CREATE TABLE post (
    id int NOT NULL,
    user_id int NOT NULL,
    community_id int NOT NULL,
    parent_post_id int NULL,
    text varchar(1024) NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT post_pk PRIMARY KEY (id)
);

-- Table: private_message
CREATE TABLE private_message (
    id int NOT NULL,
    text varchar(1024) NOT NULL,
    from_id int NOT NULL,
    to_id int NOT NULL,
    CONSTRAINT private_message_pk PRIMARY KEY (id)
);

-- Table: users
CREATE TABLE users (
    id int NOT NULL,
    username varchar(64) NOT NULL,
    password varchar(64) NOT NULL,
    role varchar(64) NOT NULL,
    firstname varchar(255) NULL,
    lastname varchar(255) NULL,
    email varchar(255) NULL,
    phone varchar(64) NULL,
    mobile varchar(64) NULL,
    description varchar(256) NULL,
    created timestamp NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: community_users (table: community)
ALTER TABLE community ADD CONSTRAINT community_users FOREIGN KEY community_users (user_id)
    REFERENCES users (id);

-- Reference: contact_users (table: contact)
ALTER TABLE contact ADD CONSTRAINT contact_users FOREIGN KEY contact_users (user_id,contact_id)
    REFERENCES users (id,id);

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
ALTER TABLE private_message ADD CONSTRAINT private_message_users FOREIGN KEY private_message_users (from_id,to_id)
    REFERENCES users (id,id);

-- End of file.

