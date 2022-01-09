package uk.brdr.data;

import com.typesafe.config.Config;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import uk.brdr.properties.DatabaseProperties;

public class DatabaseConfiguration {

  private final DatabaseProperties databaseProperties;

  public DatabaseConfiguration(DatabaseProperties databaseProperties) {
    this.databaseProperties = databaseProperties;
  }

  public DatabaseProperties getDatabaseProperties() {
    return databaseProperties;
  }

  public DataSource getDatasource() {
    var datasource = new PGSimpleDataSource();
    datasource.setUrl(databaseProperties.getUrl());
    datasource.setUser(databaseProperties.getUser());
    datasource.setPassword(databaseProperties.getPassword());
    return datasource;
  }

}
