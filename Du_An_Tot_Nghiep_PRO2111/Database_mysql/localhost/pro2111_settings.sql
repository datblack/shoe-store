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
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `setting_id` int NOT NULL AUTO_INCREMENT,
  `user_edit` int DEFAULT NULL,
  `phone_shop` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `bank` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `division_id` int DEFAULT NULL,
  `division_name` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `district_id` int DEFAULT NULL,
  `district_name` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ward_code` varchar(10) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ward_name` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `address_detail` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `update_day` datetime DEFAULT NULL,
  PRIMARY KEY (`setting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES (1,2,'0395962002','horsesoftware002@gmail.com','bzmhrmyqkgddsdpw','{\"accountNumber\":\"83838383456789\",\"bankName\":\"MBBank\",\"accountHolder\":\"BÙI QUANG HIẾU\"}',201,'Hà Nội',1582,'Huyện Đông Anh','1A1307','Xã Hải Bối','Yên Hà','2022-12-01 00:32:18');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12 23:42:12
