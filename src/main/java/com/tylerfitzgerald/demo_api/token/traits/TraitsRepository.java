package com.tylerfitzgerald.demo_api.token.traits;

import com.tylerfitzgerald.demo_api.sql.RepositoryInterface;
import com.tylerfitzgerald.demo_api.sql.TraitsTable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TraitsRepository implements RepositoryInterface<TraitDTO, Long> {

    private final JdbcTemplate jdbcTemplate;

    private static final String READ_SQL          = "SELECT * FROM " + TraitsTable.TABLE_NAME;
    // CRUD SQL
    private static final String CREATE_SQL        = "INSERT INTO " + TraitsTable.TABLE_NAME + " VALUES (null, ?, ?, ?, ?)";
    private static final String READ_BY_ID_SQL    = "SELECT * FROM " + TraitsTable.TABLE_NAME + " WHERE traitId = ?";
    private static final String UPDATE_SQL        = "UPDATE " + TraitsTable.TABLE_NAME + " set traitTypeId = ?, value = ?, displayTypeValue = ? WHERE traitId = ?";
    private static final String DELETE_BY_ID_SQL  = "DELETE FROM " + TraitsTable.TABLE_NAME + " WHERE traitId = ?";

    public TraitsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TraitDTO> read() {
        Stream<TraitDTO> stream = null;
        try {
            stream = jdbcTemplate.queryForStream(
                    READ_SQL,
                    new BeanPropertyRowMapper(TraitDTO.class)
            );
            return stream.collect(Collectors.toList());
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    @Override
    public TraitDTO readById(Long traitId) {
        Stream<TraitDTO> stream = null;
        try {
            if (traitId == 0) {
                // TokenId starts at index 1
                return null;
            }
            stream = jdbcTemplate.queryForStream(
                    READ_BY_ID_SQL,
                    new BeanPropertyRowMapper(TraitDTO.class),
                    traitId
            );
            List<TraitDTO> traitTypes = stream.collect(Collectors.toList());
            if (traitTypes.size() == 0) {
                return null;
            }
            return traitTypes.get(0);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    @Override
    public TraitDTO create(TraitDTO entity) {
        if (doesTraitIdExist(entity)) {
            return null;
        }
        int results = jdbcTemplate.update(
                CREATE_SQL,
                entity.getTraitId(),
                entity.getTraitTypeId(),
                entity.getValue(),
                entity.getDisplayTypeValue()
        );
        if (results != 1) {
            return null;
        }
        return readById(entity.getTraitId());
    }

    @Override
    /**
     * NOTE: Only the sale id can be updated
     */
    public TraitDTO update(TraitDTO entity) {
        if (!doesTraitIdExist(entity)) {
            return null;
        }
        int results = jdbcTemplate.update(
                UPDATE_SQL,
                entity.getTraitTypeId(),
                entity.getValue(),
                entity.getDisplayTypeValue(),
                entity.getTraitId()
        );
        if (results < 1) {
            return null;
        }
        return readById(entity.getTraitId());
    }

    @Override
    public boolean delete(TraitDTO entity) {
        if (!doesTraitIdExist(entity)) {
            return false;
        }
        jdbcTemplate.update(
                DELETE_BY_ID_SQL,
                entity.getTraitId()
        );
        return !doesTraitIdExist(entity);
    }

    private boolean doesTraitIdExist(TraitDTO entity) {
        return readById(entity.getTraitId()) != null;
    }
}
