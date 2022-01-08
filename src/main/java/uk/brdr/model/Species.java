package uk.brdr.model;

import java.util.Objects;

public class Species {

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

  @Override
  public String toString() {
    return "Species{" +
        "id=" + id +
        ", scientificName='" + scientificName + '\'' +
        ", preferredCommonName='" + preferredCommonName + '\'' +
        ", habitat='" + habitat + '\'' +
        ", genus='" + genus + '\'' +
        ", family='" + family + '\'' +
        ", familyOrder='" + familyOrder + '\'' +
        ", breedingPopulation='" + breedingPopulation + '\'' +
        ", winterVisitorPopulation='" + winterVisitorPopulation + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Species species = (Species) o;
    return id == species.id && Objects.equals(scientificName, species.scientificName)
        && Objects.equals(preferredCommonName, species.preferredCommonName)
        && Objects.equals(habitat, species.habitat) && Objects.equals(genus,
        species.genus) && Objects.equals(family, species.family)
        && Objects.equals(familyOrder, species.familyOrder) && Objects.equals(
        breedingPopulation, species.breedingPopulation) && Objects.equals(
        winterVisitorPopulation, species.winterVisitorPopulation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, scientificName, preferredCommonName, habitat, genus, family,
        familyOrder,
        breedingPopulation, winterVisitorPopulation);
  }
}
