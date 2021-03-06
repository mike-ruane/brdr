CREATE TABLE IF NOT EXISTS SIGHTINGS
(
    id          SERIAL PRIMARY KEY,
    species_id  INT    REFERENCES species,
    geo_id      INT    REFERENCES geo_locations,
    user_id     INT    REFERENCES users,
    date        DATE
)
