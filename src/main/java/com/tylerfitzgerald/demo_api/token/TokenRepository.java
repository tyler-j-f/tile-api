package com.tylerfitzgerald.demo_api.token;

import com.tylerfitzgerald.demo_api.RepositoryInterface;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenRepository implements RepositoryInterface<TokenDTO, Long> {

    private final JdbcTemplate jdbcTemplate;

    public TokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TokenDTO> read() {
        String sql = "SELECT * FROM token";
        Stream<TokenDTO> stream = jdbcTemplate.queryForStream(sql, new BeanPropertyRowMapper(TokenDTO.class));
        return stream.collect(Collectors.toList());
    }

    @Override
    public TokenDTO readById(Long id) {
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
