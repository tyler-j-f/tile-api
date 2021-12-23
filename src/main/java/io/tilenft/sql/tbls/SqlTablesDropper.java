package io.tilenft.sql.tbls;

import org.springframework.beans.factory.annotation.Autowired;

public class SqlTablesDropper {
  @Autowired private TokenTable tokenTable;
  @Autowired private WeightedTraitTypesTable weightedTraitTypesTable;
  @Autowired private WeightedTraitTypeWeightsTable weightedTraitTypeWeightsTable;
  @Autowired private WeightedTraitsTable weightedTraitsTable;
  @Autowired private WeightlessTraitsTable weightlessTraitsTable;
  @Autowired private WeightlessTraitTypesTable weightlessTraitTypesTable;

  public String dropSqlTables() {
    String output;
    if (tokenTable.delete()) {
      output = "Token table deleted successfully";
    } else {
      output = "Token table failed to delete";
    }
    if (weightedTraitTypesTable.delete()) {
      output = output + "\n" + "Trait types table deleted successfully";
    } else {
      output = output + "\n" + "Trait types table failed to delete";
    }
    if (weightedTraitTypeWeightsTable.delete()) {
      output = output + "\n" + "Trait type weights table deleted successfully";
    } else {
      output = output + "\n" + "Trait type weights table failed to delete";
    }
    if (weightedTraitsTable.delete()) {
      output = output + "\n" + "Traits table deleted successfully";
    } else {
      output = output + "\n" + "Traits table failed to delete";
    }
    if (weightlessTraitsTable.delete()) {
      output = output + "\n" + "Weightless traits table deleted successfully";
    } else {
      output = output + "\n" + "Weightless traits table failed to delete";
    }
    if (weightlessTraitTypesTable.delete()) {
      output = output + "\n" + "Weightless trait types table deleted successfully";
    } else {
      output = output + "\n" + "Weightless trait types table failed to delete";
    }
    return output;
  }
}
