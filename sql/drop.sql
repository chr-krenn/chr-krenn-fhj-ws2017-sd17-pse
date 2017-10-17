-- foreign keys
ALTER TABLE news_feed
    DROP FOREIGN KEY news_feed_posting;

ALTER TABLE news_feed
    DROP FOREIGN KEY news_feed_profile;

ALTER TABLE posting
    DROP FOREIGN KEY posting_user;

ALTER TABLE user
    DROP FOREIGN KEY user_profile;

ALTER TABLE user
    DROP FOREIGN KEY user_usertype;

-- tables
DROP TABLE news_feed;

DROP TABLE posting;

DROP TABLE profile;

DROP TABLE user;

DROP TABLE usertype;

-- End of file.

