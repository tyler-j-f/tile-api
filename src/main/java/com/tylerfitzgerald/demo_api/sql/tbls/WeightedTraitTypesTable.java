package com.tylerfitzgerald.demo_api.sql.tbls;

import com.tylerfitzgerald.demo_api.config.external.TraitsConfig;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightedTraitTypesTable implements TableInterface {

  @Autowired private TraitsConfig traitsConfig;

  @Autowired private WeightedTraitTypeRepository traitTypeWeightRepository;

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

  public WeightedTraitTypesTable(JdbcTemplate jdbcTemplate) {
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
    WeightedTraitTypeDTO[] traitTypes = traitsConfig.getWeightedTypes();
    for (WeightedTraitTypeDTO traitType : traitTypes) {
      WeightedTraitTypeDTO createResultWeightedTraitTypeDTO =
          traitTypeWeightRepository.create(traitType);
      if (createResultWeightedTraitTypeDTO == null) {
        System.out.println(
            "Failed to insert trait type into "
                + TABLE_NAME
                + ".\nFailed trait type: "
                + traitType.toString());
      } else {
        System.out.println(
            "Inserted trait type into "
                + TABLE_NAME
                + ".\nTrait Type: "
                + createResultWeightedTraitTypeDTO.toString());
      }
    }
    return true;
  }
}
