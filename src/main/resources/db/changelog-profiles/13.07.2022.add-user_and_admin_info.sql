--liquibase formatted sql

--changeset krasovskiy:13.07.2022.add-user_and_admin_info.sql
INSERT INTO "user" VALUES (1, 'test_user', 'pass', true, 'ROLE_USER'),
                        (2, 'admin', 'pass', true, 'ROLE_ADMIN');