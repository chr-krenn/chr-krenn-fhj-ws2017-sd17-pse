--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `pse`.`users` DROP PRIMARY KEY;

ALTER TABLE `pse`.`userprofile` DROP PRIMARY KEY;

ALTER TABLE `pse`.`private_message` DROP PRIMARY KEY;

ALTER TABLE `pse`.`enumeration` DROP PRIMARY KEY;

ALTER TABLE `pse`.`contact` DROP PRIMARY KEY;

ALTER TABLE `pse`.`community` DROP PRIMARY KEY;

ALTER TABLE `pse`.`post` DROP PRIMARY KEY;

ALTER TABLE `pse`.`users` DROP INDEX `pse`.`FKcmombhhasank864ums4swd519`;

ALTER TABLE `pse`.`users` DROP INDEX `pse`.`UK_r43af9ap4edm43mmtq01oddj6`;

ALTER TABLE `pse`.`community` DROP INDEX `pse`.`FKk7o792d66bqgrp4x310en9a98`;

ALTER TABLE `pse`.`user_community` DROP INDEX `pse`.`FK4mv0y3ugdd9ogy9x4ii08ld6c`;

ALTER TABLE `pse`.`post` DROP INDEX `pse`.`FKjsft64pkupfvvrs6bd4kyokwi`;

ALTER TABLE `pse`.`private_message` DROP INDEX `pse`.`FK9jb3g7xe0cy4sjav6fk34dkhg`;

ALTER TABLE `pse`.`enumeration_item` DROP INDEX `pse`.`FK2a64bbexb95mu9n8c769rrl0k`;

ALTER TABLE `pse`.`likes` DROP INDEX `pse`.`FKnvx9seeqqyy71bij291pwiwrg`;

ALTER TABLE `pse`.`post` DROP INDEX `pse`.`FKg4x80cty3ha7v00q85djomq6d`;

ALTER TABLE `pse`.`likes` DROP INDEX `pse`.`FK5f7eh2n1ynlotnmvjq3a69gwo`;

ALTER TABLE `pse`.`user_community` DROP INDEX `pse`.`FKvx9uvin2jevvy9sychijjx5d`;

ALTER TABLE `pse`.`contact` DROP INDEX `pse`.`FKbxl6anxo14q097g8cd2e51v55`;

ALTER TABLE `pse`.`enumeration_item` DROP INDEX `pse`.`FK9fjj5j3di6uvh1nylihdj0fh2`;

ALTER TABLE `pse`.`post` DROP INDEX `pse`.`FK7u19g6f38sqcrn2o2ot9l3c4s`;

ALTER TABLE `pse`.`private_message` DROP INDEX `pse`.`FKmjbhmvq38xehsknjxd2jl7r0u`;

ALTER TABLE `pse`.`likes` DROP INDEX `pse`.`FKowd6f4s7x9f3w50pvlo6x3b41`;

DROP TABLE `pse`.`userprofile`;

DROP TABLE `pse`.`enumeration_item`;

DROP TABLE `pse`.`contact`;

DROP TABLE `pse`.`post`;

DROP TABLE `pse`.`likes`;

DROP TABLE `pse`.`private_message`;

DROP TABLE `pse`.`enumeration`;

DROP TABLE `pse`.`community`;

DROP TABLE `pse`.`users`;

DROP TABLE `pse`.`user_community`;

CREATE TABLE `pse`.`userprofile` (
	`id` INT NOT NULL,
	`address` VARCHAR(255),
	`city` VARCHAR(255),
	`country` VARCHAR(255),
	`description` VARCHAR(255),
	`email` VARCHAR(255),
	`firstname` VARCHAR(255),
	`lastname` VARCHAR(255),
	`mobile` VARCHAR(255),
	`phone` VARCHAR(255),
	`picture` MEDIUMBLOB,
	`plz` VARCHAR(255),
	`room` VARCHAR(255),
	`team` VARCHAR(255),
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `pse`.`enumeration_item` (
	`enumeration_id` INT NOT NULL,
	`users_id` INT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `pse`.`contact` (
	`id` INT NOT NULL,
	`fk_contact_id` INT,
	`user_id` INT,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `pse`.`post` (
	`id` INT NOT NULL,
	`created` DATETIME,
	`text` VARCHAR(1024),
	`fk_community_id` INT,
	`parent_post_id` INT,
	`fk_user_id` INT,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `pse`.`likes` (
	`enumeration_id` INT NOT NULL,
	`user_id` INT NOT NULL,
	`post_id` INT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `pse`.`private_message` (
	`id` INT NOT NULL,
	`text` VARCHAR(255),
	`fk_user_id_receiver` INT,
	`fk_user_id_sender` INT,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `pse`.`enumeration` (
	`id` INT NOT NULL,
	`name` VARCHAR(255),
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `pse`.`community` (
	`id` INT NOT NULL,
	`description` VARCHAR(255),
	`name` VARCHAR(255) NOT NULL,
	`picture` TINYBLOB,
	`enumeration_id` INT,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `pse`.`users` (
	`id` INT NOT NULL,
	`password` VARCHAR(255),
	`username` VARCHAR(255) NOT NULL,
	`fk_userprofile` INT,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `pse`.`user_community` (
	`users_id` INT NOT NULL,
	`community_id` INT NOT NULL
) ENGINE=InnoDB;

CREATE INDEX `FKcmombhhasank864ums4swd519` ON `pse`.`users` (`fk_userprofile` ASC);

CREATE UNIQUE INDEX `UK_r43af9ap4edm43mmtq01oddj6` ON `pse`.`users` (`username` ASC);

CREATE INDEX `FKk7o792d66bqgrp4x310en9a98` ON `pse`.`community` (`enumeration_id` ASC);

CREATE INDEX `FK4mv0y3ugdd9ogy9x4ii08ld6c` ON `pse`.`user_community` (`users_id` ASC);

CREATE INDEX `FKjsft64pkupfvvrs6bd4kyokwi` ON `pse`.`post` (`fk_community_id` ASC);

CREATE INDEX `FK9jb3g7xe0cy4sjav6fk34dkhg` ON `pse`.`private_message` (`fk_user_id_sender` ASC);

CREATE INDEX `FK2a64bbexb95mu9n8c769rrl0k` ON `pse`.`enumeration_item` (`enumeration_id` ASC);

CREATE INDEX `FKnvx9seeqqyy71bij291pwiwrg` ON `pse`.`likes` (`user_id` ASC);

CREATE INDEX `FKg4x80cty3ha7v00q85djomq6d` ON `pse`.`post` (`fk_user_id` ASC);

CREATE INDEX `FK5f7eh2n1ynlotnmvjq3a69gwo` ON `pse`.`likes` (`enumeration_id` ASC);

CREATE INDEX `FKvx9uvin2jevvy9sychijjx5d` ON `pse`.`user_community` (`community_id` ASC);

CREATE INDEX `FKbxl6anxo14q097g8cd2e51v55` ON `pse`.`contact` (`user_id` ASC);

CREATE INDEX `FK9fjj5j3di6uvh1nylihdj0fh2` ON `pse`.`enumeration_item` (`users_id` ASC);

CREATE INDEX `FK7u19g6f38sqcrn2o2ot9l3c4s` ON `pse`.`post` (`parent_post_id` ASC);

CREATE INDEX `FKmjbhmvq38xehsknjxd2jl7r0u` ON `pse`.`private_message` (`fk_user_id_receiver` ASC);

CREATE INDEX `FKowd6f4s7x9f3w50pvlo6x3b41` ON `pse`.`likes` (`post_id` ASC);

