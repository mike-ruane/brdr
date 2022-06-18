package uk.brdr.model.sighting;

public class UserSighting {

  int geoId;
  int speciesId;

  public UserSighting(int geoId, int speciesId) {
    this.geoId = geoId;
    this.speciesId = speciesId;
  }

  public UserSighting() {
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
