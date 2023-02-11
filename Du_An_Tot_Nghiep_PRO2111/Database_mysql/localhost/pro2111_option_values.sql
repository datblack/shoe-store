-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: pro2111
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `option_values`
--

DROP TABLE IF EXISTS `option_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `option_values` (
  `Value_id` int NOT NULL AUTO_INCREMENT,
  `Option_id` int NOT NULL,
  `Value_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` int NOT NULL DEFAULT '1',
  `is_show` int DEFAULT '1',
  PRIMARY KEY (`Value_id`),
  KEY `option_option_id_idx` (`Option_id`),
  CONSTRAINT `option_option_id` FOREIGN KEY (`Option_id`) REFERENCES `options` (`Option_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option_values`
--

LOCK TABLES `option_values` WRITE;
/*!40000 ALTER TABLE `option_values` DISABLE KEYS */;
INSERT INTO `option_values` VALUES (1,1,'36',1,1),(2,1,'37',1,1),(3,1,'38',1,1),(4,1,'39',1,1),(5,1,'40',1,1),(6,1,'41',1,1),(7,1,'42',1,1),(8,1,'43',1,1),(9,1,'44',1,1),(10,2,'DA',1,1),(11,2,'DA LỘN',1,1),(12,3,'2000',1,0),(13,4,'GREY',1,1),(14,4,'GREEN',1,1),(15,4,'WHITE',1,1),(16,4,'WHITE/PINK',1,1);
/*!40000 ALTER TABLE `option_values` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12 23:43:04
