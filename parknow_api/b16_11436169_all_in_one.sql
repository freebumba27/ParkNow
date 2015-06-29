-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Host: sql311.byethost16.com
-- Generation Time: Jun 29, 2015 at 06:47 AM
-- Server version: 5.6.22-71.0
-- PHP Version: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `b16_11436169_all_in_one`
--

-- --------------------------------------------------------

--
-- Table structure for table `gcm_users`
--

CREATE TABLE IF NOT EXISTS `gcm_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gcm_regid` text,
  `name` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `gcm_users`
--

INSERT INTO `gcm_users` (`id`, `gcm_regid`, `name`, `email`, `created_at`) VALUES
(20, 'APA91bG5V-XGPvW7nDDQcWZ_2kBL4XEHN40kbiru77l9QF4dVAjrwcLdmFyFIdC2KZNNyfYf_TF37mnxigmCtzroUWkAV0SsXVXD2DGYZFmGzZSIpFn7O5EuxUJFf8ZF1KoTadqxuQaxk8wJgAa1_OTx88VCvbJ_WA', 'Anirban', 'anirban.jana@gmail.com', '2014-11-04 08:47:02');

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE IF NOT EXISTS `order_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parking_spot_id` int(11) NOT NULL,
  `purchaser_user_id` int(11) NOT NULL,
  `paypal_payment_id` text NOT NULL,
  `spot_booking_from` text NOT NULL,
  `spot_booking_upto` text NOT NULL,
  `price` int(11) NOT NULL,
  `purchased_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`id`, `parking_spot_id`, `purchaser_user_id`, `paypal_payment_id`, `spot_booking_from`, `spot_booking_upto`, `price`, `purchased_date_time`) VALUES
(1, 4, 14, 'PAY-9VB85586807034248KU6JJLI', '26-04-2015  12:57', '26-04-2015  13:00', 6, '2015-04-26 09:48:18'),
(2, 2, 12, 'PAY-8EU54471S8556322MKU6JLDQ', '26-04-2015  13:06', '26-04-2015  13:09', 15, '2015-04-26 14:47:23'),
(3, 2, 12, 'PAY-8LS437881N673152WKU6JN7A', '26-04-2015  13:11', '26-04-2015  13:13', 6, '2015-04-26 07:40:01'),
(4, 2, 12, 'PAY-3D82392422231574NKU6JOOA', '26-04-2015  13:20', '26-04-2015  15:30', 421, '2015-04-26 07:41:00'),
(5, 3, 12, 'PAY-05S748140N503745UKU6QJPI', '26-04-2015  20:59', '26-04-2015  21:04', 20, '2015-04-26 15:28:17'),
(6, 2, 1, 'PAY-4X509840V0546393JKU7R2KI', '28-04-2015  11:08', '28-04-2015  12:00', 168, '2015-04-28 05:37:00'),
(7, 2, 13, 'PAY-9N0298283P1016351KU7SBDI', '28-04-2015  11:30', '28-04-2015  16:20', 940, '2015-04-28 05:51:28'),
(8, 2, 15, 'PAY-8NS5998009479180CKU7SFTI', '28-04-2015  13:30', '28-04-2015  14:00', 97, '2015-04-28 06:01:04'),
(9, 2, 12, 'PAY-1A226505119247618KU7V2MY', '28-04-2015  15:50', '28-04-2015  16:40', 162, '2015-04-28 10:10:15'),
(10, 8, 12, 'PAY-0NB817690A272715LKU7YJOA', '28-04-2015  18:30', '28-04-2015  19:30', 120, '2015-04-28 12:58:51');

-- --------------------------------------------------------

--
-- Table structure for table `panic_alert_user_alert_details`
--

CREATE TABLE IF NOT EXISTS `panic_alert_user_alert_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `mobile_no` text NOT NULL,
  `date_time` text NOT NULL,
  `lat` text NOT NULL,
  `lng` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `panic_alert_user_alert_details`
--

INSERT INTO `panic_alert_user_alert_details` (`id`, `name`, `mobile_no`, `date_time`, `lat`, `lng`) VALUES
(3, 'Bumba Jana', '8444665326', '17:27:08 - 15.05.2015', '8.5605026', '76.8804598'),
(4, 'Bumba Jana', '8444665326', '17:31:35 - 15.05.2015', '8.5605236', '76.8805272'),
(5, 'Bumba Jana', '8444665326', '17:31:48 - 15.05.2015', '8.5605079', '76.8804379'),
(6, 'Bumba Jana', '8444665326', '17:32:05 - 15.05.2015', '8.5605083', '76.8804565'),
(7, 'Bumba Jana', '8444665326', '17:32:15 - 15.05.2015', '8.5605021', '76.880469'),
(8, 'Bumba Jana', '8444665326', '17:32:26 - 15.05.2015', '8.5605205', '76.8805361'),
(9, 'Bumba Jana', '8444665326', '17:32:41 - 15.05.2015', '8.5605365', '76.8804861'),
(10, 'Bumba Jana', '8444665326', '17:48:41 - 15.05.2015', '8.5605161', '76.8804953'),
(11, 'Bumba Jana', '8444665326', '17:49:01 - 15.05.2015', '8.5605233', '76.8805076'),
(12, 'Bumba Jana', '8444665326', '17:53:31 - 15.05.2015', '8.5605314', '76.8805583'),
(13, 'Bumba Jana', '8444665326', '17:53:56 - 15.05.2015', '8.5605368', '76.880587'),
(14, 'aaa', '92366', '19:23:28 - 15.05.2015', '8.5604238', '76.8803639');

-- --------------------------------------------------------

--
-- Table structure for table `panic_alert_user_details`
--

CREATE TABLE IF NOT EXISTS `panic_alert_user_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `email_id` text NOT NULL,
  `mobile_no` text NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `address` text NOT NULL,
  `id_card_no` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `panic_alert_user_details`
--

INSERT INTO `panic_alert_user_details` (`id`, `name`, `email_id`, `mobile_no`, `username`, `password`, `date_created`, `address`, `id_card_no`) VALUES
(2, 'aaa', 'a@gmail.com', '92366', 'a', 'a', '2015-05-15 07:12:46', 'abv,ggh', 'uii4565'),
(3, 'izzat', 'syg.sahabt26@gmail.com', '0145163421', 'izzat', 'izzat', '2015-05-15 17:11:24', 'parit raja', '930326115459');

-- --------------------------------------------------------

--
-- Table structure for table `parking_location_details`
--

CREATE TABLE IF NOT EXISTS `parking_location_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` text NOT NULL,
  `address` text NOT NULL,
  `spaceDesc` text NOT NULL,
  `price` text NOT NULL,
  `lat` text NOT NULL,
  `lng` text NOT NULL,
  `insertion_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `parking_location_details`
--

INSERT INTO `parking_location_details` (`id`, `user_id`, `address`, `spaceDesc`, `price`, `lat`, `lng`, `insertion_time`) VALUES
(1, '12', '63 G.B. Road, Flat no2, Dumdum, Kolkata', 'Enough space to park one car and one bike.', '3', '8.568511320833089', '76.85418047010899', '2015-04-21 03:23:24'),
(2, '12', '2A/3, Bispur, Hasnabad', 'Good & clear space', '3.24', '8.571524289100001', '76.85671247541904', '2015-04-26 07:35:39'),
(3, '16', '3A Asis nagar, kolkata', 'Clear and clean area', '4', '22.53985913634094', '88.35488218814135', '2015-04-21 15:26:42'),
(6, '1', '23/1 Karyavattom, Kerala ', 'Good environment, clean area', '3', '8.565502970204697', '76.89351137727499', '2015-04-28 05:33:58'),
(7, '12', 'Type your parking address', 'Good area lots of space easily you can park a can and a bike. ', '2', '8.564976', '76.883015', '2015-04-28 10:12:31'),
(8, '12', 'Put your parking address', 'Clean parking area enough for 1 car. ', '2', '8.567012', '76.8907', '2015-04-28 12:57:31'),
(9, '18', '31 rue du cotentin, paris', 'tu rentre tu sonne', '25', '48.83841472047963', '2.3122522607445717', '2015-05-08 08:52:31');

-- --------------------------------------------------------

--
-- Table structure for table `user_details`
--

CREATE TABLE IF NOT EXISTS `user_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `email_id` text NOT NULL,
  `mobile_no` text NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `confirm_password` text NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `user_details`
--

INSERT INTO `user_details` (`id`, `name`, `email_id`, `mobile_no`, `username`, `password`, `confirm_password`, `date_created`) VALUES
(1, 'anirban', 'aj@gmail.com', '263558', 'anirban', 'anirban', '', '2015-04-28 09:59:31'),
(14, 'Anirban Jana', 'ani@gmail.com', '9236594536', 'bumba', 'abc', '', '2015-04-21 12:21:42'),
(12, 'Ann', 'ann', '4', 'Ann', 'ann', '', '2015-04-28 10:00:10'),
(13, 'Anirban Jana', 'ani@gmail.com', '9236594536', 'bumba27', 'abc', '', '2015-04-21 12:16:14'),
(15, 'Anirban Jana', 'anirban@gmail.com', '9236261236', 'bumba123', '123', '', '2015-04-21 12:33:41'),
(16, 'Anirban Jana', 'anirban@gmail.com', '9239265636', 'bumba12', '123', '', '2015-04-21 15:23:49'),
(17, 'ma couillasse', 'ogb75@hotmail.com', '09188744444', 'park', '123456', '', '2015-04-28 19:22:05'),
(18, 'adn', 'ogb75@hotmail.com', '09188744444', 'adn', '123456', '', '2015-05-08 08:50:22');

-- --------------------------------------------------------

--
-- Table structure for table `user_review`
--

CREATE TABLE IF NOT EXISTS `user_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parking_spot_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `review` text NOT NULL,
  `review_star` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `user_review`
--

INSERT INTO `user_review` (`id`, `parking_spot_id`, `user_id`, `review`, `review_star`) VALUES
(5, 2, 12, 'Really nice parking spot as per the description I really liked it I will love to park my car again', '5.0'),
(6, 3, 12, 'Parking spot was good but the road is little bit narrow', '3.0'),
(7, 2, 1, 'Good place as per owner description. but little expensive.', '2.5'),
(8, 2, 13, 'Good place but entrance road is narrow be aware of that.', '3.0'),
(9, 2, 15, 'I liked the place.  I will use again.', '5.0'),
(10, 8, 12, 'Really good parking space as per the description', '5.0');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
