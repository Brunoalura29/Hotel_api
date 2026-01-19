CREATE TABLE hospede (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(150) NOT NULL,
                         documento VARCHAR(50) NOT NULL UNIQUE,
                         telefone VARCHAR(30) NOT NULL,
                         tem_carro BOOLEAN NOT NULL DEFAULT FALSE,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reserva (
                         id BIGSERIAL PRIMARY KEY,

                         hospede_id BIGINT NOT NULL,

                         data_entrada_prevista TIMESTAMP NOT NULL,
                         data_saida_prevista TIMESTAMP NOT NULL,

                         data_checkin TIMESTAMP,
                         data_checkout TIMESTAMP,

                         status VARCHAR(30) NOT NULL,

                         usa_vaga BOOLEAN NOT NULL DEFAULT FALSE,

                         valor_total NUMERIC(10,2),

                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_reserva_hospede
                             FOREIGN KEY (hospede_id)
                                 REFERENCES hospede(id)
);

CREATE INDEX idx_hospede_nome ON hospede(nome);
CREATE INDEX idx_hospede_documento ON hospede(documento);
CREATE INDEX idx_hospede_telefone ON hospede(telefone);

CREATE INDEX idx_reserva_status ON reserva(status);
CREATE INDEX idx_reserva_hospede ON reserva(hospede_id);

ALTER TABLE reserva
    ADD CONSTRAINT chk_reserva_status
        CHECK (status IN ('RESERVADO', 'CHECKED_IN', 'CHECKED_OUT'));