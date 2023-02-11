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
-- Table structure for table `product_options`
--

DROP TABLE IF EXISTS `product_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_options` (
  `Product_id` int NOT NULL,
  `Option_id` int NOT NULL,
  `Status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`Product_id`,`Option_id`),
  KEY `option_id_idx` (`Option_id`),
  CONSTRAINT `option_id` FOREIGN KEY (`Option_id`) REFERENCES `options` (`Option_id`),
  CONSTRAINT `product_id` FOREIGN KEY (`Product_id`) REFERENCES `products` (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_options`
--

LOCK TABLES `product_options` WRITE;
/*!40000 ALTER TABLE `product_options` DISABLE KEYS */;
INSERT INTO `product_options` VALUES (1,1,1),(1,2,1),(1,3,1),(1,4,1),(2,1,1),(2,2,1),(2,3,1),(2,4,1),(4,1,1),(4,2,1),(4,3,1),(4,4,1),(5,1,1),(5,2,1),(5,3,1),(5,4,1),(6,1,1),(6,2,1),(6,3,1),(6,4,1),(7,1,1),(7,2,1),(7,3,1),(7,4,1),(8,1,1),(8,2,1),(8,3,1),(8,4,1);
/*!40000 ALTER TABLE `product_options` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12 23:43:11
