package uk.brdr.data;

import java.util.List;
import uk.brdr.model.Species;

public interface SpeciesDao {
  List<Species> getAll();
}
