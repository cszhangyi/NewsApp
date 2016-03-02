CREATE DATABASE  IF NOT EXISTS `news_database` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `news_database`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: news_database
-- ------------------------------------------------------
-- Server version	5.6.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_comment` (
  `commentID` int(11) NOT NULL,
  `content` text NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `userID` int(11) NOT NULL,
  `newsID` int(11) NOT NULL,
  PRIMARY KEY (`commentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_comment`
--

LOCK TABLES `t_comment` WRITE;
/*!40000 ALTER TABLE `t_comment` DISABLE KEYS */;
INSERT INTO `t_comment` VALUES (3001,'有意思','2016-02-01 13:32:53',9001,1),(3002,'太逗了','2016-02-01 13:32:54',9002,1),(3003,'加油~','2016-02-01 13:32:55',9003,1),(3004,'不是吧！','2016-02-01 13:32:56',9004,1),(3005,'哈哈哈','2016-02-02 13:32:56',9005,1),(3006,'太原刚玉姑姑一 韩国锦湖反对法国有土地激活工具和景观空间会更机会观看脚后跟卡激活工具恢复过程工程计划国防规划和空间哈高科更何况脚后跟就','2016-02-03 13:32:56',9007,1),(3007,'啊快点放假了似的发啦锻炼腹肌啊两地分居啦大家发了打飞机啦离开对方急啊了看得见伐啦看得见伐啦空手道解放了卢卡斯的flak觉得分厘卡电视机分厘卡机东方；打飞机啊；地方','2016-02-04 13:32:56',9008,1),(3008,'啊开发的加快立法','2016-02-05 13:32:56',9009,2),(3009,'Hello World','2016-02-18 00:33:30',9001,1),(3010,'ä½ å¥½ ç« é±¼','2016-02-18 01:09:20',9001,1),(3011,'ä½ å¥½ ç« é±¼ä½ å¥½','2016-02-18 01:11:56',9001,1),(3012,'你好，章鱼！','2016-02-18 01:17:31',9001,1),(3013,'你好，章鱼！','2016-02-18 01:21:39',9002,1);
/*!40000 ALTER TABLE `t_comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-18  1:47:16
