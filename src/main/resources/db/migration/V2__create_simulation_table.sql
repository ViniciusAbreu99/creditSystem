CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS simulation (
    id BIGSERIAL PRIMARY KEY,
    simulation_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    requested_amount NUMERIC(19,2) NOT NULL,
    installment_count INTEGER NOT NULL,
    monthly_installment NUMERIC(19,2) NOT NULL,
    tax NUMERIC(5,4) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_simulation_simulation_id
    ON simulation (simulation_id);
