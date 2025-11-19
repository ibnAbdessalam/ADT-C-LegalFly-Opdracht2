-- phpMyAdmin SQL Dump
-- version 3.3.3
-- http://www.phpmyadmin.net
--
-- Machine: localhost
-- Genereertijd: 23 Feb 2013 om 20:40
-- Serverversie: 5.1.47
-- PHP-Versie: 5.3.15

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `brainstorm13`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `bijdragen`
--

CREATE TABLE IF NOT EXISTS `bijdragen` (
  `type` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rangId` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tekst` varchar(255) DEFAULT NULL,
  `deelnemer_id` int(11) DEFAULT NULL,
  `onderwerp_id` int(11) NOT NULL,
  `reactie_op_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF5FB2BA03E5634C` (`reactie_op_id`),
  KEY `FKF5FB2BA08484AF31` (`onderwerp_id`),
  KEY `FKF5FB2BA0E5F8C211` (`deelnemer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Gegevens worden uitgevoerd voor tabel `bijdragen`
--


-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `onderwerpen`
--

CREATE TABLE IF NOT EXISTS `onderwerpen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `titel` varchar(255) DEFAULT NULL,
  `facilitator_id` int(11) DEFAULT NULL,
  `sessie_id` int(11) DEFAULT NULL,
  `vorig_onderwerp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCCA60B87CA3916B9` (`vorig_onderwerp_id`),
  KEY `FKCCA60B878CAB0AB1` (`facilitator_id`),
  KEY `FKCCA60B87E431D823` (`sessie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Gegevens worden uitgevoerd voor tabel `onderwerpen`
--


-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `personen`
--

CREATE TABLE IF NOT EXISTS `personen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emailadres` varchar(255) DEFAULT NULL,
  `familienaam` varchar(255) DEFAULT NULL,
  `paswoord` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `voornaam` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IPersoon_naam` (`voornaam`),
  KEY `IPersoon_email` (`emailadres`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=42 ;

--
-- Gegevens worden uitgevoerd voor tabel `personen`
--

INSERT INTO `personen` (`id`, `emailadres`, `familienaam`, `paswoord`, `status`, `voornaam`) VALUES
(1, 'bram.vandenbogaerde@gmail.com', 'Vandenbogaerde', 'test', 'aktief', 'Bram'),
(4, 'koen.christiaens@hubrussel.be', 'Christiaens', 'HoofdletterH', 'aktief', 'Koen'),
(5, 'sia@gmail.com', 'Furler', 'Titanium', 'aktief', 'Sia Kate Isobelle'),
(8, 'sven@jazz.be', 'Libert', 'reggae-jazz', 'aktief', 'Jeremy'),
(10, 'dries@drupal.org', 'Luystermans', 'zeeringewikkeld', 'aktief', 'Dries'),
(11, 'adrian@bxl.net', 'Gonczar', 'moe', 'aktief', 'Adrian'),
(12, 'bert@hub.bee', 'De Mol', 'finland', 'aktief', 'Bert'),
(14, 'hans.vandenbogaerde@gmail.com', 'Vandenbogaerde', 'geheim', 'aktief', 'Hans'),
(15, 'elisa@brugge.be', 'Waut', 'helewaut', 'aktief', 'Elisa'),
(16, 'isolde@music.be', 'Lasoen', 'jazzke', 'aktief', 'Isolde'),
(17, 'roger.waters@pinkfloyd.co.uk', 'Waters', 'meddle', 'aktief', 'Roger'),
(18, 'lady.lynn@jazzland.be', 'lynn', 'ladyisavamp', 'aktief', 'lady'),
(25, 'roos.vanacker@gmail.com', 'Vanacker', 'geheim', 'actief', 'Roos'),
(28, 'roger.waters@pinkfloyd.co.uk', 'Waters', 'meddle', 'aktief', 'Roger'),
(29, 'roger.waters@pinkfloyd.co.uk', 'Waters', 'meddle', 'aktief', 'Roger'),
(30, 'jetthro.tull@ancienttimes.com', 'Tull', 'aqualung', 'aktief', 'Jetthro'),
(35, 'ilka.winnen@student.hubrussel.be', 'Winnen', 'supergeheim', 'aktief', 'Ilka'),
(36, 'semira.tahriou@student.hubrussel.be', 'Tahriou', 'supergeheim', 'aktief', 'Semira'),
(37, 'nataly@dawn.under', 'Dawn', 'grapje', 'aktief', 'Nataly'),
(38, 'kate@perry.com', 'Perry', 'herekate', 'aktief', 'Kate '),
(39, 'rocknroll@germany.de', 'Hagen', 'operette', 'aktief', 'Nina'),
(40, 'adriaan@komiek.be', 'Van den Hoof', 'basically', 'aktief', 'Adriaan'),
(41, 'servaes@nys.be', 'Nys', 'geheim', 'aktief', 'Servaes');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `rollen`
--

CREATE TABLE IF NOT EXISTS `rollen` (
  `type` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `usernaam` varchar(255) DEFAULT NULL,
  `sessie_id` int(11) DEFAULT NULL,
  `persoon_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usernaam` (`usernaam`),
  KEY `IRol_usernaam` (`usernaam`),
  KEY `FKC8D761E6E431D823` (`sessie_id`),
  KEY `FKC8D761E6C95BA6B1` (`persoon_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Gegevens worden uitgevoerd voor tabel `rollen`
--

INSERT INTO `rollen` (`type`, `id`, `status`, `usernaam`, `sessie_id`, `persoon_id`) VALUES
('Administrator', 1, 'aktief', 'bramadmin', NULL, 1),
('Organisator', 2, 'aktief', 'hansOrganisator', 1, 14),
('Administrator', 3, 'aktief', 'hansAdministrator', NULL, 14),
('Deelnemer', 4, 'aktief', 'bertDeelnemer', 1, 12),
('Facilitator', 5, 'aktief', 'hansFacilitator', 1, 14),
('Deelnemer', 6, 'actief', 'RoosDeelnemer', 1, 25);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `sessies`
--

CREATE TABLE IF NOT EXISTS `sessies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `begin_tijdstip` datetime DEFAULT NULL,
  `eind_tijdstip` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `titel` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Gegevens worden uitgevoerd voor tabel `sessies`
--

INSERT INTO `sessies` (`id`, `begin_tijdstip`, `eind_tijdstip`, `status`, `titel`) VALUES
(1, '2012-04-15 18:21:23', '2012-04-29 18:21:36', 'aktief', 'een eerste brainstormsessie');

--
-- Beperkingen voor gedumpte tabellen
--

--
-- Beperkingen voor tabel `bijdragen`
--
ALTER TABLE `bijdragen`
  ADD CONSTRAINT `FKF5FB2BA03E5634C` FOREIGN KEY (`reactie_op_id`) REFERENCES `bijdragen` (`id`),
  ADD CONSTRAINT `FKF5FB2BA08484AF31` FOREIGN KEY (`onderwerp_id`) REFERENCES `onderwerpen` (`id`),
  ADD CONSTRAINT `FKF5FB2BA0E5F8C211` FOREIGN KEY (`deelnemer_id`) REFERENCES `rollen` (`id`);

--
-- Beperkingen voor tabel `onderwerpen`
--
ALTER TABLE `onderwerpen`
  ADD CONSTRAINT `FKCCA60B878CAB0AB1` FOREIGN KEY (`facilitator_id`) REFERENCES `rollen` (`id`),
  ADD CONSTRAINT `FKCCA60B87CA3916B9` FOREIGN KEY (`vorig_onderwerp_id`) REFERENCES `onderwerpen` (`id`),
  ADD CONSTRAINT `FKCCA60B87E431D823` FOREIGN KEY (`sessie_id`) REFERENCES `sessies` (`id`);

--
-- Beperkingen voor tabel `rollen`
--
ALTER TABLE `rollen`
  ADD CONSTRAINT `FKC8D761E6C95BA6B1` FOREIGN KEY (`persoon_id`) REFERENCES `personen` (`id`),
  ADD CONSTRAINT `FKC8D761E6E431D823` FOREIGN KEY (`sessie_id`) REFERENCES `sessies` (`id`);
