package uk.brdr.data;

import java.util.List;
import uk.brdr.model.Bird;

public interface SpeciesRepository {
  List<Bird> getSpecies();
}
