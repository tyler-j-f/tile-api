package com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits;

import com.tylerfitzgerald.demo_api.sql.TableInterface;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightlessTraitsTable implements TableInterface {

  /*
   * NOTE: 2083 is the max VARCHAR length for a URL on the internet explorer browser.
   * So let's make the max string lengths equal to that (we will likely not need that long of strings anyways).
   */
  public static final String TABLE_NAME = "tblWeightlessTraits";
  private static final String CREATE_SQL =
      "CREATE TABLE "
          + TABLE_NAME
          + "(id int NOT NULL AUTO_INCREMENT, weightlessTraitId int, tokenId int, weightlessTraitTypeId int, value VARCHAR(2083), displayTypeValue VARCHAR(2083), PRIMARY KEY (id))";
  private static final String DELETE_SQL = "DROP TABLE " + TABLE_NAME;

  private final JdbcTemplate jdbcTemplate;

  public WeightlessTraitsTable(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public boolean create() {
    this.jdbcTemplate.execute(CREATE_SQL);
    return true;
  }

  @Override
  public boolean delete() {
    this.jdbcTemplate.execute(DELETE_SQL);
    return true;
  }
}
