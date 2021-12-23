package uk.brdr.model;

public class Bird {

  public int id;
  public String scientificName;
  public String preferredCommonName;
  public String habitat;
  public String genus;
  public String family;
  public String familyOrder;
  public String breedingPopulation;
  public String winterVisitorPopulation;

  public int getId() {
    return id;
  }

  public String getScientificName() {
    return scientificName;
  }

  public String getPreferredCommonName() {
    return preferredCommonName;
  }

  public String getHabitat() {
    return habitat;
  }

  public String getGenus() {
    return genus;
  }

  public String getFamily() {
    return family;
  }

  public String getFamilyOrder() {
    return familyOrder;
  }

  public String getBreedingPopulation() {
    return breedingPopulation;
  }

  public String getWinterVisitorPopulation() {
    return winterVisitorPopulation;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setScientificName(String scientificName) {
    this.scientificName = scientificName;
  }

  public void setPreferredCommonName(String preferredCommonName) {
    this.preferredCommonName = preferredCommonName;
  }

  public void setHabitat(String habitat) {
    this.habitat = habitat;
  }

  public void setGenus(String genus) {
    this.genus = genus;
  }

  public void setFamily(String family) {
    this.family = family;
  }

  public void setFamilyOrder(String familyOrder) {
    this.familyOrder = familyOrder;
  }

  public void setBreedingPopulation(String breedingPopulation) {
    this.breedingPopulation = breedingPopulation;
  }

  public void setWinterVisitorPopulation(String winterVisitorPopulation) {
    this.winterVisitorPopulation = winterVisitorPopulation;
  }
}
