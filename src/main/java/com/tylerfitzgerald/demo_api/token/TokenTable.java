package com.tylerfitzgerald.demo_api.token;

import com.tylerfitzgerald.demo_api.sql.TableInterface;
import org.springframework.jdbc.core.JdbcTemplate;

public class TokenTable implements TableInterface {

    private final String CREATE_SQL  = "CREATE TABLE token(id int NOT NULL AUTO_INCREMENT, tokenId int, saleId int, PRIMARY KEY (id))";
    private final String DELETE_SQL  = "DROP TABLE token";

    private final JdbcTemplate jdbcTemplate;

    public TokenTable(JdbcTemplate jdbcTemplate) {
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
        return false;
    }
}
