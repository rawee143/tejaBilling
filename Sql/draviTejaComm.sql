-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 13, 2018 at 12:14 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `draviTejaComm`
--

-- --------------------------------------------------------

--
-- Table structure for table `Login_tbl`
--

CREATE TABLE IF NOT EXISTS `Login_tbl` (
  `UserId` varchar(15) NOT NULL DEFAULT '',
  `UserName` varchar(20) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `masterPassword` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Login_tbl`
--

INSERT INTO `Login_tbl` (`UserId`, `UserName`, `Password`, `masterPassword`) VALUES
('', '', 'da39a3ee5e6b4b0d3255bfef95601890afd80709', '39dfa55283318d31afe5a3ff4a0e3253e2045e43'),
('rawee142', 'Ranjan Mallik', '6fd599b68b363c01a1296645da4305d3313ebb3f', '39dfa55283318d31afe5a3ff4a0e3253e2045e43'),
('rawee143', 'Ravi Ranjan', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', '39dfa55283318d31afe5a3ff4a0e3253e2045e43'),
('shailenyc', 'Shailendra Kumar', '229be39e04f960e46d8a64cadc8b4534e6bfc364', '39dfa55283318d31afe5a3ff4a0e3253e2045e43');

-- --------------------------------------------------------

--
-- Table structure for table `productBills`
--

CREATE TABLE IF NOT EXISTS `productBills` (
`BillNo` int(11) NOT NULL,
  `date` date NOT NULL,
  `custName` varchar(30) DEFAULT NULL,
  `address` varchar(40) DEFAULT NULL,
  `contact` varchar(10) DEFAULT NULL,
  `totalDue` double DEFAULT '0',
  `paid` double DEFAULT '0',
  `due` double DEFAULT '0',
  `cashier_name` varchar(25) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `productBills`
--

INSERT INTO `productBills` (`BillNo`, `date`, `custName`, `address`, `contact`, `totalDue`, `paid`, `due`, `cashier_name`) VALUES
(1, '2018-04-13', 'Ravi', 'janakpur', '9856332471', 102.5, 102.5, 0, 'Ravi Ranjan');

-- --------------------------------------------------------

--
-- Table structure for table `product_sales`
--

CREATE TABLE IF NOT EXISTS `product_sales` (
`id` int(11) NOT NULL,
  `BillNo` int(11) NOT NULL,
  `proCode` int(11) NOT NULL,
  `proName` varchar(150) NOT NULL,
  `qty` int(5) NOT NULL,
  `rate` double NOT NULL,
  `amount` double NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `product_sales`
--

INSERT INTO `product_sales` (`id`, `BillNo`, `proCode`, `proName`, `qty`, `rate`, `amount`) VALUES
(1, 1, 6, 'ZEBRONICS BLUETHOOTH SPEAKER', 1, 102.5, 102.5);

-- --------------------------------------------------------

--
-- Table structure for table `product_stock`
--

CREATE TABLE IF NOT EXISTS `product_stock` (
`proCode` int(11) NOT NULL,
  `proName` varchar(150) NOT NULL,
  `VendName` varchar(30) DEFAULT NULL,
  `quan` int(11) NOT NULL,
  `cost` double DEFAULT NULL,
  `mrp` double DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `product_stock`
--

INSERT INTO `product_stock` (`proCode`, `proName`, `VendName`, `quan`, `cost`, `mrp`) VALUES
(3, 'MooserBaer PenDrive 32 GB', 'Gayatri Electronics', 112, 420.45, 530),
(4, 'Redmi Note 3 Mobile', '', 55, 9999, 10000),
(5, 'Usb Charger', '', 120, 25, 52),
(6, 'ZEBRONICS BLUETHOOTH SPEAKER', '', 495, 102.5, 140),
(7, 'Nokia 2233 LPG MODEL', 'AASHISH ELECTRONICS.', 48, 8950, 9000),
(8, 'JBL BLUETOOTH SPEAKER', '', 40, 5422, 6500),
(9, 'Casio Scintefic calculator', '', 50, 125.4, 170),
(10, 'INTEX 4.1 SPEAKER', 'ANIL ELEC.', 22, 1120.25, 1200.75);

-- --------------------------------------------------------

--
-- Table structure for table `quotation_detail`
--

CREATE TABLE IF NOT EXISTS `quotation_detail` (
`id` int(11) NOT NULL,
  `quotationNo` int(11) NOT NULL,
  `proCode` int(11) NOT NULL,
  `proName` varchar(50) NOT NULL,
  `qty` int(11) NOT NULL,
  `rate` double NOT NULL,
  `amount` double NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `quotation_detail`
--

INSERT INTO `quotation_detail` (`id`, `quotationNo`, `proCode`, `proName`, `qty`, `rate`, `amount`) VALUES
(1, 1, 4, 'Redmi Note 3 Mobile', 8, 9999, 79992);

-- --------------------------------------------------------

--
-- Table structure for table `quotation_tbl`
--

CREATE TABLE IF NOT EXISTS `quotation_tbl` (
`quotationNo` int(11) NOT NULL,
  `date` date NOT NULL,
  `custName` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `contact` varchar(10) NOT NULL,
  `subTotal` double NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `quotation_tbl`
--

INSERT INTO `quotation_tbl` (`quotationNo`, `date`, `custName`, `address`, `contact`, `subTotal`) VALUES
(1, '2018-04-13', 'Ravi Ranjan', 'Janakpur', '9987455622', 79992);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Login_tbl`
--
ALTER TABLE `Login_tbl`
 ADD PRIMARY KEY (`UserId`);

--
-- Indexes for table `productBills`
--
ALTER TABLE `productBills`
 ADD PRIMARY KEY (`BillNo`);

--
-- Indexes for table `product_sales`
--
ALTER TABLE `product_sales`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product_stock`
--
ALTER TABLE `product_stock`
 ADD PRIMARY KEY (`proCode`);

--
-- Indexes for table `quotation_detail`
--
ALTER TABLE `quotation_detail`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quotation_tbl`
--
ALTER TABLE `quotation_tbl`
 ADD PRIMARY KEY (`quotationNo`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `productBills`
--
ALTER TABLE `productBills`
MODIFY `BillNo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `product_sales`
--
ALTER TABLE `product_sales`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `product_stock`
--
ALTER TABLE `product_stock`
MODIFY `proCode` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `quotation_detail`
--
ALTER TABLE `quotation_detail`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `quotation_tbl`
--
ALTER TABLE `quotation_tbl`
MODIFY `quotationNo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
