CREATE TABLE broker.order_event
(
    order_id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    event_type       VARCHAR,
    version          NUMERIC,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    event_data       json,
    CONSTRAINT version_is_positive CHECK (version >= 0)
);