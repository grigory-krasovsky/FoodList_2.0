--liquibase formatted sql

--changeset gkrasovskiy:15.07.2022.recipes-table.sql

ALTER TABLE ingredient ADD COLUMN updated_at timestamp;
UPDATE ingredient SET updated_at = now() WHERE updated_at IS NULL;