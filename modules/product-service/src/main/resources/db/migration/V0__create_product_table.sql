CREATE SCHEMA IF NOT EXISTS schema1;

CREATE TABLE schema1.products (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255),
        description TEXT,
        price DECIMAL,
        category VARCHAR(255),
        created_at TIMESTAMP,
        updated_at TIMESTAMP
);