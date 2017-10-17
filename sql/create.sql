-- tables
-- Table: news_feed
CREATE TABLE news_feed (
    profile_id int NOT NULL,
    posting_id int NOT NULL,
    CONSTRAINT news_feed_pk PRIMARY KEY (profile_id,posting_id)
);

-- Table: posting
CREATE TABLE posting (
    id int NOT NULL,
    created timestamp NOT NULL,
    text text NOT NULL,
    user_id int NOT NULL,
    likes int NOT NULL,
    CONSTRAINT posting_pk PRIMARY KEY (id)
);

-- Table: profile
CREATE TABLE profile (
    id int NOT NULL,
    address varchar(255) NOT NULL,
    telephone varchar(64) NOT NULL,
    mobile varchar(64) NOT NULL,
    mail varchar(255) NOT NULL,
    fax varchar(64) NOT NULL,
    description text NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT profile_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE user (
    id int NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    usertype_id int NOT NULL,
    profile_id int NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

-- Table: usertype
CREATE TABLE usertype (
    id int NOT NULL,
    type varchar(64) NOT NULL,
    CONSTRAINT usertype_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: news_feed_posting (table: news_feed)
ALTER TABLE news_feed ADD CONSTRAINT news_feed_posting FOREIGN KEY news_feed_posting (posting_id)
    REFERENCES posting (id);

-- Reference: news_feed_profile (table: news_feed)
ALTER TABLE news_feed ADD CONSTRAINT news_feed_profile FOREIGN KEY news_feed_profile (posting_id)
    REFERENCES profile (id);

-- Reference: posting_user (table: posting)
ALTER TABLE posting ADD CONSTRAINT posting_user FOREIGN KEY posting_user (user_id)
    REFERENCES user (id);

-- Reference: user_profile (table: user)
ALTER TABLE user ADD CONSTRAINT user_profile FOREIGN KEY user_profile (profile_id)
    REFERENCES profile (id);

-- Reference: user_usertype (table: user)
ALTER TABLE user ADD CONSTRAINT user_usertype FOREIGN KEY user_usertype (usertype_id)
    REFERENCES usertype (id);

-- End of file.

