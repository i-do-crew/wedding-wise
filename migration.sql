-- Adminer 4.8.1 MySQL 5.5.5-10.6.12-MariaDB-0ubuntu0.22.04.1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

CREATE DATABASE `weddingwise` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `weddingwise`;

DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `categories` (`id`, `title`) VALUES
    (1,	'Venues'),
    (2,	'Photographers'),
    (3,	'Florists'),
    (4,	'Catering'),
    (5,	'Cakes and dessert'),
    (6,	'Bar and beverage'),
    (7,	'Bands and DJs');

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `budget` int(11) DEFAULT NULL,
    `guest_count` int(11) DEFAULT NULL,
    `city_state` varchar(25) NOT NULL,
    `partner_fname` varchar(50) NOT NULL,
    `partner_lname` varchar(50) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `foreign_key_name` (`user_id`),
    CONSTRAINT `foreign_key_name` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `customer_vendors`;
CREATE TABLE `customer_vendors` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `favorited` tinyint(1) DEFAULT 0,
    `amount_quoted` int(11) NOT NULL,
    `customer_id` bigint(20) NOT NULL,
    `vendor_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `customer_id` (`customer_id`),
    KEY `vendor_id` (`vendor_id`),
    CONSTRAINT `customer_vendors_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
    CONSTRAINT `customer_vendors_ibfk_2` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `djs_and_live_bands`;
CREATE TABLE `djs_and_live_bands` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(50) NOT NULL,
    `category` varchar(50) NOT NULL,
    `vendor_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `vendor_id` (`vendor_id`),
    CONSTRAINT `djs_and_live_bands_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`),
    CONSTRAINT `CONSTRAINT_1` CHECK (`category` in ('DJ','Live Band','Ensemble','Solo Artist'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `djs_and_live_bands_music_genres`;
CREATE TABLE `djs_and_live_bands_music_genres` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `dj_or_live_band_id` bigint(20) NOT NULL,
    `music_genre_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `dj_or_live_band_id` (`dj_or_live_band_id`),
    KEY `music_genre_id` (`music_genre_id`),
    CONSTRAINT `djs_and_live_bands_music_genres_ibfk_1` FOREIGN KEY (`dj_or_live_band_id`) REFERENCES `djs_and_live_bands` (`id`),
    CONSTRAINT `djs_and_live_bands_music_genres_ibfk_2` FOREIGN KEY (`music_genre_id`) REFERENCES `music_genres` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `guest_lists`;
CREATE TABLE `guest_lists` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `fname` varchar(50) NOT NULL,
   `lname` varchar(50) NOT NULL,
   `plus_one` bit(1) NOT NULL DEFAULT b'0',
   `email` varchar(50) NOT NULL,
   `ph_number` varchar(20) NOT NULL,
   `street` varchar(50) NOT NULL,
   `apt_no` varchar(20) DEFAULT NULL,
   `city` varchar(50) NOT NULL,
   `state` varchar(2) NOT NULL,
   `zip` varchar(10) NOT NULL,
   `rsvp` varchar(10) NOT NULL DEFAULT 'pending',
   `customer_id` bigint(20) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `customer_id` (`customer_id`),
   CONSTRAINT `guest_lists_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
   CONSTRAINT `CONSTRAINT_1` CHECK (`rsvp` in ('yes','no','pending'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `idea_board`;
CREATE TABLE `idea_board` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filepath` varchar(100) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `idea_board_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `music_genres`;
CREATE TABLE `music_genres` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `music_genres` (`id`, `title`) VALUES
    (1,	'Top 40/Pop'),
    (2,	'Classic Rock'),
    (3,	'R&B/Soul'),
    (4,	'Motown'),
    (5,	'Disco'),
    (6,	'Funk'),
    (7,	'Hip-Hop/Rap'),
    (8,	'Country'),
    (9,	'Jazz'),
    (10,	'Swing'),
    (11,	'Big Band'),
    (12,	'Latin'),
    (13,	'Reggae'),
    (14,	'80s/90s Pop'),
    (15,	'Electronic/Dance');

DROP TABLE IF EXISTS `photographers`;
CREATE TABLE `photographers` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `photo_format` varchar(25) NOT NULL CHECK (`photo_format` in ('Digital','Film','Both')),
    `vendor_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `vendor_id_fk` (`vendor_id`),
    CONSTRAINT `vendor_id_fk` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `email` varchar(50) NOT NULL,
    `password` varchar(100) NOT NULL,
    `username` varchar(50) NOT NULL,
    `role_id` int(10) unsigned DEFAULT 1,
    `fullname` varchar(50) DEFAULT NULL,
    `account_verified` bit(1) NOT NULL,
    `failed_login_attempts` int(11) NOT NULL,
    `login_disabled` bit(1) NOT NULL,
    `account_non_expired` bit(1) NOT NULL DEFAULT b'1',
    `account_non_locked` bit(1) NOT NULL DEFAULT b'1',
    `credentials_non_expired` bit(1) NOT NULL DEFAULT b'1',
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`),
    UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `vendors`;
CREATE TABLE `vendors` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(50) DEFAULT NULL,
    `categories_id` bigint(20) NOT NULL,
    `city_state` varchar(25) NOT NULL,
    `about` longtext DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `categories_id_fk` (`categories_id`),
    CONSTRAINT `categories_id_fk` FOREIGN KEY (`categories_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `vendor_packages`;
CREATE TABLE `vendor_packages` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(50) NOT NULL,
    `description` varchar(1000) NOT NULL,
    `vendor_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `vendor_id` (`vendor_id`),
    CONSTRAINT `vendor_packages_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `vendor_ratings_reviews`;
CREATE TABLE `vendor_ratings_reviews` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `rating` int(11) NOT NULL,
    `cost` int(11) DEFAULT NULL,
    `title` varchar(100) NOT NULL,
    `description` varchar(2500) NOT NULL,
    `customer_id` bigint(20) NOT NULL,
    `vendor_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `customer_id` (`customer_id`),
    KEY `vendor_id` (`vendor_id`),
    CONSTRAINT `vendor_ratings_reviews_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
    CONSTRAINT `vendor_ratings_reviews_ibfk_2` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`),
    CONSTRAINT `rating_check` CHECK (`rating` between 1 and 5),
    CONSTRAINT `cost_check` CHECK (`cost` between 1 and 4)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `vendor_services`;
CREATE TABLE `vendor_services` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(50) NOT NULL,
    `vendor_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `vendor_id` (`vendor_id`),
    CONSTRAINT `vendor_services_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;