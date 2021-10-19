package com.tylerfitzgerald.demo_api.token;

import com.tylerfitzgerald.demo_api.RepositoryInterface;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenRepository implements RepositoryInterface<TokenDTO, Long> {

    private final JdbcTemplate jdbcTemplate;

    private final String READ_SQL  = "SELECT * FROM token";

    public TokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TokenDTO> read() {
        Stream<TokenDTO> stream = jdbcTemplate.queryForStream(READ_SQL, new BeanPropertyRowMapper(TokenDTO.class));
        return stream.collect(Collectors.toList());
    }

    @Override
    public TokenDTO readById(Long id) {
        String sql2 = "INSERT INTO token VALUES (null , 1, 2)";
        return null;
    }

    @Override
    public TokenDTO create(TokenDTO entity) {
        return null;
    }

    @Override
    public TokenDTO update(TokenDTO entity) {
        return null;
    }

    @Override
    public TokenDTO delete(TokenDTO entity) {
        return null;
    }
}
