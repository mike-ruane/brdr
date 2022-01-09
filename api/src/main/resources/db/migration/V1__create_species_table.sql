CREATE TABLE IF NOT EXISTS SPECIES
(
    id                        SERIAL PRIMARY KEY,
    scientific_name           TEXT   NOT NULL,
    preferred_common_name     TEXT   NOT NULL,
    habitat                   TEXT   NULL,
    genus                     TEXT   NOT NULL,
    family                    TEXT   NOT NULL,
    family_order              TEXT   NOT NULL,
    breeding_population       TEXT   NULL,
    winter_visitor_population TEXT   NULL
)
