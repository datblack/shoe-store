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
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Bùi Quang Hiếu','hieubqph13812@fpt.edu.vn','$2a$10$Uw5JgsiKzzTfE1RmcJ5oQeovBId2D4GgDex1SPQdD5ay3tWYV6Y.u','0395962003',1,3,'1',1,0,NULL,201,'Hà Nội',1582,'Huyện Đông Anh','Xã Đại Mạch','1A1304','123'),(3,'Ma Thế Đạt','datmtph13621@fpt.edu.vn','$2a$10$vhDEmqkz3de86y3dT9l2A.rY6q6OYzAMAFOpcwMy4z67KcaL8fbq.','0962652481',1,3,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(4,'Chu Quang Huy','huycqph17456@fpt.edu.vn','$2a$10$x/Cd3uSWibEZvDEE/JGig.ImJGXprMnxoRPR1D.uHi/ABIq3T.OmO','0316516511',1,3,'1',1,0,NULL,201,'Hà Nội',1808,'Huyện Thạch Thất','Xã Phùng Xá','1B1917','12'),(6,'Người yêu Chung','vy@gmail.com','$2a$10$Uw5JgsiKzzTfE1RmcJ5oQeovBId2D4GgDex1SPQdD5ay3tWYV6Y.u','0985996232',0,2,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(7,'huy','huypxtt@gmail.com','$2a$10$8j2tmK6sZXmndAq1x2/rj.6OveCJsNNzBHwUMvbzcJLv7IXruYDya','0123456789',1,2,'1',1,0,NULL,249,'Bắc Ninh',1768,'Huyện Yên Phong','Xã Tam Giang','190209','135'),(8,'huyen','huyen@gmail.com','$2a$10$O/Coa1vd8p6SSA884M0DTOpOunrn5hxeHhZILAETDJUdhtzSE9D8.','01253654835',0,1,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(9,'phương','phuong@gmail.com','$2a$10$MDmAaKxKeZNKV.DbZaapEeY4Q0uUD63Anoyluhz8coz7vp05pb4Xe','0235468615',0,1,'1',1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(11,'Nguyễn Thị Hoa','hoantph16552@fpt.edu.vn','$2a$10$hgu7Mgmp9/MTX9eWf5z12OqdZUwq6u9Xlk4GvVwKcerUR6GirMmmG','0826763234',0,3,NULL,1,0,NULL,234,'Thanh Hóa',1927,'Huyện Đông Sơn','Xã Đông Khê','282005','Thôn 4'),(12,'Vũ Thị Huyền','huyenvtph17141@fpt.edu.vn','$2a$12$lqJcJRWt9MbDtLWRzlN7hejR7Av7R6OH7r7VYvaFJ0J5w2YUA.ase','0395762314',0,3,NULL,1,0,NULL,267,'Hòa Bình',2270,'Huyện Yên Thủy','Xã Yên Trị','231013','abcde'),(22,'tue','hoangtue881998@gmail.com','$2a$10$bB5EA5pg1z9.t30cO6dWxuL2xFj8T2/rg46Pw6XmYWexqCG.v/Iey','0368689239',1,3,NULL,1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(23,'Hoàng Tuệ','hoangtue08081998@gmail.com','$2a$10$.0cFpFuyvqX35DUu.Tcc6OyoZcSF1qq9NyXBqHr73bbynx5d6AdKe','0368689238',1,3,NULL,1,0,NULL,263,'Yên Bái',1967,'Huyện Lục Yên','Xã Trúc Lâu','130921','Trúc Lâu - Lục Yên - Yên Bái'),(24,'Nguyễn Hoa','hoanguyen02122002@gmail.com','$2a$10$1jhuhBoiARWUKNZNzkOJ8OLGSnq1GmIik6UHDgKdj4ChwqM1AW.ba','0912345678',0,3,NULL,1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(74,'Nguyễn Hồng Anh','anhnhph12345@fpt.edu.vn','$2a$10$wTawu.peKs292/P11l0SSepMh0mJJ2edHHuH3YaKBhu1QdmJ.GGVm','0981234567',0,1,NULL,1,0,NULL,0,NULL,0,NULL,NULL,NULL,NULL),(81,'Nguyễn Thị Hồng Anh','anhnhph16788@fpt.edu.vn','$2a$10$N5qeZitwTsKK9ZmrtA52beMpgRuF1/2H7exfyJG9t/gyiztXZbD9e','0923142647',0,1,NULL,1,0,'2022-11-28 14:27:20',0,NULL,0,NULL,NULL,NULL,NULL),(95,'Nguyễn Văn C','chungntph13390@fpt.edu.vn','$2a$10$Ha8V1ma0jN5P6ijBHWdxhuVw5llp8YXlRdNC0Br1eVZ4N9nn2Rqra','0984998602',1,3,NULL,1,0,'2022-12-04 16:38:41',221,'Vĩnh Phúc',3265,'Huyện Sông Lô','Xã Đồng Quế','160705','Xóm lá'),(98,'the dat','thedat1705k2@gmail.com','$2a$10$WuHJRiqQON/e/z84V0Gy/ujnigaGhHO.8gKf0XuisQBG6yxvs0FyO','0987654354',1,1,NULL,1,0,'2022-12-08 23:26:16',201,'Hà Nội',1485,'Quận Cầu Giấy','Phường Trung Hoà','1A0607','nhà số 10');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12 23:41:50
