package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.Species;

public interface SpeciesDao {
  List<Species> getAll();

  List<Species> getSpecies(List<Integer> species);

  Species get(int speciesId);
}
