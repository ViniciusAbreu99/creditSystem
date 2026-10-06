CREATE TABLE IF NOT EXISTS proposal (
    id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    name VARCHAR(255) NOT NULL,
    simulation_id UUID NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL DEFAULT 'CREATED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_proposal_simulation_id UNIQUE (simulation_id)
);

CREATE INDEX IF NOT EXISTS idx_proposal_simulation_id ON proposal (simulation_id);
