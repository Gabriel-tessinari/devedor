--liquibase formatted sql
--changeset gabriel:1

CREATE TABLE devedores (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  CONSTRAINT uk_devedor_nome UNIQUE (nome)
);