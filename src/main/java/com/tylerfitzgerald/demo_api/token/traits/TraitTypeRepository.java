package com.tylerfitzgerald.demo_api.token.traits;

import com.tylerfitzgerald.demo_api.sql.RepositoryInterface;
import com.tylerfitzgerald.demo_api.sql.TraitTypesTable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TraitTypeRepository implements RepositoryInterface<TraitTypeDTO, Long> {

    private final JdbcTemplate jdbcTemplate;

    private static final String READ_SQL          = "SELECT * FROM " + TraitTypesTable.TABLE_NAME;
    private static final String READ_BY_ID_SQL    = "";
    private static final String CREATE_SQL        = "";
    private static final String UPDATE_SQL        = "";
    private static final String DELETE_BY_ID_SQL  = "";

    public TraitTypeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TraitTypeDTO> read() {
        Stream<TraitTypeDTO> stream = null;
        try {
            stream = jdbcTemplate.queryForStream(
                    READ_SQL,
                    new BeanPropertyRowMapper(TraitTypeDTO.class)
            );
            return stream.collect(Collectors.toList());
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    @Override
    public TraitTypeDTO readById(Long tokenId) {
        return null;
    }

    @Override
    public TraitTypeDTO create(TraitTypeDTO entity) {
        return null;
    }

    @Override
    /**
     * NOTE: Only the sale id can be updated
     */
    public TraitTypeDTO update(TraitTypeDTO entity) {
        return null;
    }

    @Override
    public boolean delete(TraitTypeDTO entity) {
        return false;
    }
}
