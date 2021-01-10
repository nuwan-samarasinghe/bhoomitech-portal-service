-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: synergy_bio_auth_db
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1),(1),(1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_access_token`
--

DROP TABLE IF EXISTS `oauth_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_access_token` (
  `authentication_id` varchar(255) NOT NULL,
  `authentication` mediumblob,
  `client_id` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `token` mediumblob,
  `token_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_access_token`
--

LOCK TABLES `oauth_access_token` WRITE;
/*!40000 ALTER TABLE `oauth_access_token` DISABLE KEYS */;
INSERT INTO `oauth_access_token` VALUES ('9d0be3e902e8abce4f6c61bd887b440d',_binary '�\�\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2Authentication�@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList�%1�\�\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0�\�^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx�\��\�a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0\nROLE_adminsq\0~\0\rt\0create_customersq\0~\0\rt\0delete_customersq\0~\0\rt\0\rread_customersq\0~\0\rt\0update_customerxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>�qi�\0L\0clientIdq\0~\0L\0requestParametersq\0~\0\ZL\0scopeq\0~\0xpt\0websr\0%java.util.Collections$UnmodifiableMap��t�B\0L\0mq\0~\0\Zxpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0codet\02gUKKnt\0\ngrant_typet\0authorization_codet\0\rresponse_typet\0codet\0redirect_urit\0http://localhost:3000/callbackt\0statet\0\0t\0	client_idq\0~\0xsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xq\0~\0	sr\0java.util.LinkedHashSet\�l\�Z�\�*\0\0xr\0java.util.HashSet�D�����4\0\0xpw\0\0\0?@\0\0\0\0\0t\0READt\0WRITExsq\0~\02w\0\0\0?@\0\0\0\0\0\0xsq\0~\0\"?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xt\0http://localhost:3000/callbacksr\09org.springframework.security.oauth2.provider.TokenRequest\�*��\�8�\0L\0	grantTypeq\0~\0xq\0~\0t\0websq\0~\0 sq\0~\0\"?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rrefresh_tokent\0$f3fd9780-1ec2-4bba-bdc0-42c9b68f8f5et\0\ngrant_typet\0\rrefresh_tokenxsq\0~\0/sq\0~\01w\0\0\0?@\0\0\0\0\0\0xq\0~\0Asq\0~\02w\0\0\0?@\0\0\0\0\0t\0supplierservicet\0	inventoryxsq\0~\02w\0\0\0?@\0\0\0\0\0q\0~\0)xsr\0[org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken\0\0\0\0\0\0\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0xq\0~\0Kpt\0\0sr\0)com.auth.authservice.model.AuthUserDetail�\�KP\���\0\0xr\0com.auth.authservice.model.User�)�\�T�= \0	L\0accountNonExpiredt\0Ljava/lang/Boolean;L\0accountNonLockedq\0~\0OL\0credentialsNonExpiredq\0~\0OL\0emailq\0~\0L\0enabledq\0~\0OL\0idt\0Ljava/lang/Integer;L\0passwordq\0~\0L\0rolesq\0~\0L\0usernameq\0~\0xpsr\0java.lang.Boolean\� r�՜�\�\0Z\0valuexpq\0~\0Sq\0~\0St\0saman@gmail.comq\0~\0Spt\0D{bcrypt}$2b$10$/4LKsdxY7Kn.mqihXYXTkuDHD5VlZVLaq.Q8m2nUdGKEpTZjVS10usr\0/org.hibernate.collection.internal.PersistentBag�Wů\�O�D\0L\0bagq\0~\0L\0providedCollectionq\0~\0xr\0>org.hibernate.collection.internal.AbstractPersistentCollectionW�]��sT\0Z\0allowLoadOutsideTransactionI\0\ncachedSizeZ\0dirtyZ\0elementRemovedZ\0initializedZ\0\risTempSessionL\0keyt\0Ljava/io/Serializable;L\0ownerq\0~\0L\0roleq\0~\0L\0sessionFactoryUuidq\0~\0L\0storedSnapshotq\0~\0Xxp\0����\0\0\0sr\0java.lang.Integer⠤���8\0I\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0sq\0~\0Nq\0~\0Sq\0~\0Sq\0~\0Sq\0~\0Tq\0~\0Sq\0~\0\\q\0~\0Uq\0~\0Yt\0samant\0%com.auth.authservice.model.User.rolespsq\0~\0\0\0\0w\0\0\0sr\0com.auth.authservice.model.Role���N�\�J\�\0L\0idq\0~\0PL\0nameq\0~\0L\0permissionsq\0~\0xpq\0~\0\\q\0~\0sq\0~\0V\0����\0\0\0q\0~\0\\q\0~\0bt\0+com.auth.authservice.model.Role.permissionspsq\0~\0\0\0\0w\0\0\0sr\0%com.auth.authservice.model.Permission�b��l_�\0L\0idq\0~\0PL\0nameq\0~\0xpq\0~\0\\q\0~\0sq\0~\0fsq\0~\0Z\0\0\0q\0~\0sq\0~\0fsq\0~\0Z\0\0\0q\0~\0sq\0~\0fsq\0~\0Z\0\0\0q\0~\0xsq\0~\0\0\0\0w\0\0\0q\0~\0gq\0~\0hq\0~\0jq\0~\0lxpxsq\0~\0\0\0\0w\0\0\0q\0~\0bxpq\0~\0^','web','7508d45dd993d9176c9c099fb4a0288f',_binary '�\�\0sr\0Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken��6$�\�\0L\0additionalInformationt\0Ljava/util/Map;L\0\nexpirationt\0Ljava/util/Date;L\0refreshTokent\0?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\0scopet\0Ljava/util/Set;L\0	tokenTypet\0Ljava/lang/String;L\0valueq\0~\0xpsr\0java.util.Collections$EmptyMapY6�Z\�\�\�\0\0xpsr\0java.util.Datehj�KYt\0\0xpw\0\0t\�n��xsr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\�Gc�\�ɷ\0L\0\nexpirationq\0~\0xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\�\ncT\�^\0L\0valueq\0~\0xpt\0$f3fd9780-1ec2-4bba-bdc0-42c9b68f8f5esq\0~\0	w\0\0tɷ\�xsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xr\0,java.util.Collections$UnmodifiableCollectionB\0�\�^�\0L\0ct\0Ljava/util/Collection;xpsr\0java.util.LinkedHashSet\�l\�Z�\�*\0\0xr\0java.util.HashSet�D�����4\0\0xpw\0\0\0?@\0\0\0\0\0t\0READt\0WRITExt\0bearert\0$921e5a66-4530-48ef-8ab2-2df0748f9f13','796da8c9269ceb6d7f7d8319914aa5d1','saman');
/*!40000 ALTER TABLE `oauth_access_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `access_token_validity` int DEFAULT NULL,
  `additional_information` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) NOT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_details`
--

LOCK TABLES `oauth_client_details` WRITE;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;
INSERT INTO `oauth_client_details` VALUES ('web',3600,'{}',NULL,'authorization_code,password,refresh_token,implicit','{bcrypt}$2b$10$XmkBVzh3SrnhBWOayQwTSuT/PkreW5pkJkgqQW38Z07N1BKl1ZgBy',10000,'inventory,supplierservice','READ,WRITE','http://localhost:3000/callback',NULL);
/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_client_token`
--

DROP TABLE IF EXISTS `oauth_client_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_client_token` (
  `authentication_id` varchar(255) NOT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `token` mediumblob,
  `token_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_token`
--

LOCK TABLES `oauth_client_token` WRITE;
/*!40000 ALTER TABLE `oauth_client_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_client_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_code`
--

DROP TABLE IF EXISTS `oauth_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_code` (
  `id` int NOT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `expires_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `authentication` mediumblob,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_code`
--

LOCK TABLES `oauth_code` WRITE;
/*!40000 ALTER TABLE `oauth_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_refresh_token`
--

DROP TABLE IF EXISTS `oauth_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_refresh_token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `authentication` mediumblob,
  `token` mediumblob,
  `token_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_refresh_token`
--

LOCK TABLES `oauth_refresh_token` WRITE;
/*!40000 ALTER TABLE `oauth_refresh_token` DISABLE KEYS */;
INSERT INTO `oauth_refresh_token` VALUES (17,_binary '�\�\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2Authentication�@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList�%1�\�\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0�\�^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx�\��\�a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0\nROLE_adminsq\0~\0\rt\0create_customersq\0~\0\rt\0delete_customersq\0~\0\rt\0\rread_customersq\0~\0\rt\0update_customerxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>�qi�\0L\0clientIdq\0~\0L\0requestParametersq\0~\0\ZL\0scopeq\0~\0xpt\0websr\0%java.util.Collections$UnmodifiableMap��t�B\0L\0mq\0~\0\Zxpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0codet\02gUKKnt\0\ngrant_typet\0authorization_codet\0\rresponse_typet\0codet\0redirect_urit\0http://localhost:3000/callbackt\0statet\0\0t\0	client_idq\0~\0xsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xq\0~\0	sr\0java.util.LinkedHashSet\�l\�Z�\�*\0\0xr\0java.util.HashSet�D�����4\0\0xpw\0\0\0?@\0\0\0\0\0t\0READt\0WRITExsq\0~\02w\0\0\0?@\0\0\0\0\0\0xsq\0~\0\"?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xt\0http://localhost:3000/callbackpsq\0~\02w\0\0\0?@\0\0\0\0\0t\0supplierservicet\0	inventoryxsq\0~\02w\0\0\0?@\0\0\0\0\0q\0~\0)xsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0xq\0~\0@sr\0Horg.springframework.security.web.authentication.WebAuthenticationDetails\0\0\0\0\0\0\0L\0\rremoteAddressq\0~\0L\0	sessionIdq\0~\0xpt\00:0:0:0:0:0:0:1t\0 FE38146922A28D0CFFFBF325296F3476psr\0)com.auth.authservice.model.AuthUserDetail�\�KP\���\0\0xr\0com.auth.authservice.model.User�)�\�T�= \0	L\0accountNonExpiredt\0Ljava/lang/Boolean;L\0accountNonLockedq\0~\0GL\0credentialsNonExpiredq\0~\0GL\0emailq\0~\0L\0enabledq\0~\0GL\0idt\0Ljava/lang/Integer;L\0passwordq\0~\0L\0rolesq\0~\0L\0usernameq\0~\0xpsr\0java.lang.Boolean\� r�՜�\�\0Z\0valuexpq\0~\0Kq\0~\0Kt\0saman@gmail.comq\0~\0Kpt\0D{bcrypt}$2b$10$/4LKsdxY7Kn.mqihXYXTkuDHD5VlZVLaq.Q8m2nUdGKEpTZjVS10usr\0/org.hibernate.collection.internal.PersistentBag�Wů\�O�D\0L\0bagq\0~\0L\0providedCollectionq\0~\0xr\0>org.hibernate.collection.internal.AbstractPersistentCollectionW�]��sT\0Z\0allowLoadOutsideTransactionI\0\ncachedSizeZ\0dirtyZ\0elementRemovedZ\0initializedZ\0\risTempSessionL\0keyt\0Ljava/io/Serializable;L\0ownerq\0~\0L\0roleq\0~\0L\0sessionFactoryUuidq\0~\0L\0storedSnapshotq\0~\0Pxp\0����\0\0\0sr\0java.lang.Integer⠤���8\0I\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0sq\0~\0Fq\0~\0Kq\0~\0Kq\0~\0Kq\0~\0Lq\0~\0Kq\0~\0Tq\0~\0Mq\0~\0Qt\0samant\0%com.auth.authservice.model.User.rolespsq\0~\0\0\0\0w\0\0\0sr\0com.auth.authservice.model.Role���N�\�J\�\0L\0idq\0~\0HL\0nameq\0~\0L\0permissionsq\0~\0xpq\0~\0Tq\0~\0sq\0~\0N\0����\0\0\0q\0~\0Tq\0~\0Zt\0+com.auth.authservice.model.Role.permissionspsq\0~\0\0\0\0w\0\0\0sr\0%com.auth.authservice.model.Permission�b��l_�\0L\0idq\0~\0HL\0nameq\0~\0xpq\0~\0Tq\0~\0sq\0~\0^sq\0~\0R\0\0\0q\0~\0sq\0~\0^sq\0~\0R\0\0\0q\0~\0sq\0~\0^sq\0~\0R\0\0\0q\0~\0xsq\0~\0\0\0\0w\0\0\0q\0~\0_q\0~\0`q\0~\0bq\0~\0dxpxsq\0~\0\0\0\0w\0\0\0q\0~\0Zxpq\0~\0V',_binary '�\�\0sr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\�Gc�\�ɷ\0L\0\nexpirationt\0Ljava/util/Date;xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\�\ncT\�^\0L\0valuet\0Ljava/lang/String;xpt\0$f3fd9780-1ec2-4bba-bdc0-42c9b68f8f5esr\0java.util.Datehj�KYt\0\0xpw\0\0tɷ\�x','7508d45dd993d9176c9c099fb4a0288f');
/*!40000 ALTER TABLE `oauth_refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2ojme20jpga3r4r79tdso17gi` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'create_customer'),(4,'delete_customer'),(2,'read_customer'),(3,'update_customer');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_role`
--

DROP TABLE IF EXISTS `permission_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission_role` (
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  KEY `FK3tuvkbyi6wcytyg21hvpd6txw` (`permission_id`),
  KEY `FK50sfdcvbvdaclpn7wp4uop4ml` (`role_id`),
  CONSTRAINT `FK3tuvkbyi6wcytyg21hvpd6txw` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FK50sfdcvbvdaclpn7wp4uop4ml` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_role`
--

LOCK TABLES `permission_role` WRITE;
/*!40000 ALTER TABLE `permission_role` DISABLE KEYS */;
INSERT INTO `permission_role` VALUES (1,1),(1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `permission_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_admin'),(2,'ROLE_operator');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_user`
--

DROP TABLE IF EXISTS `role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_user` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `FKiqpmjd2qb4rdkej916ymonic6` (`role_id`),
  KEY `FK4320p8bgvumlxjkohtbj214qi` (`user_id`),
  CONSTRAINT `FK4320p8bgvumlxjkohtbj214qi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKiqpmjd2qb4rdkej916ymonic6` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_user`
--

LOCK TABLES `role_user` WRITE;
/*!40000 ALTER TABLE `role_user` DISABLE KEYS */;
INSERT INTO `role_user` VALUES (1,1);
/*!40000 ALTER TABLE `role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `account_non_expired` bit(1) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary '',_binary '',_binary '','user@gmail.com',_binary '','{bcrypt}$2b$10$/4LKsdxY7Kn.mqihXYXTkuDHD5VlZVLaq.Q8m2nUdGKEpTZjVS10u','user');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-26 14:24:20
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: synergy_bio_supplier_db
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (26),(26),(26),(26),(26),(26);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_cost_type`
--

-- Dump completed on 2020-09-26 14:24:20
