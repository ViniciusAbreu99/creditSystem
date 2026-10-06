-- Initial baseline migration for Flyway
-- Creates a very small table as a placeholder so Flyway has at least one migration
CREATE TABLE IF NOT EXISTS migration_baseline (
  id BIGSERIAL PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

