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

ALTER TABLE users
    DROP FOREIGN KEY user_userprofile;
    
ALTER TABLE enumeration_item
    DROP FOREIGN KEY enumeration_item_enumeration;
    
ALTER TABLE enumeration_item
    DROP FOREIGN KEY enumeration_item_user;

ALTER TABLE enumeration_item
    DROP FOREIGN KEY enumeration_item_post;
    
ALTER TABLE enumeration_item
    DROP FOREIGN KEY enumeration_item_community;
    
-- tables
DROP TABLE community;

DROP TABLE contact;

DROP TABLE post;

DROP TABLE private_message;

DROP TABLE user_community;

DROP TABLE userprofile;

DROP TABLE users;

DROP TABLE enumeration_item;

DROP TABLE enumeration;

-- End of file.

