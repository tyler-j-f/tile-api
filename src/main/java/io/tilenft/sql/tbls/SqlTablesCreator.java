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
      output = "Token table created successfully";
    } else {
      output = "Token table failed to create";
    }
    if (weightedTraitTypesTable.create()) {
      output = output + "\n" + "Trait types table created successfully";
    } else {
      output = output + "\n" + "Trait types table failed to create";
    }
    if (weightedTraitTypeWeightsTable.create()) {
      output = output + "\n" + "Trait type weights table created successfully";
    } else {
      output = output + "\n" + "Trait type weights table failed to create";
    }
    if (weightedTraitsTable.create()) {
      output = output + "\n" + "Traits table created successfully";
    } else {
      output = output + "\n" + "Traits table failed to create";
    }
    if (weightlessTraitsTable.create()) {
      output = output + "\n" + "Weightless Traits table created successfully";
    } else {
      output = output + "\n" + "Weightless Traits table failed to create";
    }
    if (weightlessTraitTypesTable.create()) {
      output = output + "\n" + "Weightless Trait Types table created successfully";
    } else {
      output = output + "\n" + "Weightless Trait Types table failed to create";
    }
    return output;
  }
}
