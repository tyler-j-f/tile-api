package com.tylerfitzgerald.demo_api.sql.tblTraitTypes;

import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.sql.TableInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class TraitTypesTable implements TableInterface {

  @Autowired private TraitsConfig traitsConfig;

  @Autowired private TraitTypeRepository traitTypeWeightRepository;

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
    TraitTypeDTO[] traitTypes = traitsConfig.getWeightedTypes();
    for (TraitTypeDTO traitType : traitTypes) {
      TraitTypeDTO createResultTraitTypeDTO = traitTypeWeightRepository.create(traitType);
      if (createResultTraitTypeDTO == null) {
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
                + createResultTraitTypeDTO.toString());
      }
    }
    return true;
  }
}
