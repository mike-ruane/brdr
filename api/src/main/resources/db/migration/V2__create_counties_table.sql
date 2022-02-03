CREATE TABLE IF NOT EXISTS COUNTIES
(
    id   SERIAL       PRIMARY KEY,
    name TEXT         NOT NULL,
    lat  numeric(8,5) NOT NULL,
    lon  numeric(8,5) NOT NULL
)