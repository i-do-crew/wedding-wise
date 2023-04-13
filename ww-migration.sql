-- Adminer 4.8.1 MySQL 5.5.5-10.6.12-MariaDB-0ubuntu0.22.04.1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS `weddingwise`;
CREATE DATABASE `weddingwise` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `weddingwise`;

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

INSERT INTO `customers` (`id`, `budget`, `guest_count`, `city_state`, `partner_fname`, `partner_lname`, `user_id`) VALUES
(1,	7600,	75,	'San Antonio, TX',	'Damian',	'Brooks',	12);

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
`dalb_category_id` bigint(20) NOT NULL,
`vendor_id` bigint(20) NOT NULL,
PRIMARY KEY (`id`),
KEY `vendor_id` (`vendor_id`),
KEY `djs_and_live_bands_djs_and_live_bands_categories_id_fk` (`dalb_category_id`),
CONSTRAINT `djs_and_live_bands_djs_and_live_bands_categories_id_fk` FOREIGN KEY (`dalb_category_id`) REFERENCES `djs_and_live_bands_categories` (`id`),
CONSTRAINT `djs_and_live_bands_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `djs_and_live_bands` (`id`, `dalb_category_id`, `vendor_id`) VALUES
(1,	1,	3);

DROP TABLE IF EXISTS `djs_and_live_bands_categories`;
CREATE TABLE `djs_and_live_bands_categories` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`title` varchar(50) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `djs_and_live_bands_categories` (`id`, `title`) VALUES
(1,	'DJ'),
(2,	'Live Band'),
(3,	'Ensemble'),
(4,	'Solo Artist');

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

INSERT INTO `guest_lists` (`id`, `fname`, `lname`, `plus_one`, `email`, `ph_number`, `street`, `apt_no`, `city`, `state`, `zip`, `rsvp`, `customer_id`) VALUES
(1,	'clayton',	'priestley',	CONV('1', 2, 10) + 0,	'clayton@email.com',	'210-222-1111',	'4926 Happy St',	NULL,	'San Antonio',	'TX',	'78220',	'pending',	1);

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
`photo_format_id` bigint(20) NOT NULL,
`vendor_id` bigint(20) NOT NULL,
PRIMARY KEY (`id`),
KEY `vendor_id_fk` (`vendor_id`),
KEY `photographers_photo_format_id_fk` (`photo_format_id`),
CONSTRAINT `photographers_photo_format_id_fk` FOREIGN KEY (`photo_format_id`) REFERENCES `photo_format` (`id`),
CONSTRAINT `vendor_id_fk` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `photographers` (`id`, `photo_format_id`, `vendor_id`) VALUES
(2,	1,	2),
(3,	4,	2);

DROP TABLE IF EXISTS `photo_format`;
CREATE TABLE `photo_format` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`title` varchar(50) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `photo_format` (`id`, `title`) VALUES
(1,	'Digital'),
(2,	'Film'),
(3,	'Both'),
(4,	'Hybrid');

DROP TABLE IF EXISTS `principle_groups`;
CREATE TABLE `principle_groups` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`code` varchar(20) NOT NULL,
`group` varchar(20) NOT NULL,
`description` varchar(50) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `principle_groups` (`id`, `code`, `group`, `description`) VALUES
(1,	'ADMIN',	'Admin Group',	'Admin Group Authority'),
(2,	'CUSTOMER',	'Customer Group',	'Customer Group Authority');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`email` varchar(50) NOT NULL,
`username` varchar(50) NOT NULL,
`password` varchar(100) NOT NULL,
`role_id` int(10) unsigned DEFAULT 1,
`first_name` varchar(25) NOT NULL,
`last_name` varchar(25) NOT NULL,
`account_verified` bit(1) NOT NULL DEFAULT b'0',
`failed_login_attempts` int(11) NOT NULL DEFAULT 0,
`login_disabled` bit(1) NOT NULL DEFAULT b'0',
`account_non_expired` bit(1) NOT NULL DEFAULT b'1',
`account_non_locked` bit(1) NOT NULL DEFAULT b'1',
`credentials_non_expired` bit(1) NOT NULL DEFAULT b'1',
PRIMARY KEY (`id`),
UNIQUE KEY `email` (`email`),
UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `users` (`id`, `email`, `username`, `password`, `role_id`, `first_name`, `last_name`, `account_verified`, `failed_login_attempts`, `login_disabled`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`) VALUES
(1,	'micah@email.com',	'micah@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'micah',	'larson',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(2,	'andrea@email.com',	'andrea@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'andrea',	'varnado',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(3,	'billie@email.com',	'billie@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'billie',	'dorries',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(4,	'clayton@email.com',	'clayton@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'clayton',	'priestley',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(5,	'raul@email.com',	'raul@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'Raul',	'Hernandez',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(6,	'evelyn@email.com',	'aliyah@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'Evelyn',	'Garcia',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(7,	'mario@email.com',	'maria@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'Mario',	'Ruiz',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(8,	'greg@email.com',	'greg@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'Greg',	'Reese',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(9,	'tommy@email.com',	'tommy@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'Tommy',	'Scratch',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(10,	'desareye@email',	'desareye@email',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'Desareye',	'Gonzales',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(11,	'jenna@email.com',	'jenna@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'Jenna',	'Barbee',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(12,	'chrisit@email.com',	'chrisit@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	1,	'Christina',	'Anton',	CONV('0', 2, 10) + 0,	0,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `user_groups`;
CREATE TABLE `user_groups` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`user_id` bigint(20) DEFAULT NULL,
`group_id` bigint(20) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `user_groups_group_id_fk` (`group_id`),
KEY `user_groups_user_id_fk` (`user_id`),
CONSTRAINT `user_groups_group_id_fk` FOREIGN KEY (`group_id`) REFERENCES `principle_groups` (`id`),
CONSTRAINT `user_groups_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `user_groups` (`id`, `user_id`, `group_id`) VALUES
(1,	1,	1),
(2,	2,	1),
(3,	3,	1),
(4,	4,	1),
(5,	5,	2),
(6,	6,	2),
(7,	7,	2),
(8,	8,	2),
(9,	9,	2),
(10,	10,	2),
(11,	11,	2),
(12,	12,	2);

DROP TABLE IF EXISTS `vendors`;
CREATE TABLE `vendors` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`title` varchar(50) DEFAULT NULL,
`category_id` bigint(20) NOT NULL,
`city_state` varchar(25) NOT NULL,
`about` longtext DEFAULT NULL,
`user_id` bigint(20) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `categories_id_fk` (`category_id`),
KEY `vendors_users_id_fk` (`user_id`),
CONSTRAINT `categories_id_fk` FOREIGN KEY (`category_id`) REFERENCES `vendor_categories` (`id`),
CONSTRAINT `vendors_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `vendors` (`id`, `title`, `category_id`, `city_state`, `about`, `user_id`) VALUES
(1,	'Rancho La Mission',	1,	'San Antonio, TX',	'Welcome to Rancho la Mission. We have two venues to choose from – the outdoor venue and the indoor venue, aka “The Barn”. To start the outdoor venue gives you an open-air feel with a rustic winter wonderland filled with lots of tree lights. Plenty of outdoor space for any outdoor event. If you prefer a indoor venue, than The Barn is a perfect, country theme for your ceremony and reception or any event. We are honored to host your event and look forward to start planning your BIG day!\n',	5),
(2,	'Evelyn Photography',	2,	'San Antonio, TX',	'Evelyn here, I\'m the one behind the camera, I could spend the whole day talking about myself, but here are a few things you should know about me. As a single mother of two girls, I am the most dedicated and passionate person when it comes to being your photographer. Spanish is my first language since I\'m 100% Mexican! While I was born and raised in a small border town called Piedras Negras, my heart belongs to San Antonio. I have lived here for 16 years, so I am practically half and half.\n\nPhotography is not just a hobby for me. It is a way of expression, passion, and art. Every photograph has a story to tell, and there is no better way to remember you or your loved ones than through a photograph. So don\'t hesitate to chat, tell me all about your special moment, and I\'ll be there!',	6),
(3,	'DJMusic Entertainment LLC',	7,	'San Antonio, TX',	'At DJMusic Entertainment, we bring our client\'s vision to life with an Exceptional and Unique, Entertainment and Wedding Planning Experience. Our main goal and passion are to go beyond our clients’ expectations. We believe that small details can make a big difference.\n\nWe are passionate about weddings, our team is always an extraordinary duo of DJ and Timeline Assistant who will make sure the Ceremony and Reception run smooth and on time. We help our brides with educational materials.',	7),
(4,	'Greg Reese Events',	3,	'San Antonio, TX',	'For over two decade Greg Reese designed events for others like Events Destination Weddings Galas. His passion and desire to set out on his own and to create his own identity. In other words, he had a vision of creating a company unlike any other in South Texas. He knew that with his creative ideas, eye for luxury and knowledge of the industry, there would be tremendous opportunities for his new startup.\n\nBecause Greg Reese Events team sets us apart. We’re a group of professionals that share a passion for what we do, as well as a commitment to make your event everything you’ve imagined and beyond. Today Greg Reese plans and designs elegant and spectacular weddings, corporate and destination weddings, We take great pride in creating one-of-a-kind events that truly represent the clients’ style and personality. Greg creates more than just a party, we create an event that tells the complete story of the couple.',	8),
(5,	'Scratch Kitchen',	4,	'San Antonio, TX',	'We are a family-owned bakery, restaurant, and catering company that offers delicious desserts and fine food in San Antonio\'s Alta Vista neighborhood. We are the perfect caterer for brunch weddings, garden weddings, or any wedding where a delicious family-style meal or buffet meal is preferred over a formal sit-down dinner. We also love to cater bridal showers, baby showers, birthday celebrations, and all other parties and meetings!\n\nOwner Becky Medellin serves as the company\'s catering manager, ensuring that couples have her full attention and her eye on all the details. From the menu tasting to the big night, our top priority is delighting the bride and groom!',	9),
(6,	'Tasty Swirls Company',	5,	'San Antonio, TX',	'My name is Desareye and I am the owner of Tasty Swirls Company. I’ve always had a creative side and loved desserts so I wanted to start a business that would combine both. Most importantly share it with you! Cotton Candy has always been a favorite as it is so fluffy, fun and just melts in your mouth making you want more! I wanted our cotton candy to be a little different than your normal pink and blue cotton candy so I decided why not make it with unique flavors. I started to think of what flavors I would do and decided hmm these would taste even better with toppings! We can’t wait to be apart of your event!\n\nTasty Swirls company is known for making the Cotton candy with organic raw cane sugar, natural flavors, and no artificial dyes. We add toppings to our cotton candy which include sprinkles, tajín, sour patch kids, chocolate drizzle and so much more! We have the cart as a blank canvas to help customers design it how they want. Also we can cater to any event whether it be a wedding, baby shower, birthday party, corporate event or public event. I honestly enjoy seeing people of any age get excited when they see their cotton candy getting made and when they take their first bite. Their face just lights up!',	10),
(7,	'Barmasters Texas',	6,	'San Antonio, TX',	'BarMasters Texas is a full service bartending and bar planning company based in the San Antonio & Austin areas. We serve at weddings and other special events such as banquets, corporate parties, private events, graduation parties, birthday celebrations, bridal showers, and more. We work closely with you to create the perfect bar for your event! We look forward to the opportunity to serve you at your special event.\n\nWe provide full bartending and planning packages, bartenders by the hour, nonalcoholic options, consultations and more! We work closely with you to create the bar that fits any budget, theme, color scheme, season, etc! We are committed to giving brides and grooms a high quality bar at a reasonable price.',	11);

DROP TABLE IF EXISTS `vendors_music_genres`;
CREATE TABLE `vendors_music_genres` (
`id` bigint(20) NOT NULL,
`vendor_id` bigint(20) NOT NULL,
PRIMARY KEY (`id`,`vendor_id`),
KEY `FKil0vbajtjymahkv4yy2ffl61i` (`vendor_id`),
CONSTRAINT `FKil0vbajtjymahkv4yy2ffl61i` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`),
CONSTRAINT `FKqge5jyfdk09ewdj9qmqt6vg7m` FOREIGN KEY (`id`) REFERENCES `music_genres` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `vendor_categories`;
CREATE TABLE `vendor_categories` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`title` varchar(50) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `vendor_categories` (`id`, `title`) VALUES
(1,	'Venues'),
(2,	'Photographers'),
(3,	'Florists'),
(4,	'Catering'),
(5,	'Cakes and dessert'),
(6,	'Bar and beverage'),
(7,	'Bands and DJs');

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

INSERT INTO `vendor_packages` (`id`, `title`, `description`, `vendor_id`) VALUES
(1,	'Barn or Outdoor Courtyard',	'Welcome to Rancho la Mission. We have two venues to choose from – the outdoor venue and the indoor venue, aka “The Barn”. To start the outdoor venue gives you an open-air feel with a rustic winter wonderland filled with lots of tree lights. Plenty of outdoor space for any outdoor event. If you prefer a indoor venue, than The Barn is a perfect, country theme for your ceremony and reception or any event. We are honored to host your event and look forward to start planning your BIG day!',	1),
(2,	'Engagements',	'The engagement sessions are included with your wedding package and are a great way to get to know your photographer, and if you only need a save-the-date, say no more!',	2),
(3,	'Bridals',	'Get your hair and makeup done, with an 18 x 24 print to display on your special day, and at least three hours of coverage.',	2),
(4,	'Weddings',	'Wedding coverage starts with 8 hours of coverages and a second photographer a fabric cover album and online gallery.',	2),
(5,	'Ceremony Package',	'This Complete Ceremony Package contains all the features for a beautiful wedding service. Your ceremony starts with background music of your choice and style to set the perfect romantic mood as your guests take their seats. The duties of our DJ & Event Expert include the correct timing of music being played as your wedding party walks in and constant monitoring of the microphone to prevent annoying feedback.',	3),
(6,	'Silver Package',	'Our \"Silver Reception Package\" contains all the features for a great celebration! It starts with cocktail and dinner music of your choice to set the perfect mood for your reception. Our DJ will perform the duties of Master of Ceremony, including introductions, toasts, and other formalities you wish to include, it will also perfectly mix your favorite dance tunes & motivate your guests.',	3),
(7,	'Gold Package',	'This \"Gold Reception Package\" has the same features as our Silver Package plus one Enhancement.',	3),
(8,	'Platinum Package',	'Our \"Platinum Reception Package\" has the same features as our Gold Package but it includes two enhancements.',	3),
(9,	'Diamond Package',	'Our \"Diamond Reception Package\" has the same features as our Platinum Package but it includes 2 hours of our Photo Booth',	3),
(10,	'Enhancements',	'You can pick between one or two enhancements depending of you wedding package',	3),
(11,	'Intimate',	'1 Hour Live Spun Cotton Candy Service with Unlimited Servings • 3-4 Flavors • Toppings Included!',	6),
(12,	'Social',	'2 Hour Live Spun Cotton Candy Service with Unlimited Servings • 3-4 Flavors • Toppings Included!',	6),
(13,	'Grand',	'3 Hour Live Spun Cotton Candy Service with Unlimited Servings • 3-4 Flavors • Toppings Included!',	6);

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

INSERT INTO `vendor_services` (`id`, `title`, `vendor_id`) VALUES
(1,	'Bar services',	1),
(2,	'Catering services',	1),
(3,	'Clean up',	1),
(4,	'Dance floor',	1),
(5,	'Dressing room / Bridal Suite',	1),
(6,	'Event coordinator',	1),
(7,	'Event rentals',	1),
(8,	'Event staff',	1),
(9,	'Liability insurance',	1),
(10,	'Lighting/Sound',	1),
(11,	'On-site accomodations',	1),
(12,	'Pet friendly',	1),
(13,	'Service staff',	1),
(14,	'Set up',	1),
(15,	'Transportation',	1),
(16,	'Wedding cake services',	1),
(17,	'Wheelchair accessible',	1),
(18,	'Wifi',	1),
(19,	'Bride-only session',	2),
(20,	'Engagement session',	2),
(21,	'Extra hours',	2),
(22,	'Image editing',	2),
(23,	'Online proofing',	2),
(24,	'Printing rights',	2),
(25,	'Second photographer',	2),
(43,	'Ceremony microphones and sound',	3),
(44,	'Dance floor special effects',	3),
(45,	'Dancefloor lighting',	3),
(46,	'Event planning',	3),
(47,	'Fog/Snow machines',	3),
(48,	'Master of ceremonies',	3),
(49,	'Multimedia displays',	3),
(50,	'Multiple location support',	3),
(51,	'Music during breaks',	3),
(52,	'Photobooth',	3),
(53,	'Projector equipment',	3),
(54,	'Signs',	3),
(55,	'Sound system capability audience <300 -',	3),
(56,	'Sound system capability audience >300',	3),
(57,	'Sparklers',	3),
(58,	'Stage design',	3),
(59,	'Uplighting',	3),
(60,	'A La Carte',	4),
(61,	'Full-Service Floral Design',	4),
(62,	'Consultations',	4),
(63,	'Mock-ups',	4),
(64,	'Venue visits',	4),
(65,	'Cleanup',	4),
(66,	'Container rentals',	4),
(67,	'Day-of coordination',	4),
(68,	'Decor rentals',	4),
(69,	'Delivery',	4),
(70,	'Furniture rentals',	4),
(71,	'Setup',	4),
(72,	'Structure rentals',	4),
(73,	'Consultations and tastings - for a fee',	5),
(74,	'Delivery and setup',	5),
(75,	'Consultations',	6),
(76,	'Delivery',	6),
(77,	'Setup',	6),
(78,	'Tastings',	6),
(79,	'Rental coordination - directly invoice couple',	7);

-- 2023-04-13 17:15:59