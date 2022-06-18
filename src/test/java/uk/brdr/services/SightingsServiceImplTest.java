package uk.brdr.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.brdr.data.dao.GeoLocationsDao;
import uk.brdr.data.dao.GeoLocationsDaoImpl;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.data.dao.SightingsDaoImpl;
import uk.brdr.model.Species;
import uk.brdr.model.location.GeoLocation;
import uk.brdr.model.sighting.SightingDetail;
import uk.brdr.model.sighting.SightingsByGeo;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.UserSighting;

public class SightingsServiceImplTest {

  SightingsDao sightingsDao = mock(SightingsDaoImpl.class);
  GeoLocationsDao geoLocationsDao = mock(GeoLocationsDaoImpl.class);
  SightingsService sightingsService;

  @BeforeEach
  void setup() {
    sightingsService = new SightingsServiceImpl(sightingsDao, geoLocationsDao);
  }

  @Test
  void getSightingsForUser() {

    BigDecimal[] geo1 = {
        BigDecimal.valueOf(5324564, 5),
        BigDecimal.valueOf(-134567, 5)};
    BigDecimal[] geo2 = {
        BigDecimal.valueOf(5324565, 5),
        BigDecimal.valueOf(-134568, 5)};

    when(sightingsDao.getSightings(1)).thenReturn(List.of(
        new UserSighting(8, 23),
        new UserSighting(8, 12),
        new UserSighting(8, 5),
        new UserSighting(8, 45),
        new UserSighting(56, 64),
        new UserSighting(56, 13),
        new UserSighting(56, 78)));

    when(geoLocationsDao.getGeos(List.of(8, 56))).thenReturn(List.of(
        new GeoLocation(8, "Bath and North East Somerset", List.of(geo1, geo2)),
        new GeoLocation(56, "East Yorkshire", List.of(geo1, geo2))
    ));
    var sightingsByLocation =
        sightingsService.getSightings(1);
    var expected =
        List.of(
            new SightingsByGeo("Bath and North East Somerset", 8, List.of(geo1, geo2), List.of(23, 12, 5, 45)),
            new SightingsByGeo("East Yorkshire", 56, List.of(geo1, geo2), List.of(64, 13, 78)));

    assertEquals(sightingsByLocation.size(), expected.size());
    assertEquals(expected, sightingsByLocation);
  }

  @Test
  void noSightingsForUser() {
    when(sightingsDao.getSightings(1)).thenReturn(List.of());
    var actual = sightingsService.getSightings(1);
    assert(actual.isEmpty());
  }

  @Test
  void sightingsForUserForGeo() {
    var species = new Species(1, "some scientific name", "pref name", "habitat", "genus", "family", "family order", "1000", "1500");
    when(sightingsDao.getSightings(1, 1)).thenReturn(List.of(
        new SightingDetail(Date.valueOf("2022-05-27"), species),
        new SightingDetail(Date.valueOf("2022-05-24"), species),
        new SightingDetail(Date.valueOf("2022-05-24"), species),
        new SightingDetail(Date.valueOf("2022-05-27"), species)
        ));

    var actual = sightingsService.getSightings(1, 1);
    var expected = Map.of("2022-05-27", List.of(species, species), "2022-05-24", List.of(species, species));
    assertEquals(expected, actual);
  }
  @Test
  void addValidSighting() {
    var sighting = new Sighting(0, 1, List.of(1), 1, Date.valueOf("2022-05-25"));
    when(sightingsDao.getSightings(1)).thenReturn(List.of());
    sightingsService.addSighting(sighting);
    verify(sightingsDao).addSighting(sighting);
  }

  @Test
  void dontAddSightingWhenAlreadyExist() {
    var sighting = new Sighting(0, 1, List.of(1), 1, Date.valueOf("2022-05-25"));
    when(sightingsDao.getSightings(1)).thenReturn(List.of(new UserSighting(1, 1)));
    assertThrows(IllegalStateException.class, () -> sightingsService.addSighting(sighting));
    verify(sightingsDao, never()).addSighting(sighting);
  }
}
