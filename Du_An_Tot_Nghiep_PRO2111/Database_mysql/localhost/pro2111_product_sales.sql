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
-- Table structure for table `product_sales`
--

DROP TABLE IF EXISTS `product_sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_sales` (
  `product_sale_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `sale_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `variant_id` bigint NOT NULL,
  `status` int DEFAULT '1',
  PRIMARY KEY (`product_sale_id`),
  KEY `FK_product_variant` (`variant_id`),
  KEY `FK_sale` (`sale_id`),
  CONSTRAINT `FK_product_variant` FOREIGN KEY (`variant_id`) REFERENCES `product_variants` (`variant_id`),
  CONSTRAINT `FK_sale` FOREIGN KEY (`sale_id`) REFERENCES `sales` (`sale_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_sales`
--

LOCK TABLES `product_sales` WRITE;
/*!40000 ALTER TABLE `product_sales` DISABLE KEYS */;
INSERT INTO `product_sales` VALUES ('8a8a808584ece0ba0184ed2501bb0002','8a8a808584ece0ba0184ed2501b80001',1,1),('8a8a808584ece0ba0184ed27a9fe0006','8a8a808584ece0ba0184ed27a9fd0003',2,1);
/*!40000 ALTER TABLE `product_sales` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12 23:42:27
