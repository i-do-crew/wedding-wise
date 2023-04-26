#Create User
INSERT INTO `users` (`email`, `username`, `password`, `first_name`, `last_name`) VALUES
('kennith.mayo@email.com',	'kennith.mayo@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	'kenneth',	'mayo'),
('kennith.mayo@email.com',	'kennith.mayo@email.com',	'$2a$10$GYNeIa.HSIqhpGA3tYfuxefGKJ1gCckwTyQAGXjaTs4WBjl3NKlGu',	'kenneth',	'mayo');

#Assign User Group 1-Admin, 2-Customer, 3-Vendor
INSERT INTO `user_groups` (`user_id`, `group_id`) VALUES
(1,	3),
(2,	3);

#Create Vendor
INSERT INTO `vendors` (`business_name`, `category_id`, `about`, `user_id`) VALUES
('Rancho La Mission',	1,	'Welcome to Rancho la Mission. We have two venues to choose from – the outdoor venue and the indoor venue, aka ""The Barn"". To start the outdoor venue gives you an open-air feel with a rustic winter wonderland filled with lots of tree lights. Plenty of outdoor space for any outdoor event. If you prefer a indoor venue, than The Barn is a perfect, country theme for your ceremony and reception or any event. We are honored to host your event and look forward to start planning your BIG day!',	5),
('Rancho La Mission',	1,	'Welcome to Rancho la Mission. We have two venues to choose from – the outdoor venue and the indoor venue, aka ""The Barn"". To start the outdoor venue gives you an open-air feel with a rustic winter wonderland filled with lots of tree lights. Plenty of outdoor space for any outdoor event. If you prefer a indoor venue, than The Barn is a perfect, country theme for your ceremony and reception or any event. We are honored to host your event and look forward to start planning your BIG day!',	5);

#Add Vendor's Services Provided
INSERT INTO `vendor_services` (`title`, `vendor_id`) VALUES
('Bride-only session', 1),
('Engagement session', 1),
('Image editing', 1),
('Extra hours', 1),
('Image editing', 1),
('Online proofing', 1),
('Printing rights', 1),
('Same-day edits', 1),
('Second photographer', 1);

#Add Vendor Packages if applicable
INSERT INTO `vendor_packages` (`title`, `description`, `vendor_id`) VALUES
('Barn or Outdoor Courtyard',	'Welcome to Rancho la Mission. We have two venues to choose from – the outdoor venue and the indoor venue, aka “The Barn”. To start the outdoor venue gives you an open-air feel with a rustic winter wonderland filled with lots of tree lights. Plenty of outdoor space for any outdoor event. If you prefer a indoor venue, than The Barn is a perfect, country theme for your ceremony and reception or any event. We are honored to host your event and look forward to start planning your BIG day!',	1),
('Engagements',	'The engagement sessions are included with your wedding package and are a great way to get to know your photographer, and if you only need a save-the-date, say no more!',	2),
('Bridals',	'Get your hair and makeup done, with an 18 x 24 print to display on your special day, and at least three hours of coverage.',	2);
