-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 09, 2023 at 04:08 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `arabicpoetryencyclopedia`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `book_id` int(40) NOT NULL,
  `serial_no` int(40) NOT NULL,
  `book_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `author_death_year` int(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `poem`
--

CREATE TABLE `poem` (
  `poem_id` int(10) NOT NULL,
  `poem_title` varchar(9999) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `root`
--

CREATE TABLE `root` (
  `root_id` int(10) NOT NULL,
  `root_text` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `root_verse`
--

CREATE TABLE `root_verse` (
  `id` int(40) NOT NULL,
  `root_id` int(40) NOT NULL,
  `verse_id` int(40) NOT NULL,
  `status` varchar(40) NOT NULL DEFAULT 'automatic'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `id` int(40) NOT NULL,
  `word` varchar(40) NOT NULL,
  `tag` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `token_verse`
--

CREATE TABLE `token_verse` (
  `id` int(40) NOT NULL,
  `token_id` int(40) NOT NULL,
  `verse_id` int(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `verse`
--

CREATE TABLE `verse` (
  `verse_id` int(40) NOT NULL,
  `misrah_first` varchar(100) NOT NULL,
  `misrah_second` varchar(100) CHARACTER SET utf32 COLLATE utf32_general_ci NOT NULL,
  `poem_id` int(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_id`),
  ADD UNIQUE KEY `serial_no` (`serial_no`),
  ADD UNIQUE KEY `serial_no_2` (`serial_no`);

--
-- Indexes for table `poem`
--
ALTER TABLE `poem`
  ADD PRIMARY KEY (`poem_id`),
  ADD UNIQUE KEY `poem_title` (`poem_title`) USING HASH;

--
-- Indexes for table `root`
--
ALTER TABLE `root`
  ADD PRIMARY KEY (`root_id`),
  ADD UNIQUE KEY `root_text` (`root_text`) USING HASH;

--
-- Indexes for table `root_verse`
--
ALTER TABLE `root_verse`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `word` (`word`);

--
-- Indexes for table `token_verse`
--
ALTER TABLE `token_verse`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `verse`
--
ALTER TABLE `verse`
  ADD PRIMARY KEY (`verse_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `book_id` int(40) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `poem`
--
ALTER TABLE `poem`
  MODIFY `poem_id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `root`
--
ALTER TABLE `root`
  MODIFY `root_id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `root_verse`
--
ALTER TABLE `root_verse`
  MODIFY `id` int(40) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `token`
--
ALTER TABLE `token`
  MODIFY `id` int(40) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `token_verse`
--
ALTER TABLE `token_verse`
  MODIFY `id` int(40) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `verse`
--
ALTER TABLE `verse`
  MODIFY `verse_id` int(40) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
