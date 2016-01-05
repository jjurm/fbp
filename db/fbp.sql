
CREATE DATABASE IF NOT EXISTS `fbp`;

USE `fbp`;

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
CREATE TABLE IF NOT EXISTS `logs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `thread` varchar(100) NOT NULL,
  `level` varchar(10) NOT NULL,
  `logger` tinytext NOT NULL,
  `message` text,
  `throwable` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
