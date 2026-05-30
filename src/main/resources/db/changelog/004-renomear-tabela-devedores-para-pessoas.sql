--liquibase formatted sql
--changeset gabriel:4

ALTER TABLE devedores RENAME TO pessoas;

ALTER TABLE dividas RENAME COLUMN devedor_id TO pessoa_id;

ALTER TABLE dividas RENAME CONSTRAINT fk_divida_devedor TO fk_divida_pessoa;

ALTER INDEX idx_divida_devedor RENAME TO idx_divida_pessoa;