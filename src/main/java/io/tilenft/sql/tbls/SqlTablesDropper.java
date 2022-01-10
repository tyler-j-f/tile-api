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
    output = deleteTokenTable();
    output = output + deleteWeightedTraitTypesTable();
    output = output + deleteWeightedTraitTypeWeightsTable();
    output = output + deleteWeightedTraitsTable();
    output = output + deleteWeightlessTraitsTable();
    output = output + deleteWeightlessTraitTypesTable();
    return output;
  }

  private String deleteTokenTable() {
    try {
      if (tokenTable.delete()) {
        return "\nToken table deleted successfully.";
      } else {
        return "\nToken table failed to delete.";
      }
    } catch (Exception e) {
      System.out.println("deleteTokenTable Exception caught: " + e);
      return "\nToken table failed to delete.";
    }
  }

  private String deleteWeightedTraitTypesTable() {
    try {
      if (weightedTraitTypesTable.delete()) {
        return "\nWeighted trait types table deleted successfully.";
      } else {
        return "\nWeighted trait types table failed to delete.";
      }
    } catch (Exception e) {
      System.out.println("deleteWeightedTraitTypesTable Exception caught: " + e);
      return "\nWeighted trait types table failed to delete.";
    }
  }

  private String deleteWeightedTraitTypeWeightsTable() {
    try {
      if (weightedTraitTypeWeightsTable.delete()) {
        return "\nWeighted trait type weights table deleted successfully.";
      } else {
        return "\nWeighted trait type weights table failed to delete.";
      }
    } catch (Exception e) {
      System.out.println("deleteWeightedTraitTypeWeightsTable Exception caught: " + e);
      return "\nWeighted trait type weights table failed to delete.";
    }
  }

  private String deleteWeightedTraitsTable() {
    try {
      if (weightedTraitsTable.delete()) {
        return "\nWeighted traits table deleted successfully.";
      } else {
        return "\nWeighted traits table failed to delete.";
      }
    } catch (Exception e) {
      System.out.println("deleteWeightedTraitsTable Exception caught: " + e);
      return "\nWeighted traits table failed to delete.";
    }
  }

  private String deleteWeightlessTraitsTable() {
    try {
      if (weightlessTraitsTable.delete()) {
        return "\nWeightless traits table deleted successfully.";
      } else {
        return "\nWeightless traits table failed to delete.";
      }
    } catch (Exception e) {
      System.out.println("deleteWeightlessTraitsTable Exception caught: " + e);
      return "\nWeightless traits table failed to delete.";
    }
  }

  private String deleteWeightlessTraitTypesTable() {
    try {
      if (weightlessTraitTypesTable.delete()) {
        return "\nWeightless trait types table deleted successfully.";
      } else {
        return "\nWeightless trait types table failed to delete.";
      }
    } catch (Exception e) {
      System.out.println("deleteWeightlessTraitsTable Exception caught: " + e);
      return "\nWeightless trait types table failed to delete.";
    }
  }
}
