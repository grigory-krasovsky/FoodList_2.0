--liquibase formatted sql

--changeset krasovskiy:13.07.2022.add-user.sql
CREATE SEQUENCE user_id_pk_seq;
CREATE TABLE "user"
(
    id        INT DEFAULT nextval('user_id_pk_seq')
        CONSTRAINT pk_user_id PRIMARY KEY,
    user_name VARCHAR(250) NOT NULL,
    password  VARCHAR(250) NOT NULL,
    active    BOOLEAN,
    roles     VARCHAR(250) NOT NULL
);