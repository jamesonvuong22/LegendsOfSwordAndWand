-- Legends of Sword and Wand
-- Deliverable 1 Database Schema
-- MySQL Compatible

CREATE DATABASE IF NOT EXISTS lowsw;
USE lowsw;

-- =========================
-- USERS
-- =========================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- PARTIES
-- =========================
CREATE TABLE IF NOT EXISTS parties (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    gold INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =========================
-- HEROES
-- =========================
CREATE TABLE IF NOT EXISTS heroes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    party_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    class_type VARCHAR(50) NOT NULL,
    level INT DEFAULT 1,
    hp INT NOT NULL,
    mana INT NOT NULL,
    attack_power INT NOT NULL,
    defense_power INT NOT NULL,
    experience INT DEFAULT 0,
    FOREIGN KEY (party_id) REFERENCES parties(id) ON DELETE CASCADE
);

-- =========================
-- CAMPAIGNS (PvE Progress)
-- =========================
CREATE TABLE IF NOT EXISTS campaigns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    party_id BIGINT NOT NULL,
    current_room INT DEFAULT 1,
    last_room_type VARCHAR(50),
    score INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (party_id) REFERENCES parties(id) ON DELETE CASCADE
);

-- =========================
-- PvP INVITATIONS
-- =========================
CREATE TABLE IF NOT EXISTS pvp_invites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    challenger_id BIGINT NOT NULL,
    opponent_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (challenger_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (opponent_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =========================
-- PvP MATCHES
-- =========================
CREATE TABLE IF NOT EXISTS pvp_matches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invite_id BIGINT NOT NULL,
    winner_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (invite_id) REFERENCES pvp_invites(id) ON DELETE CASCADE,
    FOREIGN KEY (winner_id) REFERENCES users(id) ON DELETE SET NULL
);
