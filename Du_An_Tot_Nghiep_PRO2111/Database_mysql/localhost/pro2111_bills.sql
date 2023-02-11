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
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills` (
  `bill_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `customer_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `total_money` decimal(10,0) DEFAULT '0',
  `shipping_fee` decimal(10,0) DEFAULT '0',
  `discount` int DEFAULT '0',
  `payments` int DEFAULT '0',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `note` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `bill_type` int DEFAULT '0',
  `division_id` int DEFAULT '0',
  `division_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `district_id` int DEFAULT '0',
  `district_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ward_id` int DEFAULT '0',
  `ward_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ward_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `address_detail` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `success_date` date DEFAULT NULL,
  `cancel_date` date DEFAULT NULL,
  `status` int DEFAULT '1',
  PRIMARY KEY (`bill_id`),
  KEY `customer_idx_idx` (`customer_id`),
  KEY `user_idx_idx` (`user_id`),
  CONSTRAINT `customer_idx` FOREIGN KEY (`customer_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_idx` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
INSERT INTO `bills` VALUES ('HD2212080001',NULL,2,'2022-12-08 11:29:02',643500,0,0,0,'0984998602',NULL,1,0,NULL,0,NULL,0,NULL,NULL,NULL,'2022-12-08',NULL,4),('HD2212080002',12,NULL,'2022-12-08 16:21:11',685001,41501,0,0,'0395762314',NULL,0,267,'Hòa Bình',2270,'Huyện Yên Thủy',0,'Xã Yên Trị','231013','abcde','2022-12-11',NULL,4),('HD2212100001',98,NULL,'2022-12-10 23:23:22',1961153,30653,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,'2022-12-10',5),('HD2212110001',6,2,'2022-12-11 16:47:11',1287000,0,0,0,'0985996232',NULL,1,0,NULL,0,NULL,0,NULL,NULL,NULL,'2022-12-11',NULL,4),('HD2212110002',NULL,4,'2022-12-11 20:49:43',643500,0,0,0,'0395962002',NULL,1,0,NULL,0,NULL,0,NULL,NULL,NULL,'2022-12-11',NULL,4),('HD2212110003',7,NULL,'2022-12-11 21:37:33',680001,36501,0,0,'0123456789',NULL,0,249,'Bắc Ninh',1768,'Huyện Yên Phong',0,'Xã Tam Giang','190209','135',NULL,'2022-12-11',5),('HD2212110004',7,NULL,'2022-12-11 21:42:16',680001,36501,0,0,'0123456789',NULL,0,249,'Bắc Ninh',1768,'Huyện Yên Phong',0,'Xã Tam Giang','190209','135','2022-12-11',NULL,4),('HD2212110005',7,NULL,'2022-12-11 22:34:47',680001,36501,0,0,'0123456789',NULL,0,249,'Bắc Ninh',1768,'Huyện Yên Phong',0,'Xã Tam Giang','190209','135',NULL,NULL,1),('HD2212110006',2,2,'2022-12-11 22:41:49',546975,0,0,0,'0395962002',NULL,1,201,'Hà Nội',1582,'Huyện Đông Anh',0,'Xã Đại Mạch','1A1304','123',NULL,NULL,0),('HD2212110007',2,NULL,'2022-12-11 23:42:13',1454569,41069,0,0,'0395962003',NULL,0,201,'Hà Nội',1582,'Huyện Đông Anh',0,'Xã Đại Mạch','1A1304','123',NULL,NULL,3),('HD2212120001',98,NULL,'2022-12-12 01:07:20',120000,21000,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,NULL,1),('HD2212120002',98,NULL,'2022-12-12 01:09:13',32000,21000,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,NULL,1),('HD2212120003',98,NULL,'2022-12-12 01:19:36',32000,21000,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,NULL,1),('HD2212120004',98,NULL,'2022-12-12 01:22:32',32000,21000,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,NULL,1),('HD2212120005',98,NULL,'2022-12-12 01:23:59',32000,21000,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,NULL,1),('HD2212120006',98,NULL,'2022-12-12 01:34:05',32000,21000,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,NULL,1),('HD2212120007',98,NULL,'2022-12-12 01:35:22',32000,21000,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,NULL,1),('HD2212120008',98,NULL,'2022-12-12 01:36:14',664500,21000,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,NULL,2),('HD2212120009',98,2,'2022-12-12 01:38:22',32000,21000,0,0,'0987654354',NULL,0,201,'Hà Nội',1485,'Quận Cầu Giấy',0,'Phường Trung Hoà','1A0607','nhà số 10',NULL,NULL,3),('HD2212120010',95,NULL,'2022-12-12 22:11:24',52501,41501,0,0,'0984998602',NULL,0,221,'Vĩnh Phúc',3265,'Huyện Sông Lô',0,'Xã Đồng Quế','160705','Xóm lá',NULL,NULL,1),('HD2212120011',95,NULL,'2022-12-12 22:11:50',63501,41501,0,0,'0984998602',NULL,0,221,'Vĩnh Phúc',3265,'Huyện Sông Lô',0,'Xã Đồng Quế','160705','Xóm lá',NULL,NULL,1);
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12 23:41:35
