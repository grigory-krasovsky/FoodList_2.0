--liquibase formatted sql

--changeset gkrasovskiy:15.07.2022.recipes-table.sql

CREATE SEQUENCE IF NOT EXISTS recipe_id_pk_seq;
CREATE TABLE IF NOT EXISTS recipe
(
    id          INT DEFAULT nextval('recipe_id_pk_seq')
        CONSTRAINT pk_recipe_id PRIMARY KEY,
    uuid        UUID          NOT NULL,
    recipe_text VARCHAR(5000) NOT NULL
);