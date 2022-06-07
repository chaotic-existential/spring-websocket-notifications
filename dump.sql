-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: demo
-- ------------------------------------------------------
-- Server version	8.0.28

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

USE demo;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `last_sent_at` timestamp NULL DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,'New minor disease spreading: In a normal check-up, a doctor in <country> found a new disease which has been named <plague name>. It appears to be mostly harmless but must be investigated further. Other countries are also reporting this disease','2022-06-07 10:27:50','New disease spotted in <country>','WARNING'),(2,'First death from <plague name> has been confirmed in <country>. Bad luck or the beginning of something terrible?','2022-06-07 10:27:53','First death in <country>','SUCCESS'),(3,'People infected with <plague name> came to Tokyo and spread their disease. It should have never went on!','2022-06-07 10:28:02','Tokyo Olympics infect Japan!','SUCCESS'),(4,'Latest research shows that hot countries are getting significantly hotter due to global warming',NULL,'Global warming: Hot countries getting hotter','WARNING'),(5,'<Plague name> is a Necroa Virus. It has extreme regenerative abilities combined with aggressive metabolic demands but most of its genetic structure is a mystery',NULL,'Necroa Virus identified','WARNING'),(6,'Zombies have hunted down the last group of survivors in <country>. There is no one left alive and without food. The zombies will begin to starve',NULL,'Zombies destroy <country>','SUCCESS'),(7,'Extreme tsunami hits <country>. WHO analysts expect significant property damage and electricity shortages','2022-06-07 10:28:11','Tsunami hits <country>','WARNING'),(8,'Scientists have introduced synthetic genes into <plague name>. WHO researchers say that this breakthrough will help cure efforts',NULL,'<plague name> genes manipulated','WARNING'),(9,'<country> is first to instruct doctors to begin research into a cure for <plague name>. Without greater funding, it is expected to take a long time','2022-06-07 10:28:22','<country> starts work on cure','WARNING'),(10,'The whole world worships <plague name> as their god and master. Efforts to cure the plague have ceased and humanity is entering a dark new future as a slave species','2022-06-07 10:28:25','<plague name> enslaves humanity','SUCCESS'),(11,'The last healthy person on the planet recently became infected with <plague name>','2022-06-07 10:28:28','There are no healthy people left in the world','SUCCESS');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-07 10:29:03
