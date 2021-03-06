package io.tilenft.sql.tbls;

import io.tilenft.config.external.TraitsConfig;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import io.tilenft.sql.repositories.WeightlessTraitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightlessTraitTypesTable implements TableInterface {

  @Autowired private TraitsConfig traitsConfig;

  @Autowired private WeightlessTraitTypeRepository weightlessTraitTypeRepository;

  /*
   * NOTE: 2083 is the max VARCHAR length for a URL on the internet explorer browser.
   * So let's make the max string lengths equal to that (we will likely not need that long of strings anyways).
   */
  public static final String TABLE_NAME = "tblWeightlessTraitTypes";
  private static final String CREATE_SQL =
      "CREATE TABLE "
          + TABLE_NAME
          + "(id int NOT NULL AUTO_INCREMENT, weightlessTraitTypeId int, weightlessTraitTypeName VARCHAR(2083), description VARCHAR(2083), PRIMARY KEY (id))";
  private static final String DELETE_SQL = "DROP TABLE " + TABLE_NAME;

  private final JdbcTemplate jdbcTemplate;

  public WeightlessTraitTypesTable(JdbcTemplate jdbcTemplate) {
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
    WeightlessTraitTypeDTO[] traitTypes = traitsConfig.getWeightlessTypes();
    for (WeightlessTraitTypeDTO traitType : traitTypes) {
      WeightlessTraitTypeDTO weightlessTraitTypeDTO =
          weightlessTraitTypeRepository.create(traitType);
      if (weightlessTraitTypeDTO == null) {
        System.out.println(
            "Failed to insert weightless trait type into "
                + TABLE_NAME
                + ".\nFailed weightless trait type: "
                + traitType);
      } else {
        System.out.println(
            "Inserted weightless trait type into "
                + TABLE_NAME
                + ".\nWeightless Trait Type: "
                + weightlessTraitTypeDTO);
      }
    }
    return true;
  }
}
