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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12 23:41:20
