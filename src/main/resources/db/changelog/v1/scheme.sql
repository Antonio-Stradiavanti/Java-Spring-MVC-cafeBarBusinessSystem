-- Создаст таблицу продукт если такая не существует, удалит если существует
-- В таблицу databasechangelog liquibase записывает контрольную сумму исполненного файла миграции
-- Контрольные суммы используются чтобы нельзя было менять уже исполненные скрипты миграции
-- Не рекомендуется вносить правки в скрипты миграции
-- Для того чтобы выполнить измененный файл миграции нужно удалить соотв. запись в таблице databasechangelog
DROP TABLE IF EXISTS products;

CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

