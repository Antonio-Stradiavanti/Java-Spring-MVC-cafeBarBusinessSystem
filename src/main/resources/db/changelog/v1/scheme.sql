--liquibase formatted sql

--changeset senioravanti:update_schema
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

--changeset senioravanti:create_products
--comment В таблицу databasechangelog liquibase записывает контрольную сумму исполненного файла миграции.Контрольные суммы используются чтобы нельзя было менять уже исполненные скрипты миграции. Не рекомендуется вносить правки в скрипты миграции. Для того чтобы выполнить измененный файл миграции нужно удалить соотв. запись в таблице databasechangelog.
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

--changeset senioravanti:create_users
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,

    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,

    lastname VARCHAR(64),
    firstname VARCHAR(32),  
    patronym VARCHAR(36),

    phone_number VARCHAR(16) NOT NULL UNIQUE,  
    birthday DATE,

    role VARCHAR(16) NOT NULL
);

--changeset senioravanti:create_clients
CREATE TABLE IF NOT EXISTS clients (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT UNIQUE,

    name VARCHAR(64),
    birthday DATE,

    email VARCHAR(100) UNIQUE,

    registration_state VARCHAR(16)
);

--changeset senioravanti:create_bonus_cards
CREATE TABLE IF NOT EXISTS bonus_cards (
);