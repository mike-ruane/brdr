import mapbox from 'mapbox-gl';

// https://docs.mapbox.com/help/glossary/access-token/
mapbox.accessToken = "super-secret-access-token";

const key = Symbol();

export { mapbox, key };