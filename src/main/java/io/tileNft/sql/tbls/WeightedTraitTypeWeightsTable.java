package io.tileNft.sql.tbls;

import io.tileNft.config.external.TraitsConfig;
import io.tileNft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tileNft.sql.repositories.WeightedTraitTypeWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightedTraitTypeWeightsTable implements TableInterface {

  @Autowired private TraitsConfig traitsConfig;

  @Autowired private WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository;

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

  public WeightedTraitTypeWeightsTable(JdbcTemplate jdbcTemplate) {
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
    WeightedTraitTypeWeightDTO[] traitTypeWeights = traitsConfig.getTypeWeights();
    for (WeightedTraitTypeWeightDTO traitTypeWeight : traitTypeWeights) {
      WeightedTraitTypeWeightDTO createResultWeightedTraitTypeWeightDTO =
          weightedTraitTypeWeightRepository.create(traitTypeWeight);
      if (createResultWeightedTraitTypeWeightDTO == null) {
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
                + createResultWeightedTraitTypeWeightDTO.toString());
      }
    }
    return true;
  }
}
