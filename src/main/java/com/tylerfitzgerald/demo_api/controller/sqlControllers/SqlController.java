package com.tylerfitzgerald.demo_api.controller.sqlControllers;

import com.tylerfitzgerald.demo_api.controller.BaseController;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenTable;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightsTable;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypesTable;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitsTable;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypesTable;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/sql"})
public class SqlController extends BaseController {

  @Autowired private TokenTable tokenTable;

  @Autowired private WeightedTraitTypesTable weightedTraitTypesTable;

  @Autowired private WeightedTraitTypeWeightsTable weightedTraitTypeWeightsTable;

  @Autowired private WeightedTraitsTable weightedTraitsTable;

  @Autowired private WeightlessTraitsTable weightlessTraitsTable;

  @Autowired private WeightlessTraitTypesTable weightlessTraitTypesTable;

  @GetMapping("createSqlTables")
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

  @GetMapping("initialTablesPopulate")
  public String initialTablesPopulate() {
    weightedTraitTypesTable.initialize();
    weightedTraitTypeWeightsTable.initialize();
    weightlessTraitTypesTable.initialize();
    return "Trait types table initialized with initial data successfully";
  }

  @GetMapping("dropSqlTables")
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
