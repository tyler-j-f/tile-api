package com.tylerfitzgerald.demo_api.sql;

import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class TraitTypesTable implements TableInterface {

  @Autowired private TraitTypeRepository traitTypeRepository;

  /*
   * NOTE: 2083 is the max VARCHAR length for a URL on the internet explorer browser.
   * So let's make the max string lengths equal to that (we will likely not need that long of strings anyways).
   */
  public static final String TABLE_NAME = "tblTraitTypes";
  private static final String CREATE_SQL =
      "CREATE TABLE "
          + TABLE_NAME
          + "(id int NOT NULL AUTO_INCREMENT, traitTypeId int, traitTypeName VARCHAR(2083), description VARCHAR(2083), PRIMARY KEY (id))";
  private static final String DELETE_SQL = "DROP TABLE " + TABLE_NAME;

  private final JdbcTemplate jdbcTemplate;

  public TraitTypesTable(JdbcTemplate jdbcTemplate) {
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

  public boolean initialize() {
    traitTypeRepository.create(
        TraitTypeDTO.builder()
            .traitTypeId(1L)
            .traitTypeName("Color")
            .description("Color of the sub-tiles")
            .build());
    traitTypeRepository.create(
        TraitTypeDTO.builder()
            .traitTypeId(2L)
            .traitTypeName("Shape")
            .description("Shape of the sub-tiles")
            .build());
    return true;
  }
}
