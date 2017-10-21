-- foreign keys
ALTER TABLE feed
    DROP FOREIGN KEY news_feed_posting;

ALTER TABLE feed
    DROP FOREIGN KEY news_feed_profile;

ALTER TABLE posting
    DROP FOREIGN KEY posting_user;

ALTER TABLE users
    DROP FOREIGN KEY user_profile;

ALTER TABLE users
    DROP FOREIGN KEY user_usertype;

-- tables
DROP TABLE feed;

DROP TABLE posting;

DROP TABLE profile;

DROP TABLE users;

DROP TABLE usertype;

-- End of file.

