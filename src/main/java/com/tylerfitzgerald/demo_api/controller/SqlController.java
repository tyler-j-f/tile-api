package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.token.TokenTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/sql"})
public class SqlController {

    @Autowired
    private TokenTable tokenTable;

    @GetMapping("createSqlTables")
    public String createSqlTables() {
        if (tokenTable.create()) {
            return "Token table created successfully";
        }
        return "Token table failed to create";
    }

    @GetMapping("dropSqlTables")
    public String dropSqlTables() {
        if (tokenTable.delete()) {
            return "Token table deleted successfully";
        }
        return "Token table failed to delete";
    }

}
