CREATE TABLE IF NOT EXISTS LOCATIONS
(
    id         SERIAL       PRIMARY KEY,
    name       TEXT         NOT NULL,
    county_id  INT          REFERENCES counties,
    region_id  INT          REFERENCES regions,
    country_id INT          REFERENCES countries,
    lat        numeric(8,5) NOT NULL,
    lon        numeric(8,5) NOT NULL
)
