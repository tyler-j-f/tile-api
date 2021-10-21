package com.tylerfitzgerald.demo_api.sql;

import com.tylerfitzgerald.demo_api.sql.TableInterface;
import org.springframework.jdbc.core.JdbcTemplate;

public class TokenTable implements TableInterface {

    /*
     * NOTE: 2083 is the max VARCHAR length for a URL on the internet explorer browser.
     * So let's make the max string lengths equal to that (we will likely not need that long of strings anyways).
     */
    private static final String CREATE_SQL  = "CREATE TABLE token(id int NOT NULL AUTO_INCREMENT, tokenId int, saleId int, name VARCHAR(2083), description VARCHAR(2083), externalUrl VARCHAR(2083), imageUrl VARCHAR(2083), PRIMARY KEY (id))";
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
