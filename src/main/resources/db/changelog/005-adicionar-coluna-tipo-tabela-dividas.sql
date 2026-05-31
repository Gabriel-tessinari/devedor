--liquibase formatted sql
--changeset gabriel:5

ALTER TABLE dividas ADD COLUMN tipo VARCHAR(10) DEFAULT 'RECEBER' NOT NULL;