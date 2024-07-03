-- --------------------------------------------------------
-- Máy chủ:                      127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Phiên bản:           12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for websitenghenhac
CREATE DATABASE IF NOT EXISTS `websitenghenhac` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `websitenghenhac`;

-- Dumping structure for table websitenghenhac.albums
CREATE TABLE IF NOT EXISTS `albums` (
  `album_id` bigint NOT NULL AUTO_INCREMENT,
  `image_path` varchar(255) DEFAULT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `artist_id` bigint DEFAULT NULL,
  `genre_id` bigint DEFAULT NULL,
  PRIMARY KEY (`album_id`),
  KEY `FK72gqyi6l1j674radjyitcm86f` (`artist_id`),
  KEY `FKgo1exs517g8n9osc20m3qidib` (`genre_id`),
  CONSTRAINT `FK72gqyi6l1j674radjyitcm86f` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`artist_id`),
  CONSTRAINT `FKgo1exs517g8n9osc20m3qidib` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.albums: ~9 rows (approximately)
REPLACE INTO `albums` (`album_id`, `image_path`, `release_date`, `title`, `artist_id`, `genre_id`) VALUES
	(1, '/images/albumImg/SonTung.png', '2024-06-25 00:00:00.000000', 'Sơn Tùng album', 1, 1),
	(3, '/images/albumImg/HuongTram.png', '2024-07-03 00:00:00.000000', 'Hương Tràm album', 3, 2),
	(4, '/images/albumImg/SOOBIN.png', '2024-07-03 00:00:00.000000', 'SOOBIN album', 4, 2),
	(5, '/images/albumImg/VuongAnhTu.png', '2024-07-03 00:00:00.000000', 'Vương Anh Tú abum', 5, 2),
	(6, '/images/albumImg/MCK.png', '2024-07-03 00:00:00.000000', 'MCK album', 2, 3),
	(7, '/images/albumImg/Karik.png', '2024-07-03 00:00:00.000000', 'Karik album', 6, 3),
	(8, '/images/albumImg/PhuongLy.png', '2024-07-03 00:00:00.000000', 'Phương Ly album', 7, 2),
	(9, '/images/albumImg/GDragon.png', '2024-07-03 00:00:00.000000', 'G-Dragon album', 9, 3),
	(10, '/images/albumImg/BRay.png', '2024-07-03 00:00:00.000000', 'B Ray album', 1, 1);

-- Dumping structure for table websitenghenhac.album_artist
CREATE TABLE IF NOT EXISTS `album_artist` (
  `album_id` bigint NOT NULL,
  `artist_id` bigint NOT NULL,
  PRIMARY KEY (`album_id`,`artist_id`),
  KEY `FKp5bpsjh87uhwjupyxgclxy8h8` (`artist_id`),
  CONSTRAINT `FKp5bpsjh87uhwjupyxgclxy8h8` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`artist_id`),
  CONSTRAINT `FKq5vop77s6nx12cnyxgqdyi0or` FOREIGN KEY (`album_id`) REFERENCES `albums` (`album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.album_artist: ~0 rows (approximately)

-- Dumping structure for table websitenghenhac.artists
CREATE TABLE IF NOT EXISTS `artists` (
  `artist_id` bigint NOT NULL AUTO_INCREMENT,
  `artist_name` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`artist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.artists: ~9 rows (approximately)
REPLACE INTO `artists` (`artist_id`, `artist_name`, `avatar`, `bio`, `country`) VALUES
	(1, 'Sơn Tùng MTP', 'images/albumImg/SonTung.png', 'Ca sĩ top 1 Viet Nam', 'Việt Nam'),
	(2, 'MCK', 'images/albumImg/MCK.png', 'Rapper', 'Việt Nam'),
	(3, 'Hương Tràm', 'images/albumImg/HuongTram.png', 'Ca sĩ', 'Việt Nam'),
	(4, 'SOOBIN', 'images/albumImg/SOOBIN.png', 'Ca sĩ', 'Việt Nam'),
	(5, 'Vương Anh Tú', 'images/albumImg/VuongAnhTu.png', 'Ca sĩ', 'Việt Nam'),
	(6, 'Karik', 'images/albumImg/Karik.png', 'Rapper', 'Việt Nam'),
	(7, 'Phương Ly', 'images/albumImg/PhuongLy.png', 'Ca sĩ', 'Việt Nam'),
	(8, 'B Ray', 'images/albumImg/BRay.png', 'Rapper', 'Việt Nam'),
	(9, 'G-Dragon', 'images/albumImg/GDragon.png', 'Rapper', 'Hàn Quốc');

-- Dumping structure for table websitenghenhac.comments
CREATE TABLE IF NOT EXISTS `comments` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `song_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKjss5ndgf3fog24fnj5oo19712` (`song_id`),
  KEY `FKqi14bvepnwtjbbaxm7m4v44yg` (`user_id`),
  CONSTRAINT `FKjss5ndgf3fog24fnj5oo19712` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`),
  CONSTRAINT `FKqi14bvepnwtjbbaxm7m4v44yg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.comments: ~3 rows (approximately)
REPLACE INTO `comments` (`comment_id`, `date`, `text`, `song_id`, `user_id`) VALUES
	(1, '2024-06-29 16:57:32.997000', 'chill', 1, 3),
	(2, '2024-06-29 17:07:46.900000', '3', 1, 3),
	(3, '2024-06-29 17:27:04.896000', 'gh', 1, 3);

-- Dumping structure for table websitenghenhac.genres
CREATE TABLE IF NOT EXISTS `genres` (
  `genre_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.genres: ~3 rows (approximately)
REPLACE INTO `genres` (`genre_id`, `description`, `name`) VALUES
	(1, 'Pop', 'Pop'),
	(2, 'Ballad', 'Ballad'),
	(3, 'Rap', 'Rap');

-- Dumping structure for table websitenghenhac.playlists
CREATE TABLE IF NOT EXISTS `playlists` (
  `playlist_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`playlist_id`),
  KEY `FK51mk6rie8wb7wwoblf0vwphua` (`user_id`),
  CONSTRAINT `FK51mk6rie8wb7wwoblf0vwphua` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.playlists: ~0 rows (approximately)

-- Dumping structure for table websitenghenhac.playlist_details
CREATE TABLE IF NOT EXISTS `playlist_details` (
  `playlistId` bigint NOT NULL,
  `playlist_id` bigint NOT NULL,
  `songId` bigint NOT NULL,
  `song_id` bigint NOT NULL,
  PRIMARY KEY (`playlistId`,`songId`),
  KEY `FK76b989rsay9oqohfb4qdvaxtr` (`playlist_id`),
  KEY `FKsawrj4yjsl06ft7aann6bgnrj` (`song_id`),
  CONSTRAINT `FK76b989rsay9oqohfb4qdvaxtr` FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`playlist_id`),
  CONSTRAINT `FKsawrj4yjsl06ft7aann6bgnrj` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.playlist_details: ~0 rows (approximately)

-- Dumping structure for table websitenghenhac.ratings
CREATE TABLE IF NOT EXISTS `ratings` (
  `rating_id` bigint NOT NULL AUTO_INCREMENT,
  `rating_value` int NOT NULL,
  `song_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`rating_id`),
  KEY `FKnnav5h90sv58tnjyl3ykgpcpc` (`song_id`),
  KEY `FKgd4laqdp3puv1bxgmh62tj8hn` (`user_id`),
  CONSTRAINT `FKgd4laqdp3puv1bxgmh62tj8hn` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKnnav5h90sv58tnjyl3ykgpcpc` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.ratings: ~1 rows (approximately)
REPLACE INTO `ratings` (`rating_id`, `rating_value`, `song_id`, `user_id`) VALUES
	(1, 5, 1, 3);

-- Dumping structure for table websitenghenhac.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(250) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.role: ~2 rows (approximately)
REPLACE INTO `role` (`id`, `description`, `name`) VALUES
	(1, 'admin', 'Admin'),
	(2, 'user', 'User');

-- Dumping structure for table websitenghenhac.songs
CREATE TABLE IF NOT EXISTS `songs` (
  `song_id` bigint NOT NULL AUTO_INCREMENT,
  `duration` int NOT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `lyric` varchar(255) DEFAULT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `album_id` bigint DEFAULT NULL,
  `genre_id` bigint DEFAULT NULL,
  PRIMARY KEY (`song_id`),
  KEY `FKte4gkb2cqtk2erfa87oopj2cj` (`album_id`),
  KEY `FKd5mor9lg3wkqhn2tp0r75nkm` (`genre_id`),
  CONSTRAINT `FKd5mor9lg3wkqhn2tp0r75nkm` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`genre_id`),
  CONSTRAINT `FKte4gkb2cqtk2erfa87oopj2cj` FOREIGN KEY (`album_id`) REFERENCES `albums` (`album_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.songs: ~27 rows (approximately)
REPLACE INTO `songs` (`song_id`, `duration`, `file_path`, `image_path`, `lyric`, `release_date`, `title`, `album_id`, `genre_id`) VALUES
	(1, 0, '/audio/SonTung_ECNHQ.mp3', '/images/songImg/SonTung_ECNHQ.png', 'hh', '2024-06-10 00:00:00.000000', 'Em của ngày hôm qua', 1, 1),
	(2, 0, '/audio/SonTung_LT.mp3', '/images/songImg/SonTung_LT.png', 'f', '2024-07-03 00:00:00.000000', 'Lạc trôi', 1, 1),
	(3, 0, '/audio/SonTung_DLTTAD.mp3', '/images/songImg/SonTung_DLTTAD.png', 'd', '2024-07-03 00:00:00.000000', 'Dừng làm trái tim anh đau', 1, 1),
	(4, 0, '/audio/SOOBIN_GN.mp3', '/images/songImg/SOOBIN_GN.png', 'f', '2024-07-03 00:00:00.000000', 'Giá như', 4, 2),
	(5, 0, '/audio/SOOBIN_PSMCG.mp3', '/images/songImg/SOOBIN_PSMCG.png', 'd', '2024-07-03 00:00:00.000000', 'Phía sau một cô gái', 4, 2),
	(6, 0, '/audio/SOOBIN_ADQVCD.mp3', '/images/songImg/SOOBIN_ADQVCD.png', 'd', '2024-07-03 00:00:00.000000', 'Anh đã quen với cô đơn', 4, 2),
	(7, 0, '/audio/MCK_TVS.mp3', '/images/songImg/MCK_TVS.png', 'd', '2024-07-03 00:00:00.000000', 'Tại vì sao', 6, 3),
	(8, 0, '/audio/MCK_ADOH.mp3', '/images/songImg/MCK_ADOH.png', 'd', '2024-07-03 00:00:00.000000', 'Anh đã ổn hơn', 6, 3),
	(9, 0, '/audio/MCK_NNTY.mp3', '/images/songImg/MCK_NNTY.png', 'd', '2024-07-03 00:00:00.000000', 'Nghe như tình yêu', 6, 3),
	(10, 0, '/audio/HuongTram_EGM.mp3', '/images/songImg/HuongTram_EGM.png', 'd', '2024-07-03 00:00:00.000000', 'Em gái mưa', 3, 2),
	(11, 0, '/audio/HuongTram_Ngoc.png', '/images/songImg/HuongTram_Ngoc.png', 'd', '2024-07-03 00:00:00.000000', 'Ngốc', 3, 2),
	(12, 0, '/audio/HuongTram_CEGATCN.mp3', '/images/songImg/HuongTram_CEGATCN.png', 'd', '2024-07-03 00:00:00.000000', 'Cho em gần anh thêm chút nữa', 3, 2),
	(13, 0, '/audio/Bray_CO20.mp3', '/images/songImg/Bray_CO20.png', 'd', '2024-07-03 00:00:00.000000', 'Cao ốc 20', 10, 3),
	(14, 0, '/audio/BRay_OTTP.mp3', '/images/songImg/BRay_OTTP.png', '', '2024-07-03 00:00:00.000000', 'Ở trong thành phố', 10, 3),
	(15, 0, '/audio/BRay_HH.mp3', '/images/songImg/BRay_HH.png', '', '2024-07-03 00:00:00.000000', 'Hoàn hảo', 10, 3),
	(16, 0, '/audio/VuongAnhTu_CSEOK.mp3', '/images/songImg/VuongAnhTu_CSEOK.png', '', '2024-07-03 00:00:00.000000', 'Cuộc sống em ổn không', 5, 2),
	(17, 0, '/audio/VuongAnhTu_ASOT.mp3', '/images/songImg/VuongAnhTu_ASOT.png', '', '2024-07-03 00:00:00.000000', 'Anh sẽ ổn thôi', 5, 2),
	(18, 0, '/audio/VuongAnhTu_GATLNCH.mp3', '/images/songImg/VuongAnhTu_GATLNCH.png', '', '2024-07-03 00:00:00.000000', 'Giúp anh trả lời những câu hỏi', 5, 2),
	(19, 0, '/audio/PhuongLy_TT.mp3', '/images/songImg/PhuongLy_TT.png', '', '2024-07-03 00:00:00.000000', 'Thích thích', 8, 1),
	(20, 0, '/audio/PhuongLy_ALNLCE.mp3', '/images/songImg/PhuongLy_ALNLCE.png', '', '2024-07-03 00:00:00.000000', 'Anh là ngoại lệ của em', 8, 1),
	(21, 0, '/audio/PhuongLy_MSY.mp3', '/images/songImg/PhuongLy_MSY.png', '', '2024-07-03 00:00:00.000000', 'Missing you', 8, 1),
	(22, 0, '/audio/GDRAGON_THATXX.mp3', '/images/songImg/GDRAGON_THATXX.png', '', '2024-07-03 00:00:00.000000', 'That xx', 9, 3),
	(23, 0, '/audio/GDRAGON _CROOKED.mp3', '/images/songImg/GDRAGON _CROOKED.png', '', '2024-07-03 00:00:00.000000', 'Crooked', 9, 3),
	(24, 0, '/audio/GDRAGON_Untitled.mp3', '/images/songImg/GDRAGON_Untitled.png', '', '2024-07-03 00:00:00.000000', 'Untitled', 9, 3),
	(25, 0, '/audio/Karik_3TB.mp3', '/images/songImg/Karik_3TB.png', '', '2024-07-03 00:00:00.000000', '3 thằng bạn', 7, 3),
	(26, 0, '/audio/Karik_TLTC.mp3', '/images/songImg/Karik_TLTC.png', '', '2024-07-03 00:00:00.000000', 'Từng là tất cả', 7, 3),
	(27, 0, '/audio/Karik_DLAET.mp3', '/images/songImg/Karik_DLAET.png', '', '2024-07-03 00:00:00.000000', 'Đó là anh em tao', 7, 3);

-- Dumping structure for table websitenghenhac.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_non_locked` bit(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date_of_birth` datetime(6) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `provider` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_589idila9li6a4arw1t8ht1gx` (`phone`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.user: ~3 rows (approximately)
REPLACE INTO `user` (`id`, `account_non_locked`, `address`, `date_of_birth`, `email`, `password`, `phone`, `provider`, `username`) VALUES
	(1, NULL, NULL, NULL, 'user@gmail.com', 'Huynhtanloc_0x', '0931912103', NULL, 'user'),
	(2, b'1', NULL, NULL, 'user1@gmail.com', '$2a$10$EzOMbWYF1V/Y.ropLEJumOpieYGo4CRNPBZlREJ0UI2UtL5ptOcOq', '0123456789', NULL, 'user1'),
	(3, b'1', NULL, NULL, 'user2@gmail.com', '$2a$10$Wjxlrrwsohc6XZOytlg7iei7laE0nr1QrFrsH1ilrLGLbWkLzyYca', '0123456987', NULL, 'user2');

-- Dumping structure for table websitenghenhac.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table websitenghenhac.user_role: ~2 rows (approximately)
REPLACE INTO `user_role` (`user_id`, `role_id`) VALUES
	(2, 2),
	(3, 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
