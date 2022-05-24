package uk.brdr.model.sighting;

public class SightingForUser {

  int geoId;
  int speciesId;

  public SightingForUser(int geoId, int speciesId) {
    this.geoId = geoId;
    this.speciesId = speciesId;
  }

  public SightingForUser() {
  }

  public int getGeoId() {
    return geoId;
  }

  public int getSpeciesId() {
    return speciesId;
  }

  public void setGeoId(int geoId) {
    this.geoId = geoId;
  }

  public void setSpeciesId(int speciesId) {
    this.speciesId = speciesId;
  }
}
