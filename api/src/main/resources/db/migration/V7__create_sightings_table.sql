CREATE TABLE IF NOT EXISTS SIGHTINGS
(
    id          SERIAL PRIMARY KEY,
    species_id  INT    REFERENCES species,
    location_id INT    REFERENCES locations,
    user_id     INT    REFERENCES users,
    date        DATE
)
