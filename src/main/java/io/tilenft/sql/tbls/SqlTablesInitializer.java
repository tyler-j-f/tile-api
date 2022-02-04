package io.tilenft.sql.tbls;

import org.springframework.beans.factory.annotation.Autowired;

public class SqlTablesInitializer {
  @Autowired private WeightedTraitTypesTable weightedTraitTypesTable;
  @Autowired private WeightedTraitTypeWeightsTable weightedTraitTypeWeightsTable;
  @Autowired private WeightlessTraitTypesTable weightlessTraitTypesTable;

  public String initialTablesPopulate() {
    weightedTraitTypesTable.initialize();
    weightedTraitTypeWeightsTable.initialize();
    weightlessTraitTypesTable.initialize();
    return "Trait types table initialized with initial data successfully";
  }
}
