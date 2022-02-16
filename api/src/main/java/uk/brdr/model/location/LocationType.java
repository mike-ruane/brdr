package uk.brdr.model.location;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum LocationType {
  LOCATIONS("locations"),
  COUNTIES("counties"),
  REGIONS("regions"),
  COUNTRIES("countries");

  private final String name;

  private static final Map<String, LocationType> ENUM_MAP;

  LocationType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  static {
    Map<String, LocationType> map = new ConcurrentHashMap<String, LocationType>();
    for (LocationType instance : LocationType.values()) {
      map.put(instance.getName().toLowerCase(), instance);
    }
    ENUM_MAP = Collections.unmodifiableMap(map);
  }

  public static LocationType get(String name) {
    return ENUM_MAP.get(name.toLowerCase());
  }
}
