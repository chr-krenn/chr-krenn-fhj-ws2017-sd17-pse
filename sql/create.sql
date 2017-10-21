-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-10-21 19:16:25.404

-- tables
-- Table: feed
CREATE TABLE feed (
    id int NOT NULL,
    profile_id int NOT NULL,
    posting_id int NOT NULL,
    CONSTRAINT feed_pk PRIMARY KEY (id)
);

-- Table: posting
CREATE TABLE posting (
    id int NOT NULL,
    user_id int NOT NULL,
    text text NOT NULL,
    likes int NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT posting_pk PRIMARY KEY (id)
);

-- Table: profile
CREATE TABLE profile (
    id int NOT NULL,
    user_id int NOT NULL,
    address varchar(255) NULL,
    phone varchar(64) NULL,
    mobile varchar(64) NULL,
    mail varchar(255) NULL,
    fax varchar(64) NULL,
    description text NULL,
    created timestamp NOT NULL,
    CONSTRAINT profile_pk PRIMARY KEY (id)
);

-- Table: users
CREATE TABLE users (
    id int NOT NULL,
    usertype_id int NOT NULL,
    first_name varchar(255) NULL,
    last_name varchar(255) NULL,
    email varchar(255) NULL,
    username varchar(64) NOT NULL,
    password varchar(64) NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

-- Table: usertype
CREATE TABLE usertype (
    id int NOT NULL,
    type varchar(64) NOT NULL,
    CONSTRAINT usertype_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: news_feed_posting (table: feed)
ALTER TABLE feed ADD CONSTRAINT news_feed_posting FOREIGN KEY news_feed_posting (posting_id)
    REFERENCES posting (id);

-- Reference: news_feed_profile (table: feed)
ALTER TABLE feed ADD CONSTRAINT news_feed_profile FOREIGN KEY news_feed_profile (profile_id)
    REFERENCES profile (id);

-- Reference: posting_user (table: posting)
ALTER TABLE posting ADD CONSTRAINT posting_user FOREIGN KEY posting_user (user_id)
    REFERENCES users (id);

-- Reference: profile_users (table: profile)
ALTER TABLE profile ADD CONSTRAINT profile_users FOREIGN KEY profile_users (user_id)
    REFERENCES users (id);

-- Reference: user_usertype (table: users)
ALTER TABLE users ADD CONSTRAINT user_usertype FOREIGN KEY user_usertype (usertype_id)
    REFERENCES usertype (id);

-- End of file.

