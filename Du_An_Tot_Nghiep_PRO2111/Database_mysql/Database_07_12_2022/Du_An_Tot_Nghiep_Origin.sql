-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 67.207.89.59    Database: PRO2111
-- ------------------------------------------------------
-- Server version	8.0.31-0ubuntu0.22.04.1

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
-- Table structure for table `bill_details`
--

DROP TABLE IF EXISTS `bill_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_details` (
  `detail_bill_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `bill_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `variant_id` bigint DEFAULT NULL,
  `quantity` int DEFAULT '0',
  `price` decimal(10,0) DEFAULT '0',
  `tax` decimal(10,0) DEFAULT NULL,
  `total_money` decimal(10,0) DEFAULT '0',
  `note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `status` int DEFAULT '0',
  `bill_detail_id_parent` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `user_confirm` int DEFAULT NULL,
  PRIMARY KEY (`detail_bill_id`),
  KEY `fk_bill_details_bills1` (`bill_id`),
  KEY `product_variant_idx_idx` (`variant_id`),
  CONSTRAINT `fk_bill_details_bills1` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`bill_id`),
  CONSTRAINT `product_variant_idx` FOREIGN KEY (`variant_id`) REFERENCES `product_variants` (`variant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_details`
--

LOCK TABLES `bill_details` WRITE;
/*!40000 ALTER TABLE `bill_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill_details` ENABLE KEYS */;
UNLOCK TABLES;

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
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_details`
--

DROP TABLE IF EXISTS `cart_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_details` (
  `cart_detail_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `cart_id` int NOT NULL,
  `variant_id` bigint NOT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`cart_detail_id`),
  KEY `cart_id_idx` (`cart_id`),
  KEY `variant_id_idx` (`variant_id`),
  CONSTRAINT `cart_id` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`),
  CONSTRAINT `variant_id` FOREIGN KEY (`variant_id`) REFERENCES `product_variants` (`variant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_details`
--

LOCK TABLES `cart_details` WRITE;
/*!40000 ALTER TABLE `cart_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`cart_id`),
  KEY `userindex_idx` (`user_id`),
  CONSTRAINT `userindex` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (1,2,0),(2,11,0);
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history` (
  `history_id` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `setting_id` int NOT NULL,
  `created_date` datetime NOT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES ('402880eb846e31b401846e32bd050000',1,'2022-11-13 06:35:30','Cập nhật số điện thoại từ 0395962002 -> 0395.96.2002'),('402880eb846e39b401846e3e84200000',1,'2022-11-13 06:48:22','Cập nhật số điện thoại từ 0395.96.2002 -> 113'),('402880eb846e39b401846e3f77100001',1,'2022-11-13 06:49:24','Cập nhật số điện thoại từ 113 -> 0395.96.2002'),('402880eb846e39b401846e4142f90002',1,'2022-11-13 06:51:22','Cập nhật số điện thoại từ 0395.96.2002 -> 84395962002'),('402880eb846e42d101846e4324360000',1,'2022-11-13 06:53:25','Cập nhật số điện thoại từ 84395962002 -> 0395.96.2002'),('402880eb846e74e401846e76c88b0000',1,'2022-11-13 07:49:49','Cập nhật ngân hàng từ MBBank -> Techcombank '),('402880eb846e797c01846e7a75200000',1,'2022-11-13 07:53:50','Cập nhật chủ tài khoản từ BUI QUANG HIEU -> BÙI QUANG HIẾU Cập nhật ngân hàng từ Techcombank -> MBBank Cập nhật số tài khoản từ 19034033608015 -> 83838383456789 '),('402880eb84712bf3018471348feb0000',1,'2022-11-13 20:36:21','Cập nhật email từ horsesoftware002@gmail.com -> bui.quang.hieu2910@gmail.com'),('402880eb84712bf30184713718170001',1,'2022-11-13 20:39:07','Cập nhật email từ bui.quang.hieu2910@gmail.com -> horsesoftware002@gmail.com'),('402880eb847f871a01847f878cfb0000',1,'2022-11-16 15:21:40','Cập nhật Địa chỉ chi tiết từ: Yên Hà1 -> Yên Hà'),('402880eb847f871a01847f8a25320001',1,'2022-11-16 15:24:31','Cập nhật Thành phố/Tỉnh từ: Hà Nội -> Kiên GiangCập nhật Quận/Huyện từ: Huyện Đông Anh -> Huyện An MinhCập nhật Phường/Xã từ: Xã Hải Bối -> Xã Vân Khánh Tây'),('402880eb847f8e3301847f8f98d30000',1,'2022-11-16 15:30:28','Cập nhật Thành phố/Tỉnh từ: Kiên Giang -> Hà Nội Cập nhật Quận/Huyện từ: Huyện An Minh -> Huyện Đông Anh Cập nhật Phường/Xã từ: Xã Vân Khánh Tây -> Xã Hải Bối '),('8a8a80858480181d0184804227960000',1,'2022-11-16 18:45:30','Cập nhật Địa chỉ chi tiết từ: Yên Hà -> Yên Hà1'),('8a8a80858480181d0184804239c20001',1,'2022-11-16 18:45:35','Cập nhật Địa chỉ chi tiết từ: Yên Hà1 -> Yên Hà'),('8a8a8085849b316001849b45e43a0000',1,'2022-11-22 00:39:20','Cập nhật Quận/Huyện từ: Huyện Đông Anh -> Huyện Thạch Thất Cập nhật Phường/Xã từ: Xã Hải Bối -> Xã Phùng Xá '),('8a8a8085849b316001849b52301d0001',1,'2022-11-22 00:52:46','Cập nhật Quận/Huyện từ: Huyện Thạch Thất -> Huyện Đông Anh Cập nhật Phường/Xã từ: Xã Phùng Xá -> Xã Hải Bối '),('8a8a808584c965950184c97660110002',1,'2022-11-30 23:54:49','Cập nhật số điện thoại từ 0395.96.2002 -> 0'),('8a8a808584c978080184c979b1510000',1,'2022-11-30 23:58:27','Cập nhật email từ horsesoftware002@gmail.com -> Hoanguyen02122002@gmail.com'),('8a8a808584c990720184c9953b0b0000',1,'2022-12-01 00:28:31','Cập nhật email từ Hoanguyen02122002@gmail.com -> aaa@ddd'),('8a8a808584c990720184c995e9d60001',1,'2022-12-01 00:29:16','Cập nhật email từ aaa@ddd -> Hoantph16552@fpt.edu.vn'),('8a8a808584c990720184c998b0390002',1,'2022-12-01 00:32:18','Cập nhật số điện thoại từ 0 -> 0395962002');
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `images_id` int NOT NULL AUTO_INCREMENT,
  `product_variant_id` bigint NOT NULL,
  `image_path` varchar(255) NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`images_id`),
  KEY `product_variant_id_idx` (`product_variant_id`),
  CONSTRAINT `product_variant_id` FOREIGN KEY (`product_variant_id`) REFERENCES `product_variants` (`variant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (18,72,'4cfb8cad.jpg',1),(21,85,'82611fc.jpg',1),(22,85,'56bf2bc.jpg',1),(23,85,'de8ffbf2.jpg',1),(24,85,'332aee2d.jpg',1),(25,86,'4cfb8cad.jpg',1),(26,86,'1b8293a1.jpg',1),(27,87,'f4ced8cf.jpg',1),(28,87,'24743e32.jpg',1),(29,88,'964a36fa.jpg',1),(30,88,'6da71f54.jpg',1),(31,88,'26bad46e.jpg',1),(32,88,'f8abe8e5.jpg',1),(35,73,'ed79348c.jpg',1),(36,73,'e7d6389e.jpg',1),(37,73,'e516af42.jpg',1),(40,72,'58800180.jpg',1),(41,72,'82611fc.jpg',1),(42,74,'82611fc.jpg',1),(43,79,'e7d6389e.jpg',1),(44,72,'1b8293a1.jpg',1),(45,72,'e7d6389e.jpg',1),(46,90,'f88b1274.jpg',1),(47,95,'56bf2bc.jpg',0),(48,95,'e7d6389e.jpg',0),(49,95,'e7d6389e.jpg',1),(56,126,'e7d6389e.jpg',0),(57,127,'e7d6389e.jpg',0),(58,128,'aa979a3a.jpg',0),(59,129,'e516af42.jpg',0),(60,77,'1b8293a1.jpg',1),(61,77,'1b8293a1.jpg',1),(62,77,'1b8293a1.jpg',1),(63,80,'d2e50d77.jpg',1),(64,143,'6cda6ee6.jpg',1),(65,143,'8d297b0d.jpg',1),(66,143,'24743e32.jpg',1),(67,145,'4ac25b17.jpg',1),(68,145,'24743e32.jpg',1),(69,146,'82611fc.jpg',0),(70,75,'2e99648b.jpg',1),(71,148,'2e99648b.jpg',1),(74,164,'f4ced8cf.jpg',1),(75,164,'f88b1274.jpg',1),(76,164,'f4ced8cf.jpg',1),(77,164,'56bf2bc.jpg',1);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option_values`
--

LOCK TABLES `option_values` WRITE;
/*!40000 ALTER TABLE `option_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `option_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `options` (
  `Option_id` int NOT NULL AUTO_INCREMENT,
  `Option_name` varchar(100) NOT NULL,
  `Status` int NOT NULL DEFAULT '1',
  `is_show` int DEFAULT '1',
  PRIMARY KEY (`Option_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

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
/*!40000 ALTER TABLE `product_options` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `product_sales` VALUES ('4028b88183d461a30183d463b5750001','4028b88183d461a30183d463b5190000',72,1),('4028b88183d461a30183d463b5770002','4028b88183d461a30183d463b5190000',73,1),('8a8a808583fbcfc20183ff3f962d0001','8a8a808583fbcfc20183ff3f96230000',85,1),('8a8a808583fbcfc20183ff3f962e0002','8a8a808583fbcfc20183ff3f96230000',86,1),('8a8a808583fbcfc20183ffdd8500000e','8a8a808583fbcfc20183ffdd8500000d',76,1),('8a8a80858457cf2f0184581effc90002','8a8a80858457cf2f0184581effc60001',80,1),('8a8a80858466d694018466e1d23d0005','8a8a80858466d694018466e1d23c0003',80,1),('8a8a80858466d694018466e245250006','8a8a80858466d694018466e1d23c0003',72,1),('8a8a80858466d694018466e51f190008','8a8a80858466d694018466e51f190007',85,1),('8a8a80858466d694018467608e420009','8a8a80858466d694018466e1d23c0003',73,1),('8a8a808584815ad101848645869e0008','8a8a808584815ad10184864586860007',85,1),('8a8a808584815ad101848645869e0009','8a8a808584815ad10184864586860007',86,1),('8a8a808584815ad101848645869f000a','8a8a808584815ad10184864586860007',80,1),('8a8a8085849b831201849e7870ba0000','8a8a80858466d694018466e1d23c0003',95,1),('8a8a808584c3b59d0184c41505cd0004','8a8a808584c3b59d0184c41505ca0002',72,1),('8a8a808584c3b59d0184c41505cd0005','8a8a808584c3b59d0184c41505ca0002',73,1),('8a8a808584d393a90184d39c66700001','8a8a808584d393a90184d39c665e0000',79,1),('8a8a808584ddb6c80184de3055150008','8a8a808584ddb6c80184de3054fb0006',72,1);
/*!40000 ALTER TABLE `product_sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_variants`
--

DROP TABLE IF EXISTS `product_variants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_variants` (
  `variant_id` bigint NOT NULL AUTO_INCREMENT,
  `SKU_ID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `quantity_error` int DEFAULT '0',
  `tax` decimal(10,0) DEFAULT '0',
  `import_price` decimal(10,0) DEFAULT NULL,
  `price` decimal(10,0) NOT NULL DEFAULT '0',
  `is_sale` int DEFAULT '0',
  `status` int NOT NULL DEFAULT '1',
  `user_create` int NOT NULL,
  `user_edit` int NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `edit_date` datetime DEFAULT NULL,
  PRIMARY KEY (`variant_id`),
  KEY `prodcut_id_idx` (`product_id`),
  KEY `FK_user_create_idx` (`user_create`),
  KEY `user_edit` (`user_edit`),
  CONSTRAINT `prodcut_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`Product_id`),
  CONSTRAINT `user_create` FOREIGN KEY (`user_create`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_edit` FOREIGN KEY (`user_edit`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_variants`
--

LOCK TABLES `product_variants` WRITE;
/*!40000 ALTER TABLE `product_variants` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_variants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `Product_id` int NOT NULL AUTO_INCREMENT,
  `Product_name` varchar(100) NOT NULL,
  `Status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `sales` VALUES ('4028b88183d461a30183d463b5190000','CHƯƠNG TRÌNH TEST 2',1,10,'2022-10-14 09:47:28','2022-12-03 09:47:00','2022-12-04 20:46:00',2,0,NULL,NULL,NULL,0,2,'2022-12-02 22:44:45',2),('8a8a808583fbcfc20183ff3f96230000','CHƯƠNG TRÌNH TEST 3',1,10,'2022-10-22 17:31:41','2022-10-22 18:31:00','2022-10-23 17:31:00',2,0,NULL,NULL,NULL,0,2,NULL,NULL),('8a8a808583fbcfc20183ffdd8500000d','TEST4',1,10,'2022-10-22 20:24:11','2022-11-14 23:54:00','2022-11-30 20:24:00',2,0,NULL,NULL,NULL,0,2,'2022-11-13 23:06:32',2),('8a8a80858457cf2f0184581effc60001','CHƯƠNG TRÌNH TEST 8',1,13,'2022-11-08 23:42:17','2022-11-08 23:45:00','2022-11-19 23:41:00',2,0,NULL,NULL,NULL,0,2,'2022-11-17 11:23:02',23),('8a8a80858466d694018466e1d23c0003','GIẢM GIÁ',1,10,'2022-11-11 20:29:46','2022-11-20 00:04:00','2022-12-03 23:29:00',2,0,NULL,NULL,NULL,0,12,'2022-11-22 15:33:24',12),('8a8a80858466d694018466e1d23c0004','',0,NULL,NULL,'2022-11-12 20:27:00','2022-11-24 21:27:00',2,1,'20:27:00','20:27:00','8a8a80858466d694018466e1d23c0003',0,NULL,NULL,NULL),('8a8a80858466d694018466e51f190007','TEST HUYỀN',1,100,'2022-11-11 20:33:23','2022-12-21 20:33:00','2022-12-23 21:32:00',0,0,NULL,NULL,NULL,0,12,'2022-11-26 23:12:51',2),('8a8a808584815ad10184864586860007','TEST',0,100000,'2022-11-17 22:46:54','2022-11-28 22:32:00','2022-12-09 19:48:00',1,0,NULL,NULL,NULL,0,11,'2022-12-05 18:46:42',12),('8a8a808584c3b59d0184c41505ca0002','TEST IPHONE',1,50,'2022-11-29 22:50:23',NULL,NULL,2,0,NULL,NULL,NULL,1,12,'2022-12-02 23:38:15',12),('8a8a808584c3b59d0184c41505ca0003','',0,NULL,NULL,'2022-11-29 22:49:00','2022-12-07 10:49:00',0,3,'22:49:00','22:49:00','8a8a808584c3b59d0184c41505ca0002',1,NULL,NULL,NULL),('8a8a808584ce84b50184d35624480002','',0,NULL,NULL,'2022-12-03 21:55:00','2022-12-14 21:55:00',0,4,'22:55:00','23:55:00','8a8a808584c3b59d0184c41505ca0002',1,NULL,NULL,NULL),('8a8a808584d393a90184d39c665e0000','TEST HUYENCHO',1,99,'2022-12-02 23:12:33','2022-12-02 23:12:00','2022-12-07 23:11:00',1,0,NULL,NULL,NULL,0,12,'2022-12-02 23:14:01',12),('8a8a808584ddb6c80184de2d340a0004','',0,NULL,NULL,'2022-12-07 00:26:00','2022-12-15 00:26:00',2,3,'00:26:00','00:28:00','8a8a808584c3b59d0184c41505ca0002',1,NULL,NULL,NULL),('8a8a808584ddb6c80184de2dc6330005','',0,NULL,NULL,'2022-12-05 00:27:00','2022-12-16 00:27:00',0,6,'00:27:00','00:49:00','8a8a808584c3b59d0184c41505ca0002',1,NULL,NULL,NULL),('8a8a808584ddb6c80184de3054fb0006','TEST IPHONE HUYEN CHO',1,25,'2022-12-05 00:30:20',NULL,NULL,2,0,NULL,NULL,NULL,1,12,'2022-12-05 18:55:11',12),('8a8a808584ddb6c80184de3054fc0007','',0,NULL,NULL,'2022-12-05 00:29:00','2022-12-16 00:30:00',0,5,'00:32:00','05:36:00','8a8a808584ddb6c80184de3054fb0006',1,NULL,NULL,NULL),('8a8a808584ddb6c80184de315afd0009','',0,NULL,NULL,'2022-11-28 06:31:00','2022-12-05 20:31:00',2,1,'03:31:00','21:35:00','8a8a808584ddb6c80184de3054fb0006',1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `sex` int NOT NULL DEFAULT '0',
  `role` int NOT NULL DEFAULT '1',
  `avatar` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `status` int DEFAULT '1',
  `otp` int DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `division_id` int DEFAULT '0',
  `division_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `district_id` int DEFAULT '0',
  `district_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ward_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ward_code` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `address_detail` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `users_Email_IX` (`email`),
  UNIQUE KEY `users_Phone_IX` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Bùi Quang Hiếu','hieubqph13812@fpt.edu.vn','$2a$10$Uw5JgsiKzzTfE1RmcJ5oQeovBId2D4GgDex1SPQdD5ay3tWYV6Y.u','0395962003',1,3,'1',1,0,NULL,201,'Hà Nội',1582,'Huyện Đông Anh','Xã Đại Mạch','1A1304','123'),(3,'Ma Thế Đạt','datmtph13621@fpt.edu.vn','$2a$10$vhDEmqkz3de86y3dT9l2A.rY6q6OYzAMAFOpcwMy4z67KcaL8fbq.','0962652481',1,3,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(4,'Chu Quang Huy','huycqph17456@fpt.edu.vn','$2a$10$x/Cd3uSWibEZvDEE/JGig.ImJGXprMnxoRPR1D.uHi/ABIq3T.OmO','0316516511',1,3,'1',1,0,NULL,201,'Hà Nội',1808,'Huyện Thạch Thất','Xã Phùng Xá','1B1917','12'),(6,'Người yêu Chung','vy@gmail.com','$2a$10$Uw5JgsiKzzTfE1RmcJ5oQeovBId2D4GgDex1SPQdD5ay3tWYV6Y.u','0985996232',0,2,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(7,'huy','huypxtt@gmail.com','$2a$10$8j2tmK6sZXmndAq1x2/rj.6OveCJsNNzBHwUMvbzcJLv7IXruYDya','0123456789',1,2,'1',1,0,NULL,249,'Bắc Ninh',1768,'Huyện Yên Phong','Xã Tam Giang','190209','135'),(8,'huyen','huyen@gmail.com','$2a$10$O/Coa1vd8p6SSA884M0DTOpOunrn5hxeHhZILAETDJUdhtzSE9D8.','01253654835',0,1,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(9,'phương','phuong@gmail.com','$2a$10$MDmAaKxKeZNKV.DbZaapEeY4Q0uUD63Anoyluhz8coz7vp05pb4Xe','0235468615',0,1,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(10,'abc','abc@gmail.com','$2a$12$lqJcJRWt9MbDtLWRzlN7hejR7Av7R6OH7r7VYvaFJ0J5w2YUA.ase','0123545862',0,1,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(11,'Nguyễn Thị Hoa','hoantph16552@fpt.edu.vn','$2a$10$hgu7Mgmp9/MTX9eWf5z12OqdZUwq6u9Xlk4GvVwKcerUR6GirMmmG','0826763234',0,3,NULL,1,0,NULL,234,'Thanh Hóa',1927,'Huyện Đông Sơn','Xã Đông Khê','282005','Thôn 4'),(12,'Vũ Thị Huyền','huyenvtph17141@fpt.edu.vn','$2a$12$lqJcJRWt9MbDtLWRzlN7hejR7Av7R6OH7r7VYvaFJ0J5w2YUA.ase','0395762314',0,3,NULL,1,0,NULL,267,'Hòa Bình',2270,'Huyện Yên Thủy','Xã Yên Trị','231013','abcde'),(22,'tue','hoangtue881998@gmail.com','$2a$10$bB5EA5pg1z9.t30cO6dWxuL2xFj8T2/rg46Pw6XmYWexqCG.v/Iey','0368689239',1,3,NULL,1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(23,'Hoàng Tuệ','hoangtue08081998@gmail.com','$2a$10$.0cFpFuyvqX35DUu.Tcc6OyoZcSF1qq9NyXBqHr73bbynx5d6AdKe','0368689238',1,3,NULL,1,0,NULL,263,'Yên Bái',1967,'Huyện Lục Yên','Xã Trúc Lâu','130921','Trúc Lâu - Lục Yên - Yên Bái'),(24,'Nguyễn Hoa','hoanguyen02122002@gmail.com','$2a$10$s91WEISUt6pLv2qujmqsJuKTbGLreWB5k3DD5Gumf/gnaHrC9.rG.','0912345678',0,1,NULL,1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(25,'acc','abcc@gmail.com','$2a$10$g1EDTo8n91sG6JHkI5IJOOCOHIAg03684SjPSpJyI6965TWG6sJ0e','0123545892',1,1,'1',2,656117,'2022-11-10 14:30:34',0,NULL,0,NULL,NULL,NULL,NULL),(62,'Nguyễn Thái Chung','nguyenthaichung0809@gmail.com','$2a$10$C0Io78HkwXdvkxQpGDraD.jDVXJAG3lex5r9hesVytYLdtJaHcQ/u','1234567890',0,1,NULL,2,172075,'2022-11-29 22:50:28',0,NULL,0,NULL,NULL,NULL,NULL),(63,'vy','vy123@gmail.com','$2a$10$iJbOKJlxJXaM/FiDzWEabutYTOJww.KfRw65zT9/T5AIy1IPDc0Xa','....1......',0,2,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(73,'Nguyễn Thái Chung','nthaichung0809@gmail.com','$2a$10$/PfKKdSN8efvLfUqeXfQrOtwWKs9oC1PJAMNZ.PmYJ6iO1z7b1sby','1234567998',0,1,NULL,2,655708,'2022-11-23 00:04:23',0,NULL,0,NULL,NULL,NULL,NULL),(74,'Nguyễn Hồng Anh','anhnhph12345@fpt.edu.vn','$2a$10$wTawu.peKs292/P11l0SSepMh0mJJ2edHHuH3YaKBhu1QdmJ.GGVm','0981234567',0,1,NULL,1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(81,'Nguyễn Thị Hồng Anh','anhnhph16788@fpt.edu.vn','$2a$10$N5qeZitwTsKK9ZmrtA52beMpgRuF1/2H7exfyJG9t/gyiztXZbD9e','0923142647',0,1,NULL,1,0,'2022-11-28 14:27:20',0,NULL,0,NULL,NULL,NULL,NULL),(85,'Hiii','hih123@gmail.com.vn','$2a$10$o3fzkX5w92/xDdt.7Eiq/emxnGw2L4g62Ed.NYYBiWolUL0UbpJga','0821234567',0,1,NULL,1,0,'2022-11-29 15:42:15',0,NULL,0,NULL,NULL,NULL,NULL),(86,'Nguyễn Thái Chung','thudtph17509@fpt.edu.vn','$2a$10$2s6NurWoU4DPfgDK1A8qOuGijFboIy/xkYL9BhpZqTbLNWiNTwn5m','1234567899',0,1,NULL,1,347372,'2022-11-29 22:32:47',0,NULL,0,NULL,NULL,NULL,NULL),(94,'Nguyễn Thái Chung','thedat1705k2@gmail.com','$2a$10$f0/XN.Ut1veZMDT54JF6rOkd.uTGXBvQQx.DpfBzZiB0hd1eQO4fm','1234567123',0,1,NULL,1,181960,'2022-11-29 23:05:00',0,NULL,0,NULL,NULL,NULL,NULL),(95,'Nguyễn Văn C','chungntph13390@fpt.edu.vn','$2a$10$LADyNCAVGQ5afuaW5muBFuq3LuTnKNqRZjyZ.1OgjsBOYc19QI.0G','0984998602',1,1,NULL,1,974173,'2022-12-04 16:38:41',0,NULL,0,NULL,NULL,NULL,NULL),(97,'huyen','quynhvtph18499@fpt.edu.vn','$2a$10$liUNLKrQFAQWCeh9g8HD6uhKckY5.hiBsVoLMbe84fST1tsHk85Na','0395762319',1,1,NULL,2,434925,'2022-12-06 22:46:05',0,NULL,0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant_values`
--

DROP TABLE IF EXISTS `variant_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant_values` (
  `Variant_id` bigint NOT NULL,
  `Product_id` int NOT NULL,
  `Option_id` int NOT NULL,
  `Value_id` int NOT NULL,
  `Status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`Variant_id`,`Product_id`,`Option_id`),
  KEY `option_value_value_id_idx` (`Value_id`),
  CONSTRAINT `FK1` FOREIGN KEY (`Variant_id`) REFERENCES `product_variants` (`variant_id`),
  CONSTRAINT `option_value_value_id` FOREIGN KEY (`Value_id`) REFERENCES `option_values` (`Value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant_values`
--

LOCK TABLES `variant_values` WRITE;
/*!40000 ALTER TABLE `variant_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `variant_values` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-07  1:12:17
