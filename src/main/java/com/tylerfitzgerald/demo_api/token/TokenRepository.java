package com.tylerfitzgerald.demo_api.token;

import com.tylerfitzgerald.demo_api.sql.RepositoryInterface;
import com.tylerfitzgerald.demo_api.sql.TokenTable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenRepository implements RepositoryInterface<TokenDTO, Long> {

    private final JdbcTemplate jdbcTemplate;

    private static final String READ_SQL          = "SELECT * FROM " + TokenTable.TABLE_NAME;
    // CRUD SQL
    private static final String CREATE_SQL        = "INSERT INTO " + TokenTable.TABLE_NAME + " VALUES (null, ?, ?, ?, ?, ?, ?)";
    private static final String READ_BY_ID_SQL    = "SELECT * FROM " + TokenTable.TABLE_NAME + " WHERE tokenId = ?";
    private static final String UPDATE_SQL        = "UPDATE " + TokenTable.TABLE_NAME + " set saleId = ?, name = ?, description = ?, externalUrl = ?, imageUrl = ? WHERE tokenId = ?";
    private static final String DELETE_BY_ID_SQL  = "DELETE FROM " + TokenTable.TABLE_NAME + " WHERE tokenId = ?";

    public TokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TokenDTO> read() {
        Stream<TokenDTO> stream = null;
        try {
            stream = jdbcTemplate.queryForStream(
                    READ_SQL,
                    new BeanPropertyRowMapper(TokenDTO.class)
            );
            return stream.collect(Collectors.toList());
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    @Override
    public TokenDTO readById(Long tokenId) {
        Stream<TokenDTO> stream = null;
        try {
            if (tokenId == 0) {
                // TokenId starts at index 1
                return null;
            }
            stream = jdbcTemplate.queryForStream(
                    READ_BY_ID_SQL,
                    new BeanPropertyRowMapper(TokenDTO.class),
                    tokenId
            );
            List<TokenDTO> tokens = stream.collect(Collectors.toList());
            if (tokens.size() == 0) {
                return null;
            }
            return tokens.get(0);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    @Override
    public TokenDTO create(TokenDTO entity) {
        if (doesTokenIdExist(entity)) {
            return null;
        }
        int results = jdbcTemplate.update(
                CREATE_SQL,
                entity.getTokenId(),
                entity.getSaleId(),
                entity.getName(),
                entity.getDescription(),
                entity.getExternalUrl(),
                entity.getImageUrl()
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
                entity.getSaleId(),
                entity.getName(),
                entity.getDescription(),
                entity.getExternalUrl(),
                entity.getImageUrl(),
                entity.getTokenId()
        );
        if (results < 1) {
            return null;
        }
        return readById(entity.getTokenId());
    }

    @Override
    public boolean delete(TokenDTO entity) {
        if (!doesTokenIdExist(entity)) {
            return false;
        }
        jdbcTemplate.update(
                DELETE_BY_ID_SQL,
                entity.getTokenId()
        );
        return !doesTokenIdExist(entity);
    }

    private boolean doesTokenIdExist(TokenDTO entity) {
        return readById(entity.getTokenId()) != null;
    }
}
