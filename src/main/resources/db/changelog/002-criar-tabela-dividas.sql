--liquibase formatted sql
--changeset gabriel:2

CREATE TABLE dividas (
  id BIGSERIAL PRIMARY KEY,
  descricao VARCHAR(255) NOT NULL,
  valor DECIMAL(10, 2) NOT NULL,
  data_divida DATE NOT NULL,
  observacao TEXT,
  devedor_id BIGINT NOT NULL,
  CONSTRAINT fk_divida_devedor FOREIGN KEY (devedor_id) REFERENCES devedores(id) ON DELETE CASCADE
);

-- Index para acelerar buscas por devedor
CREATE INDEX idx_divida_devedor ON dividas(devedor_id);