package uk.brdr.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public class DatabaseUtils {

  public static DataSource getH2DataSource() {
    var jdbcDataSource = new JdbcDataSource();
    jdbcDataSource.setUrl("jdbc:h2:~/brdr");
    jdbcDataSource.setUser("sa");
    jdbcDataSource.setPassword("");
    return jdbcDataSource;
  }

  public static void loadSpecies(DataSource dataSource) throws SQLException, IOException {
    var conn = dataSource.getConnection();
    var sql =
        "INSERT INTO species "
            + "(scientific_name, preferred_common_name, habitat, genus, family, family_order,"
            + "breeding_population, winter_visitor_population)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    var statement = conn.prepareStatement(sql);

    BufferedReader lineReader =
        new BufferedReader(new FileReader("src/test/resources/species.csv"));
    String lineText;
    lineReader.readLine(); // skip header

    while ((lineText = lineReader.readLine()) != null) {
      var data = lineText.split(",", -1);
      var scientificName = data[0];
      var preferredCommonName = data[1];
      var habitat = data[2];
      var genus = data[3];
      var family = data[4];
      var familyOrder = data[5];
      var breedingPopulation = data[6];
      var winterVisitorPopulation = data[7];

      statement.setString(1, scientificName);
      statement.setString(2, preferredCommonName);
      statement.setString(3, habitat);
      statement.setString(4, genus);
      statement.setString(5, family);
      statement.setString(6, familyOrder);
      statement.setString(7, breedingPopulation);
      statement.setString(8, winterVisitorPopulation);

      statement.execute();
    }
    lineReader.close();
    conn.commit();
    conn.close();
  }

  public static void loadLocations(DataSource dataSource) throws SQLException, IOException {
    var conn = dataSource.getConnection();
    var sql =
        "INSERT INTO locations "
            + "(name, county_id, region_id, country_id, lat, lon)"
            + "VALUES (?, ?, ?, ?, ?, ?)";
    var statement = conn.prepareStatement(sql);

    BufferedReader lineReader =
        new BufferedReader(new FileReader("src/test/resources/locations.csv"));
    String lineText;
    lineReader.readLine(); // skip header

    while ((lineText = lineReader.readLine()) != null) {
      var data = lineText.split(",", -1);
      var name = data[0];
      var countyId = data[1];
      var regionId = data[2];
      var countryId = data[3];
      var lat = data[4];
      var lon = data[5];

      statement.setString(1, name);
      statement.setInt(2, Integer.parseInt(countyId));
      statement.setInt(3, Integer.parseInt(regionId));
      statement.setInt(4, Integer.parseInt(countryId));
      statement.setBigDecimal(5, new BigDecimal(lat));
      statement.setBigDecimal(6, new BigDecimal(lon));

      statement.execute();
    }
    lineReader.close();
    conn.commit();
    conn.close();
  }

  public static void loadLocationGrouping(DataSource dataSource, String location)
      throws SQLException, IOException {
    var conn = dataSource.getConnection();
    var sql = String.format("INSERT INTO %s " + "(name, lat, lon)" + "VALUES (?, ?, ?)", location);
    var statement = conn.prepareStatement(sql);

    BufferedReader lineReader =
        new BufferedReader(new FileReader(String.format("src/test/resources/%s.csv", location)));
    String lineText;
    lineReader.readLine(); // skip header

    while ((lineText = lineReader.readLine()) != null) {
      var data = lineText.split(",", -1);
      var name = data[0];
      var lat = data[1];
      var lon = data[2];

      var bd = new BigDecimal(lat);

      statement.setString(1, name);
      statement.setBigDecimal(2, new BigDecimal(lat));
      statement.setBigDecimal(3, new BigDecimal(lon));

      statement.execute();
    }
    lineReader.close();
    conn.commit();
    conn.close();
  }
}
