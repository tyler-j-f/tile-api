package com.tylerfitzgerald.demo_api.token.traits;

import com.tylerfitzgerald.demo_api.sql.RepositoryInterface;
import com.tylerfitzgerald.demo_api.sql.TraitsTable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TraitsRepository implements RepositoryInterface<TraitDTO, Long> {

    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper beanPropertyRowMapper;

    public static final String READ_SQL          = "SELECT * FROM " + TraitsTable.TABLE_NAME;
    // CRUD SQL
    public static final String CREATE_SQL        = "INSERT INTO " + TraitsTable.TABLE_NAME + " VALUES (null, ?, ?, ?)";
    public static final String READ_BY_ID_SQL    = "SELECT * FROM " + TraitsTable.TABLE_NAME + " WHERE traitId = ?";
    public static final String UPDATE_SQL_BASE   = "UPDATE " + TraitsTable.TABLE_NAME + " set ";
    public static final String UPDATE_SQL        = "UPDATE " + TraitsTable.TABLE_NAME + " set traitTypeId = ?, traitTypeWeightId = ? WHERE traitId = ?";
    public static final String DELETE_BY_ID_SQL  = "DELETE FROM " + TraitsTable.TABLE_NAME + " WHERE traitId = ?";

    public TraitsRepository(
            JdbcTemplate jdbcTemplate,
            BeanPropertyRowMapper beanPropertyRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.beanPropertyRowMapper = beanPropertyRowMapper;
    }

    @Override
    public List<TraitDTO> read() {
        Stream<TraitDTO> stream = null;
        try {
            stream = jdbcTemplate.queryForStream(
                    READ_SQL,
                    beanPropertyRowMapper
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
            stream = jdbcTemplate.queryForStream(
                    READ_BY_ID_SQL,
                    beanPropertyRowMapper,
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
                entity.getTraitTypeWeightId()
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
        List<Object> updateValuesList = new ArrayList<>();
        String updateSQL  = UPDATE_SQL_BASE;
        // traitTypeId
        Long traitTypeId = entity.getTraitTypeId();
        boolean shouldUpdateTraitTypeId = traitTypeId != null;
        boolean isCommaNeededToAppend = false;
        if (shouldUpdateTraitTypeId) {
            updateSQL = updateSQL + "traitTypeId = ?";
            updateValuesList.add(traitTypeId);
            isCommaNeededToAppend = true;
        }
        // likelihood
        Long traitTypeWeightId = entity.getTraitTypeWeightId();
        boolean shouldUpdateTraitTypeWeightId = traitTypeWeightId != null;
        if (shouldUpdateTraitTypeWeightId) {
            if (isCommaNeededToAppend) {
                updateSQL = updateSQL + ", ";
            }
            updateSQL = updateSQL + "traitTypeWeightId = ?";
            updateValuesList.add(traitTypeWeightId);
        }
        updateSQL = updateSQL + " WHERE traitId = ?";
        updateValuesList.add(entity.getTraitId());
        int results = jdbcTemplate.update(
                updateSQL,
                updateValuesList.toArray()
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
