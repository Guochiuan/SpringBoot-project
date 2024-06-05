DROP DATABASE  IF EXISTS `drone_delivery`;

CREATE DATABASE  IF NOT EXISTS `drone_delivery`;
USE `drone_delivery`;


DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
                           `id` int ,
                           `account_name` varchar(255) DEFAULT NULL,
                           `first_name` varchar(255) DEFAULT NULL,
                           `last_name` varchar(255) DEFAULT NULL,
                           `password` varchar(225) ,
                           `phone_number` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)

    -- UNIQUE KEY (`password`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `manager` VALUES
                          (1,'manager1','Harry','Potter', '1111', '347111'),
                          (2, 'manager2','Hermione','Granger', '2111', '3479222'),
                          (3, 'manager3','Will','Smith', '3333', '234234'),
                          (4, 'manager4','Elon','Musk', '444', '9188042990');


DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
                         `id` int AUTO_INCREMENT,
                         `name` varchar(225) NOT NULL,
                         `manager_id` int DEFAULT NULL,
                         `incoming_revenue` int DEFAULT NULL,
                         `completed_revenue` int DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `store` VALUES

                        (1,'Walmart',1,0,0),
                        (2,'Amazon',2,0,0),
                        (3,'Kroger',3,0,0),
                        (4,'Costco',4,0,0);

DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(225) NOT NULL,
                        `price` double NOT NULL,
                        `weight` int NOT NULL,
                        `store_id` int DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY (`name`),
                        FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `item` VALUES
                        (1,'Tomato_Walmart',5,1,1),
                        (2,'Tomato_Amazon',4,1,2),
                        (3,'Tomato_Kroger',3,1,3),
                        (4,'Tomato_Costco',1,1,4),
                        (5,'Cheese_Walmart',5,1,1),
                        (6,'Cheese_Amazon',4,1,2),
                        (7,'Cheese_Kroger',3,1,3),
                        (8,'Cheese_Costco',1,1,4);


DROP TABLE IF EXISTS `drone`;
CREATE TABLE `drone` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `available_cap` int NOT NULL,
                         `lift_cap` int NOT NULL,
                         `num_deliveries_left` int NOT NULL,
                         `num_orders` int NOT NULL,
                         `remaining_cap` int NOT NULL,
                         `serial_num` varchar(225) NOT NULL,
                         `store_id` int DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY (`serial_num`),
                         FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `drone`
VALUES
    (1, 100, 100, 10, 0, 100, 'sn1', 1),
    (2, 100, 100, 10, 0, 100, 'sn2', 2),
    (3, 200, 200, 10, 0, 200, 'sn3', 3),
    (4, 200, 200, 10, 0, 200, 'sn4', 4);

DROP TABLE IF EXISTS `pilot`;
CREATE TABLE `pilot` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `account_name` varchar(255) DEFAULT NULL,
                         `first_name` varchar(255) DEFAULT NULL,
                         `last_name` varchar(255) DEFAULT NULL,
                         `password` varchar(225) NOT NULL,
                         `phone_number` varchar(255) DEFAULT NULL,
                         `exp_level` int NOT NULL,
                         `license_id` varchar(255) DEFAULT NULL,
                         `tax_id` varchar(255) DEFAULT NULL,
                         `drone_id` int DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         FOREIGN KEY (`drone_id`) REFERENCES `drone` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `pilot`
VALUES
    (1, 'pilot1', 'Ron', 'Weasley', '111', '111', 0, '111', '111', null),
    (2, 'pilot2', 'George', 'Weasley', '222', '222', 0, '222', '222', null),
    (3, 'pilot3', 'Fred', 'Weasley', '333', '333', 0, '333', '333', null),
    (4, 'pilot4', 'Tom', 'Hanks', '4444', '444', 0, '4444', '444', null);

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
                            `id` int NOT NULL,
                            `account_name` varchar(255) DEFAULT NULL,
                            `first_name` varchar(255) DEFAULT NULL,
                            `last_name` varchar(255) DEFAULT NULL,
                            `password` varchar(225) NOT NULL,
                            `phone_number` varchar(255) DEFAULT NULL,
                            `available_credit` int NOT NULL,
                            `credits` int NOT NULL,
                            `rating` int NOT NULL,
                            PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `customer`
VALUES
    (1, 'customer1', 'Neville', 'Longbottom', '111', '111', 100, 100, 5),
    (2, 'customer2', 'Luna', 'Lovegood', '222', '111', 100, 100, 5),
    (3, 'customer3', 'Ginny', 'Weasley', '333', '111', 100, 100, 5);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `username` varchar(50) NOT NULL,
                         `password` char(68) NOT NULL,
                         `enabled` tinyint(1) NOT NULL,
                         PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `order_number` varchar(225) NOT NULL,
                          `total_cost` int NOT NULL,
                          `total_weight` int NOT NULL,
                          `customer_id` int DEFAULT NULL,
                          `drone_id` int DEFAULT NULL,
                          `store_id` int DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY (`order_number`),
                          FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
                          FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
                          FOREIGN KEY (`drone_id`) REFERENCES `drone` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# INSERT INTO `orders`
# VALUES
#     (1, 'order1', 10, 10, 1, 2, 1),
#     (2, 'order2', 20, 10, 2, 3, 1),
#     (3, 'order3', 30, 10, 3, 4, 2),
#     (4, 'order4', 40, 10, 1, 4, 2),
#     (5, 'order5', 50, 10, 2, 5, 2),
#     (6, 'order6', 60, 10, 3, 5, 2);

DROP TABLE IF EXISTS `line`;
CREATE TABLE `line` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `cost` int NOT NULL,
                        `quantity` int NOT NULL,
                        `weight` int NOT NULL,
                        `item_id` int DEFAULT NULL,
                        `order_id` int DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
                        FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `store_item`;
CREATE TABLE `store_item` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `price` int NOT NULL,
                              `item_id` int DEFAULT NULL,
                              `store_id` int DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
                              FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `store_item` VALUES
                        (1,5,1,1),
                        (2,4,2,2),
                        (3,3,3,3),
                        (4,1,4,4),
                        (5,5,5,1),
                        (6,4,6,2),
                        (7,3,7,3),
                        (8,1,8,4);


DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs`(
    info TEXT)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `users`
VALUES
    ('customer1','{noop}customer1',1),
    ('customer2','{noop}customer2',1),
    ('customer3','{noop}customer3',1),
    ('pilot1','{noop}pilot1',1),
    ('pilot2','{noop}pilot2',1),
    ('pilot3','{noop}pilot3',1),
    ('pilot4','{noop}pilot4',1),
    ('manager1','{noop}manager1',1),
    ('manager2','{noop}manager2',1),
    ('manager3','{noop}manager3',1),
    ('manager4','{noop}manager4',1),
    ('admin','{noop}admin',1);

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
                               `username` varchar(50) NOT NULL,
                               `authority` varchar(50) NOT NULL,
                               UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
                               CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `authorities`
--

INSERT INTO `authorities`
VALUES
    ('customer1','ROLE_CUSTOMER'),
    ('customer2','ROLE_CUSTOMER'),
    ('customer3','ROLE_CUSTOMER'),
    ('pilot1','ROLE_PILOT'),
	('pilot2','ROLE_PILOT'),
	('pilot3','ROLE_PILOT'),
	('pilot4','ROLE_PILOT'),
    ('manager1','ROLE_MANAGER'),
    ('manager2','ROLE_MANAGER'),
    ('manager3','ROLE_MANAGER'),
    ('manager4','ROLE_MANAGER'),
    ('admin','ROLE_ADMIN');
