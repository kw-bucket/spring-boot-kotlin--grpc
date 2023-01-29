CREATE SCHEMA IF NOT EXISTS example_grpc;

CREATE TABLE IF NOT EXISTS example_grpc.payment (
    id              SERIAL,

    datetime        TIMESTAMP NOT NULL,
    customer_id     VARCHAR(32) NOT NULL,
    sales           DECIMAL(16, 2) NOT NULL,
    points          INT NOT NULL,

    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
