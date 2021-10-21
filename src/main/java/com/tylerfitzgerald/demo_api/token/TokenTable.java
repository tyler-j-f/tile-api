package com.tylerfitzgerald.demo_api.token;

import com.tylerfitzgerald.demo_api.sql.TableInterface;
import org.springframework.jdbc.core.JdbcTemplate;

public class TokenTable implements TableInterface {

    private static final String CREATE_SQL  = "CREATE TABLE token(id int NOT NULL AUTO_INCREMENT, tokenId int, saleId int, name NVCHAR(MAX), description NVCHAR(MAX), externalUrl NVCHAR(MAX), imageUrl NVCHAR(MAX), PRIMARY KEY (id))";
    private static final String DELETE_SQL  = "DROP TABLE token";

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
        return true;
    }
}
