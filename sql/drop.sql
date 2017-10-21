-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-10-21 19:16:25.404

-- foreign keys
ALTER TABLE feed
    DROP FOREIGN KEY news_feed_posting;

ALTER TABLE feed
    DROP FOREIGN KEY news_feed_profile;

ALTER TABLE posting
    DROP FOREIGN KEY posting_user;

ALTER TABLE profile
    DROP FOREIGN KEY profile_users;

ALTER TABLE users
    DROP FOREIGN KEY user_usertype;

-- tables
DROP TABLE feed;

DROP TABLE posting;

DROP TABLE profile;

DROP TABLE users;

DROP TABLE usertype;

-- End of file.

