CREATE TABLE IF NOT EXISTS SIGHTINGS
(
    id          SERIAL  PRIMARY KEY,
    species_id  INTEGER REFERENCES species,
    location_id INTEGER REFERENCES locations
)
