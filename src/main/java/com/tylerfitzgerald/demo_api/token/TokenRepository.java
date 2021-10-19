package com.tylerfitzgerald.demo_api.token;

import com.tylerfitzgerald.demo_api.RepositoryInterface;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenRepository implements RepositoryInterface<TokenDTO, Long> {

    private final JdbcTemplate jdbcTemplate;

    private final String READ_SQL        = "SELECT * FROM token";
    private final String READ_BY_ID_SQL  = "SELECT * FROM token WHERE tokenId = ?";
    private final String CREATE_SQL      = "INSERT INTO token VALUES (null , ?, ?)";
    private final String UPDATE_SQL      = "UPDATE token set saleId = ?";

    public TokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TokenDTO> read() {
        Stream<TokenDTO> stream = jdbcTemplate.queryForStream(
                READ_SQL,
                new BeanPropertyRowMapper(TokenDTO.class)
        );
        return stream.collect(Collectors.toList());
    }

    @Override
    public TokenDTO readById(Long tokenId) {
        if (tokenId == 0) {
            // TokenId starts at index 1
            return null;
        }
        Stream<TokenDTO> stream = jdbcTemplate.queryForStream(
                READ_BY_ID_SQL,
                new BeanPropertyRowMapper(TokenDTO.class),
                tokenId
        );
        List<TokenDTO> tokens = stream.collect(Collectors.toList());
        if (tokens.size() == 0) {
            return null;
        }
        return tokens.get(0);
    }

    @Override
    public TokenDTO create(TokenDTO entity) {
        if (doesTokenIdExist(entity)) {
            return null;
        }
        int results = jdbcTemplate.update(
                CREATE_SQL,
                entity.getTokenId(),
                entity.getSaleId()
        );
        if (results != 1) {
            return null;
        }
        return readById(entity.getTokenId());
    }

    @Override
    /**
     * NOTE: Only the sale id can be updated
     */
    public TokenDTO update(TokenDTO entity) {
        if (!doesTokenIdExist(entity)) {
            return null;
        }
        int results = jdbcTemplate.update(
                UPDATE_SQL,
                entity.getSaleId()
        );
        if (results < 1) {
            return null;
        }
        return readById(entity.getTokenId());
    }

    @Override
    public TokenDTO delete(TokenDTO entity) {
        return null;
    }

    private boolean doesTokenIdExist(TokenDTO entity) {
        return readById(entity.getTokenId()) != null;
    }
}
