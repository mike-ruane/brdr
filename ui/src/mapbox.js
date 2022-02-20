import mapbox from 'mapbox-gl';

// https://docs.mapbox.com/help/glossary/access-token/
mapbox.accessToken = "pk.eyJ1IjoibWlrZS1qcnVhbmUiLCJhIjoiY2t5aHNhOGQ1MHhjdTJ3cDBlZ3I4enQ1diJ9.sCjAbEbuBdbX7fbcx2Bjuw";

const key = Symbol();

export { mapbox, key };
