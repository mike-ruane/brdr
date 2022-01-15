package uk.brdr.utils;

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

}
