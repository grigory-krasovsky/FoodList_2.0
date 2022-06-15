--liquibase formatted sql

--changeset gkrasovskiy:13.07.2022.ingredients-table.sql

CREATE SEQUENCE IF NOT EXISTS ingredient_id_pk_seq ;
CREATE TABLE IF NOT EXISTS ingredient
(
    id INT DEFAULT nextval('ingredient_id_pk_seq')
        CONSTRAINT pk_ingredient_id PRIMARY KEY,
    uuid UUID NOT NULL ,
    name VARCHAR (250) NOT NULL
);