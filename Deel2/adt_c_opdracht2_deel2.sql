-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 27 nov. 2025 à 16:33
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `adt_c_opdracht2_deel2`
--

-- --------------------------------------------------------

--
-- Structure de la table `contract_analyses`
--

CREATE TABLE `contract_analyses` (
  `id` bigint(20) NOT NULL,
  `request_id` bigint(20) DEFAULT NULL,
  `contract_text` longtext NOT NULL,
  `question` text DEFAULT NULL,
  `summary` text NOT NULL,
  `risks` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`risks`)),
  `recommendation` text NOT NULL,
  `analyzed_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `ai_model` varchar(100) DEFAULT 'gpt-4'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `requests`
--

CREATE TABLE `requests` (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `status` enum('PENDING','IN_PROGRESS','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PENDING',
  `client_email` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL
) ;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `contract_analyses`
--
ALTER TABLE `contract_analyses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_analyses_request_id` (`request_id`),
  ADD KEY `idx_analyses_analyzed_at` (`analyzed_at`);

--
-- Index pour la table `requests`
--
ALTER TABLE `requests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_requests_status` (`status`),
  ADD KEY `idx_requests_client_email` (`client_email`),
  ADD KEY `idx_requests_created_at` (`created_at`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `contract_analyses`
--
ALTER TABLE `contract_analyses`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `requests`
--
ALTER TABLE `requests`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `contract_analyses`
--
ALTER TABLE `contract_analyses`
  ADD CONSTRAINT `fk_request` FOREIGN KEY (`request_id`) REFERENCES `requests` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
