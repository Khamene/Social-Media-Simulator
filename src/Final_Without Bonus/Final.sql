-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: twitter
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `blocks`
--

DROP TABLE IF EXISTS `blocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blocks` (
  `BlockerID` varchar(10) NOT NULL,
  `BlockedID` varchar(10) NOT NULL,
  KEY `BlockerID` (`BlockerID`),
  KEY `BlockedID` (`BlockedID`),
  CONSTRAINT `blocks_ibfk_1` FOREIGN KEY (`BlockerID`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `blocks_ibfk_2` FOREIGN KEY (`BlockedID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blocks`
--

LOCK TABLES `blocks` WRITE;
/*!40000 ALTER TABLE `blocks` DISABLE KEYS */;
/*!40000 ALTER TABLE `blocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `postID` varchar(10) DEFAULT NULL,
  `userID` varchar(10) DEFAULT NULL,
  `comment` varchar(280) DEFAULT NULL,
  `dateCommented` date DEFAULT NULL,
  KEY `postID` (`postID`),
  KEY `userID` (`userID`),
  CONSTRAINT `comments_ibfk_3` FOREIGN KEY (`postID`) REFERENCES `posts` (`PostID`) ON DELETE CASCADE,
  CONSTRAINT `comments_ibfk_4` FOREIGN KEY (`userID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES ('#P1','#PU0','Hey dudues, share ops',NULL),('#p2','#PU0','Your bags are good for nothing','2022-07-30');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `COMMENTINCREMENT` AFTER INSERT ON `comments` FOR EACH ROW UPDATE POSTS SET COMMENTNUM = COMMENTNUM + 1 WHERE POSTS.POSTID = NEW.POSTID */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `COMMENTDECREMENT` AFTER DELETE ON `comments` FOR EACH ROW UPDATE POSTS SET COMMENTNUM = COMMENTNUM - 1 WHERE POSTS.POSTID = OLD.POSTID */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `directgroups`
--

DROP TABLE IF EXISTS `directgroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `directgroups` (
  `GroupID` varchar(10) NOT NULL,
  `OwnerID` varchar(10) DEFAULT NULL,
  `groupName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`GroupID`),
  UNIQUE KEY `groupName` (`groupName`),
  KEY `OwnerID` (`OwnerID`),
  CONSTRAINT `directgroups_ibfk_2` FOREIGN KEY (`OwnerID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `directgroups`
--

LOCK TABLES `directgroups` WRITE;
/*!40000 ALTER TABLE `directgroups` DISABLE KEYS */;
INSERT INTO `directgroups` VALUES ('#G0','#PU0','Rangers');
/*!40000 ALTER TABLE `directgroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followrequests`
--

DROP TABLE IF EXISTS `followrequests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `followrequests` (
  `FromID` varchar(10) DEFAULT NULL,
  `ToID` varchar(10) DEFAULT NULL,
  KEY `FromID` (`FromID`),
  KEY `ToID` (`ToID`),
  CONSTRAINT `followrequests_ibfk_3` FOREIGN KEY (`FromID`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `followrequests_ibfk_4` FOREIGN KEY (`ToID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followrequests`
--

LOCK TABLES `followrequests` WRITE;
/*!40000 ALTER TABLE `followrequests` DISABLE KEYS */;
INSERT INTO `followrequests` VALUES ('#BU0','#PU0'),('#BU0','#PU1'),('#BU0','#PU2');
/*!40000 ALTER TABLE `followrequests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupsadmins`
--

DROP TABLE IF EXISTS `groupsadmins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupsadmins` (
  `groupID` varchar(10) DEFAULT NULL,
  `userID` varchar(10) DEFAULT NULL,
  KEY `groupID` (`groupID`),
  KEY `userID` (`userID`),
  CONSTRAINT `groupsadmins_ibfk_3` FOREIGN KEY (`groupID`) REFERENCES `directgroups` (`GroupID`) ON DELETE CASCADE,
  CONSTRAINT `groupsadmins_ibfk_4` FOREIGN KEY (`userID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupsadmins`
--

LOCK TABLES `groupsadmins` WRITE;
/*!40000 ALTER TABLE `groupsadmins` DISABLE KEYS */;
INSERT INTO `groupsadmins` VALUES ('#G0','#PU0');
/*!40000 ALTER TABLE `groupsadmins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupsparticipants`
--

DROP TABLE IF EXISTS `groupsparticipants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupsparticipants` (
  `userID` varchar(10) DEFAULT NULL,
  `BANNED` bit(1) DEFAULT b'0',
  `groupID` varchar(10) DEFAULT NULL,
  KEY `userID` (`userID`),
  KEY `groupID` (`groupID`),
  CONSTRAINT `groupsparticipants_ibfk_4` FOREIGN KEY (`userID`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `groupsparticipants_ibfk_5` FOREIGN KEY (`groupID`) REFERENCES `directgroups` (`GroupID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupsparticipants`
--

LOCK TABLES `groupsparticipants` WRITE;
/*!40000 ALTER TABLE `groupsparticipants` DISABLE KEYS */;
INSERT INTO `groupsparticipants` VALUES ('#PU0',_binary '\0','#G0'),('#PU1',_binary '\0','#G0');
/*!40000 ALTER TABLE `groupsparticipants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inbox`
--

DROP TABLE IF EXISTS `inbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inbox` (
  `MESSAGEID` varchar(10) DEFAULT NULL,
  `SEEN` bit(1) DEFAULT NULL,
  `USERID` varchar(10) DEFAULT NULL,
  KEY `MESSAGEID` (`MESSAGEID`),
  KEY `USERID` (`USERID`),
  CONSTRAINT `inbox_ibfk_1` FOREIGN KEY (`MESSAGEID`) REFERENCES `messages` (`messageID`) ON DELETE CASCADE,
  CONSTRAINT `inbox_ibfk_2` FOREIGN KEY (`USERID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inbox`
--

LOCK TABLES `inbox` WRITE;
/*!40000 ALTER TABLE `inbox` DISABLE KEYS */;
INSERT INTO `inbox` VALUES ('#DM0',_binary '','#PU1'),('#DM1',_binary '','#PU1'),('#DM2',_binary '','#PU1');
/*!40000 ALTER TABLE `inbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `postID` varchar(10) DEFAULT NULL,
  `userID` varchar(10) DEFAULT NULL,
  `dateLiked` date DEFAULT NULL,
  KEY `postID` (`postID`),
  KEY `userID` (`userID`),
  CONSTRAINT `likes_ibfk_3` FOREIGN KEY (`postID`) REFERENCES `posts` (`PostID`) ON DELETE CASCADE,
  CONSTRAINT `likes_ibfk_4` FOREIGN KEY (`userID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES ('#P1','#PU1',NULL),('#P1','#PU0',NULL),('#p2','#PU0','2022-07-30'),('#p2','#PU2','2022-07-30');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `LIKEINCREMENT` AFTER INSERT ON `likes` FOR EACH ROW UPDATE POSTS SET LIKENUM = LIKENUM + 1 WHERE POSTS.POSTID = NEW.POSTID */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `LIKEDECREMENT` AFTER DELETE ON `likes` FOR EACH ROW UPDATE POSTS SET LIKENUM = LIKENUM - 1 WHERE POSTS.POSTID = OLD.POSTID */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `messageID` varchar(10) NOT NULL,
  `user1` varchar(10) DEFAULT NULL,
  `user2` varchar(10) DEFAULT NULL,
  `groupID` varchar(10) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `forwarded` bit(1) DEFAULT b'0',
  `modified` bit(1) DEFAULT b'0',
  `repliedMessageID` varchar(10) DEFAULT NULL,
  `messageType` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`messageID`),
  KEY `user1` (`user1`),
  KEY `user2` (`user2`),
  KEY `groupID` (`groupID`),
  KEY `repliedMessageID` (`repliedMessageID`),
  CONSTRAINT `messages_ibfk_5` FOREIGN KEY (`user1`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `messages_ibfk_6` FOREIGN KEY (`user2`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `messages_ibfk_7` FOREIGN KEY (`groupID`) REFERENCES `directgroups` (`GroupID`) ON DELETE CASCADE,
  CONSTRAINT `messages_ibfk_8` FOREIGN KEY (`repliedMessageID`) REFERENCES `messages` (`messageID`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES ('#DM0','#PU0','#PU1',NULL,'Fuck you',_binary '\0',_binary '\0',NULL,'D'),('#DM1','#PU0','#PU1',NULL,'Fuck Y\'all',_binary '\0',_binary '\0',NULL,'D'),('#DM2','#PU0','#PU1',NULL,'Shit you',_binary '\0',_binary '\0',NULL,'D');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `PostID` varchar(10) NOT NULL,
  `UserID` varchar(10) DEFAULT NULL,
  `LikeNum` int DEFAULT NULL,
  `commentNum` int DEFAULT NULL,
  `content` varchar(280) DEFAULT NULL,
  `filepath` varchar(150) DEFAULT NULL,
  `dateModified` datetime DEFAULT NULL,
  PRIMARY KEY (`PostID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES ('#P0','#PU0',0,0,'this is me, Mehrshad',NULL,'2022-07-29 00:20:39'),('#P1','#PU0',2,1,'this is you, Not Mehrshad',NULL,'2022-07-29 00:20:55'),('#P2','#BU0',2,1,'Check out my new Bags',NULL,'2022-07-30 12:38:35');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relations`
--

DROP TABLE IF EXISTS `relations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relations` (
  `FollowerID` varchar(10) DEFAULT NULL,
  `FollowedID` varchar(10) DEFAULT NULL,
  KEY `FollowerID` (`FollowerID`),
  KEY `FollowedID` (`FollowedID`),
  CONSTRAINT `relations_ibfk_3` FOREIGN KEY (`FollowerID`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `relations_ibfk_4` FOREIGN KEY (`FollowedID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relations`
--

LOCK TABLES `relations` WRITE;
/*!40000 ALTER TABLE `relations` DISABLE KEYS */;
INSERT INTO `relations` VALUES ('#PU0','#PU1'),('#PU1','#PU0'),('#PU2','#BU0'),('#PU0','#BU0');
/*!40000 ALTER TABLE `relations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `replymessages`
--

DROP TABLE IF EXISTS `replymessages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `replymessages` (
  `MESSAGEID` varchar(10) NOT NULL,
  `CONTENT` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`MESSAGEID`),
  CONSTRAINT `replymessages_ibfk_1` FOREIGN KEY (`MESSAGEID`) REFERENCES `messages` (`messageID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `replymessages`
--

LOCK TABLES `replymessages` WRITE;
/*!40000 ALTER TABLE `replymessages` DISABLE KEYS */;
/*!40000 ALTER TABLE `replymessages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(10) NOT NULL,
  `username` varchar(20) NOT NULL,
  `Age` int DEFAULT NULL,
  `Gender` varchar(1) DEFAULT NULL,
  `Birthday` date DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `UserType` varchar(1) DEFAULT NULL,
  `DateCreated` timestamp NULL DEFAULT NULL,
  `PASSWORD` varchar(32) NOT NULL,
  `PRIVACYMODE` varchar(4) DEFAULT NULL,
  `securityAnswer1` varchar(20) DEFAULT NULL,
  `securityAnswer2` varchar(20) DEFAULT NULL,
  `phoneNumber` varchar(11) DEFAULT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `SECURITYQUESTION1` int DEFAULT NULL,
  `SECURITYQUESTION2` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `EMAIL` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('#BU0','BagLovers7',20,'F','2002-10-12','AliRahmati@gmail.com','B','2022-07-30 08:07:22','BagbagBag','PUB','Shit','Shit','09131458249','Ali','Rahmati',1,2),('#PU0','Mehrshad522',20,'M','2002-10-17','Mehrshadtaji61@gmail.com','P','2022-07-27 18:27:32','MehrshadTaji','PRIV','Coco','Shahrekord','09397205066','Mehrshad','Taji',1,2),('#PU1','Alia777',20,'F','2002-01-01','Alia.Fur@gmail.com','P','2022-07-27 18:28:32','ShitsHIT','PUB','NMA','Doctor','09131234567','Alia','Fur',4,5),('#PU2','Amin2022',20,'F','2002-10-10','Amin@Amini.com','P','2022-07-28 17:44:38','Paswordss','PRIV','You','You','09123459823','Amin','Amin',1,3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visiteddates`
--

DROP TABLE IF EXISTS `visiteddates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visiteddates` (
  `visitedID` varchar(10) DEFAULT NULL,
  `visitorID` varchar(10) DEFAULT NULL,
  `dateVisited` date DEFAULT NULL,
  KEY `visitedID` (`visitedID`),
  KEY `visitorID` (`visitorID`),
  CONSTRAINT `visiteddates_ibfk_3` FOREIGN KEY (`visitedID`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `visiteddates_ibfk_5` FOREIGN KEY (`visitorID`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visiteddates`
--

LOCK TABLES `visiteddates` WRITE;
/*!40000 ALTER TABLE `visiteddates` DISABLE KEYS */;
INSERT INTO `visiteddates` VALUES ('#BU0','#PU0','2022-07-30'),('#BU0','#PU0','2022-07-30');
/*!40000 ALTER TABLE `visiteddates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'twitter'
--

--
-- Dumping routines for database 'twitter'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-30 23:11:00
