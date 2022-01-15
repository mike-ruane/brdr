CREATE TABLE IF NOT EXISTS SIGHTINGS
(
    id         SERIAL  PRIMARY KEY,
    user_id    INTEGER NOT NULL,
    species_id INTEGER NOT NULL,
    city       TEXT    NOT NULL
)
