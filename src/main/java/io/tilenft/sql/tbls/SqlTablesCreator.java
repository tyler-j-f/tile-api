package io.tilenft.sql.tbls;

import org.springframework.beans.factory.annotation.Autowired;

public class SqlTablesCreator {
  @Autowired private TokenTable tokenTable;
  @Autowired private WeightedTraitTypesTable weightedTraitTypesTable;
  @Autowired private WeightedTraitTypeWeightsTable weightedTraitTypeWeightsTable;
  @Autowired private WeightedTraitsTable weightedTraitsTable;
  @Autowired private WeightlessTraitsTable weightlessTraitsTable;
  @Autowired private WeightlessTraitTypesTable weightlessTraitTypesTable;

  public String createSqlTables() {
    String output;
    if (tokenTable.create()) {
      output = "\nToken table created successfully";
    } else {
      output = "\nToken table failed to create";
    }
    if (weightedTraitTypesTable.create()) {
      output = output + "\nTrait types table created successfully";
    } else {
      output = output + "\nTrait types table failed to create";
    }
    if (weightedTraitTypeWeightsTable.create()) {
      output = output + "\nTrait type weights table created successfully";
    } else {
      output = output + "\nTrait type weights table failed to create";
    }
    if (weightedTraitsTable.create()) {
      output = output + "\nTraits table created successfully";
    } else {
      output = output + "\nTraits table failed to create";
    }
    if (weightlessTraitsTable.create()) {
      output = output + "\nWeightless Traits table created successfully";
    } else {
      output = output + "\nWeightless Traits table failed to create";
    }
    if (weightlessTraitTypesTable.create()) {
      output = output + "\nWeightless Trait Types table created successfully";
    } else {
      output = output + "\nWeightless Trait Types table failed to create";
    }
    return output;
  }
}
