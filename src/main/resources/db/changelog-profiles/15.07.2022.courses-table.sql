--liquibase formatted sql

--changeset gkrasovskiy:15.07.2022.courses-table.sql

CREATE SEQUENCE IF NOT EXISTS course_id_pk_seq;
CREATE TABLE IF NOT EXISTS course
(
    id   INT DEFAULT nextval('course_id_pk_seq')
        CONSTRAINT pk_course_id PRIMARY KEY,
    uuid UUID         NOT NULL,
    name VARCHAR(250) NOT NULL,
    recipe_id INT,
    CONSTRAINT fk_course_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
);

CREATE TABLE IF NOT EXISTS course_ingredient
(
    course_id     INT,
    CONSTRAINT fk_course_ingredient_course
        FOREIGN KEY (course_id) REFERENCES course (id),
    ingredient_id INT,
    CONSTRAINT fk_course_ingredient_ingredient
        FOREIGN KEY (ingredient_id) REFERENCES ingredient (id)
)