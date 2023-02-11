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
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `sale_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `sale_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `discount_type` int DEFAULT '0',
  `discount` decimal(10,0) DEFAULT '0',
  `created_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `status` int DEFAULT NULL,
  `weekday` int DEFAULT NULL,
  `start_at` time DEFAULT NULL,
  `end_at` time DEFAULT NULL,
  `sale_parent` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `sale_type` int DEFAULT NULL,
  `created_user` int DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `updated_user` int DEFAULT NULL,
  PRIMARY KEY (`sale_id`),
  KEY `FK_saleId_saleParent` (`sale_parent`),
  CONSTRAINT `FK_saleId_saleParent` FOREIGN KEY (`sale_parent`) REFERENCES `sales` (`sale_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES ('8a8a808584ece0ba0184ed2501b80001','KHUYẾN MÃI 3- 9',1,15,'2022-12-07 22:12:16','2022-12-11 22:39:00','2022-12-12 22:42:00',2,0,NULL,NULL,NULL,0,2,'2022-12-11 22:37:35',2),('8a8a808584ece0ba0184ed27a9fd0003','TẾT DƯƠNG LỊCH',1,16,'2022-12-07 22:15:10',NULL,NULL,2,0,NULL,NULL,NULL,1,2,'2022-12-12 00:38:35',2),('8a8a808584ece0ba0184ed27a9fe0004','',0,NULL,NULL,'2022-12-08 22:14:00','2022-12-15 22:14:00',2,1,'04:00:00','08:00:00','8a8a808584ece0ba0184ed27a9fd0003',1,NULL,NULL,NULL),('8a8a808584ece0ba0184ed27a9fe0005','',0,NULL,NULL,'2022-12-08 22:14:00','2022-12-21 22:14:00',2,1,'02:00:00','04:00:00','8a8a808584ece0ba0184ed27a9fd0003',1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12 23:42:42
