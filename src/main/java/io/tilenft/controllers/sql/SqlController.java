package io.tilenft.controllers.sql;

import io.tilenft.controllers.BaseController;
import io.tilenft.sql.tbls.SqlTablesCreator;
import io.tilenft.sql.tbls.SqlTablesDropper;
import io.tilenft.sql.tbls.SqlTablesInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/sql"})
public class SqlController extends BaseController {
  @Autowired private SqlTablesCreator sqlTablesCreator;
  @Autowired private SqlTablesInitializer sqlTablesInitializer;
  @Autowired private SqlTablesDropper sqlTablesDropper;

  @GetMapping("createSqlTables")
  public String createSqlTables() {
    return sqlTablesCreator.createSqlTables();
  }

  @GetMapping("initialTablesPopulate")
  public String initialTablesPopulate() {
    return sqlTablesInitializer.initialTablesPopulate();
  }

  @GetMapping("dropSqlTables")
  public String dropSqlTables() {
    return sqlTablesDropper.dropSqlTables();
  }
}
