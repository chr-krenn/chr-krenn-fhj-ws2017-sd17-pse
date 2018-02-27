-- MySQL dump 10.16  Distrib 10.1.26-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: pse
-- ------------------------------------------------------
-- Server version	10.1.26-MariaDB

-- created with mysqldump -d -u root -p pse

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `community`
--

DROP TABLE IF EXISTS `community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `name` varchar(255) NOT NULL,
  `is_private` TINYINT unsigned NOT NULL,
  `picture` tinyblob,
  `portaladmin_id` int(11) DEFAULT NULL,
  `enumeration_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ggi0mfnbrejia9lxku7voffc9` (`name`),
  KEY `FKk7o792d66bqgrp4x310en9a98` (`enumeration_id`),
  CONSTRAINT `FKk7o792d66bqgrp4x310en9a98` FOREIGN KEY (`enumeration_id`) REFERENCES `enumeration` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_contact_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbxl6anxo14q097g8cd2e51v55` (`user_id`),
  CONSTRAINT `FKbxl6anxo14q097g8cd2e51v55` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `enumeration`
--

DROP TABLE IF EXISTS `enumeration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enumeration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `enumeration_item`
--

DROP TABLE IF EXISTS `enumeration_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enumeration_item` (
  `enumeration_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  KEY `FK9fjj5j3di6uvh1nylihdj0fh2` (`users_id`),
  KEY `FK2a64bbexb95mu9n8c769rrl0k` (`enumeration_id`),
  CONSTRAINT `FK2a64bbexb95mu9n8c769rrl0k` FOREIGN KEY (`enumeration_id`) REFERENCES `enumeration` (`id`),
  CONSTRAINT `FK9fjj5j3di6uvh1nylihdj0fh2` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` mediumblob,
  `filename` varchar(255) NOT NULL,
  `community_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3dj4spomxuc93fn5g89vgx1u` (`filename`),
  KEY `FKjeurflerqd51hwvqyovv1dnal` (`community_id`),
  KEY `FKe70ql3orpo0ghvfmqccv27ng` (`user_id`),
  CONSTRAINT `FKe70ql3orpo0ghvfmqccv27ng` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjeurflerqd51hwvqyovv1dnal` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likes` (
  `enumeration_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  KEY `FKnvx9seeqqyy71bij291pwiwrg` (`user_id`),
  KEY `FK5f7eh2n1ynlotnmvjq3a69gwo` (`enumeration_id`),
  KEY `FKowd6f4s7x9f3w50pvlo6x3b41` (`post_id`),
  CONSTRAINT `FK5f7eh2n1ynlotnmvjq3a69gwo` FOREIGN KEY (`enumeration_id`) REFERENCES `enumeration` (`id`),
  CONSTRAINT `FKnvx9seeqqyy71bij291pwiwrg` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKowd6f4s7x9f3w50pvlo6x3b41` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `text` varchar(1024) DEFAULT NULL,
  `fk_community_id` int(11) DEFAULT NULL,
  `parent_post_id` int(11) DEFAULT NULL,
  `fk_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjsft64pkupfvvrs6bd4kyokwi` (`fk_community_id`),
  KEY `FK7u19g6f38sqcrn2o2ot9l3c4s` (`parent_post_id`),
  KEY `FKg4x80cty3ha7v00q85djomq6d` (`fk_user_id`),
  CONSTRAINT `FK7u19g6f38sqcrn2o2ot9l3c4s` FOREIGN KEY (`parent_post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FKg4x80cty3ha7v00q85djomq6d` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjsft64pkupfvvrs6bd4kyokwi` FOREIGN KEY (`fk_community_id`) REFERENCES `community` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_users`
--

DROP TABLE IF EXISTS `post_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_users` (
  `Post_id` int(11) NOT NULL,
  `likedByUsers_id` int(11) NOT NULL,
  KEY `FKmrbiwgfd5dfevlcichn72rsy` (`likedByUsers_id`),
  KEY `FKjpqym9kc0w6m82ouanq0npqbh` (`Post_id`),
  CONSTRAINT `FKjpqym9kc0w6m82ouanq0npqbh` FOREIGN KEY (`Post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FKmrbiwgfd5dfevlcichn72rsy` FOREIGN KEY (`likedByUsers_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `private_message`
--

DROP TABLE IF EXISTS `private_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `private_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `fk_user_id_receiver` int(11) DEFAULT NULL,
  `fk_user_id_sender` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmjbhmvq38xehsknjxd2jl7r0u` (`fk_user_id_receiver`),
  KEY `FK9jb3g7xe0cy4sjav6fk34dkhg` (`fk_user_id_sender`),
  CONSTRAINT `FK9jb3g7xe0cy4sjav6fk34dkhg` FOREIGN KEY (`fk_user_id_sender`) REFERENCES `users` (`id`),
  CONSTRAINT `FKmjbhmvq38xehsknjxd2jl7r0u` FOREIGN KEY (`fk_user_id_receiver`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_community`
--

DROP TABLE IF EXISTS `user_community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_community` (
  `community_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  KEY `FK4mv0y3ugdd9ogy9x4ii08ld6c` (`users_id`),
  KEY `FKvx9uvin2jevvy9sychijjx5d` (`community_id`),
  CONSTRAINT `FK4mv0y3ugdd9ogy9x4ii08ld6c` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKvx9uvin2jevvy9sychijjx5d` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userprofile`
--

DROP TABLE IF EXISTS `userprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userprofile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `picture` mediumblob,
  `plz` varchar(255) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `team` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `fk_userprofile` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FKcmombhhasank864ums4swd519` (`fk_userprofile`),
  CONSTRAINT `FKcmombhhasank864ums4swd519` FOREIGN KEY (`fk_userprofile`) REFERENCES `userprofile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-26  8:20:57
