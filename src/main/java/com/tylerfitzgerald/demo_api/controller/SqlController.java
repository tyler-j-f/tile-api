package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.sql.TokenTable;

import com.tylerfitzgerald.demo_api.sql.TraitTypesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/sql"})
public class SqlController {

    @Autowired
    private TokenTable tokenTable;

    @Autowired
    private TraitTypesTable traitTypesTable;

    @GetMapping("createSqlTables")
    public String createSqlTables() {
        String output;
        if (tokenTable.create()) {
            output = "Token table created successfully";
        } else {
            output = "Token table failed to create";
        }
        if (traitTypesTable.create()) {
            output = output + "\n" + "Trait types table created successfully";
        } else {
            output = output + "\n" + "Trait types table failed to create";
        }
        return output;
    }

    @GetMapping("initialTablesPopulate")
    public String initialTablesPopulate() {
        traitTypesTable.initialize();
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
        if (traitTypesTable.delete()) {
            output = output + "\n" + "Trait types table deleted successfully";
        } else {
            output = output + "\n" + "Trait types table failed to delete";
        }
        return output;
    }

}
