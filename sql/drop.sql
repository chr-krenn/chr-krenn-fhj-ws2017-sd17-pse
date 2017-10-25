-- foreign keys
ALTER TABLE contact
    DROP FOREIGN KEY contact_users;

ALTER TABLE post
    DROP FOREIGN KEY post_community;

ALTER TABLE post
    DROP FOREIGN KEY post_post;

ALTER TABLE post
    DROP FOREIGN KEY posting_user;

ALTER TABLE private_message
    DROP FOREIGN KEY private_message_users;

ALTER TABLE user_community
    DROP FOREIGN KEY user_community_community;

ALTER TABLE user_community
    DROP FOREIGN KEY user_community_users;

-- tables
DROP TABLE community;

DROP TABLE contact;

DROP TABLE post;

DROP TABLE private_message;

DROP TABLE user_community;

DROP TABLE users;

-- End of file.

