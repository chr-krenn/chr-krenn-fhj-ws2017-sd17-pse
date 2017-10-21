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
    created timestamp NOT NULL,
    text text NOT NULL,
    likes int NOT NULL,
    CONSTRAINT posting_pk PRIMARY KEY (id)
);

-- Table: profile
CREATE TABLE profile (
    id int NOT NULL,
    address varchar(255) NOT NULL,
    phone varchar(64) NOT NULL,
    mobile varchar(64) NOT NULL,
    mail varchar(255) NOT NULL,
    fax varchar(64) NOT NULL,
    description text NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT profile_pk PRIMARY KEY (id)
);

-- Table: users
CREATE TABLE users (
    id int NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    usertype_id int NOT NULL,
    profile_id int NOT NULL,
    uname varchar(64) NOT NULL,
    passwd varchar(64) NOT NULL,
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

-- Reference: user_profile (table: users)
ALTER TABLE users ADD CONSTRAINT user_profile FOREIGN KEY user_profile (profile_id)
    REFERENCES profile (id);

-- Reference: user_usertype (table: users)
ALTER TABLE users ADD CONSTRAINT user_usertype FOREIGN KEY user_usertype (usertype_id)
    REFERENCES usertype (id);

-- End of file.

