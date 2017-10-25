-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-10-25 07:57:56.446

-- foreign keys
ALTER TABLE community
    DROP FOREIGN KEY community_users;

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

-- tables
DROP TABLE community;

DROP TABLE contact;

DROP TABLE post;

DROP TABLE private_message;

DROP TABLE users;

-- End of file.

