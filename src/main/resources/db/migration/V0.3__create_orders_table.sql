CREATE TABLE broker.order
(
    order_id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    amount           NUMERIC,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT version_is_positive CHECK (amount >= 0)
);