CREATE TABLE IF NOT EXISTS LOCATIONS
(
    id       SERIAL  PRIMARY KEY,
    location TEXT    NOT NULL,
    county   TEXT    NOT NULL
)
