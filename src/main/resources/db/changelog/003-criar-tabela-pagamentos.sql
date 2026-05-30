--liquibase formatted sql
--changeset gabriel:3

CREATE TABLE pagamentos (
  id BIGSERIAL PRIMARY KEY,
  valor DECIMAL(10, 2) NOT NULL,
  data_pagamento DATE NOT NULL,
  divida_id BIGINT NOT NULL,
  
  CONSTRAINT fk_pagamento_divida FOREIGN KEY (divida_id) REFERENCES dividas(id) ON DELETE CASCADE
);

-- Index para acelerar buscas por dívida
CREATE INDEX idx_pagamento_divida ON pagamentos(divida_id);