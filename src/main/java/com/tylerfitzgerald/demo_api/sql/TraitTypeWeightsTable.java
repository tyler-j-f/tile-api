package com.tylerfitzgerald.demo_api.sql;

import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.token.traitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class TraitTypeWeightsTable implements TableInterface {

  @Autowired private TraitsConfig traitsConfig;

  @Autowired private TraitTypeWeightRepository traitTypeWeightRepository;

  /*
   * NOTE: 2083 is the max VARCHAR length for a URL on the internet explorer browser.
   * So let's make the max string lengths equal to that (we will likely not need that long of strings anyways).
   */
  public static final String TABLE_NAME = "tblTraitTypeWeights";
  private static final String CREATE_SQL =
      "CREATE TABLE "
          + TABLE_NAME
          + "(id int NOT NULL AUTO_INCREMENT, traitTypeWeightId int, traitTypeId int, likelihood int, value VARCHAR(2083), displayTypeValue VARCHAR(2083), PRIMARY KEY (id))";
  private static final String DELETE_SQL = "DROP TABLE " + TABLE_NAME;

  private final JdbcTemplate jdbcTemplate;

  public TraitTypeWeightsTable(JdbcTemplate jdbcTemplate) {
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
    TraitTypeWeightDTO[] traitTypeWeights = traitsConfig.getTypeWeights();
    for (TraitTypeWeightDTO traitTypeWeight : traitTypeWeights) {
      TraitTypeWeightDTO createResultTraitTypeWeightDTO =
          traitTypeWeightRepository.create(traitTypeWeight);
      if (createResultTraitTypeWeightDTO == null) {
        System.out.println(
            "Failed to insert trait type weight into "
                + TABLE_NAME
                + ".\nFailed trait type weight: "
                + traitTypeWeight.toString());
      } else {
        System.out.println(
            "Inserted trait type weight into "
                + TABLE_NAME
                + ".\nTrait: "
                + createResultTraitTypeWeightDTO.toString());
      }
    }
    return true;
  }
}
