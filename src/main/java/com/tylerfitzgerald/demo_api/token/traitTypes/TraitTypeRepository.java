package com.tylerfitzgerald.demo_api.token.traitTypes;

import com.tylerfitzgerald.demo_api.sql.RepositoryInterface;
import com.tylerfitzgerald.demo_api.sql.TraitTypesTable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TraitTypeRepository implements RepositoryInterface<TraitTypeDTO, Long> {

    private final JdbcTemplate jdbcTemplate;

    private static final String READ_SQL          = "SELECT * FROM " + TraitTypesTable.TABLE_NAME;
    // CRUD SQL
    private static final String CREATE_SQL        = "INSERT INTO " + TraitTypesTable.TABLE_NAME + " VALUES (null, ?, ?, ?)";
    private static final String READ_BY_ID_SQL    = "SELECT * FROM " + TraitTypesTable.TABLE_NAME + " WHERE traitTypeId = ?";
    private static final String UPDATE_BASE_SQL        = "UPDATE " + TraitTypesTable.TABLE_NAME + " set ";
    private static final String DELETE_BY_ID_SQL  = "DELETE FROM " + TraitTypesTable.TABLE_NAME + " WHERE traitTypeId = ?";

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
    public TraitTypeDTO readById(Long traitId) {
        Stream<TraitTypeDTO> stream = null;
        try {
            if (traitId == 0) {
                // TokenId starts at index 1
                return null;
            }
            stream = jdbcTemplate.queryForStream(
                    READ_BY_ID_SQL,
                    new BeanPropertyRowMapper(TraitTypeDTO.class),
                    traitId
            );
            List<TraitTypeDTO> traitTypes = stream.collect(Collectors.toList());
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
    public TraitTypeDTO create(TraitTypeDTO entity) {
        if (doesTraitTypeIdExist(entity)) {
            return null;
        }
        int results = jdbcTemplate.update(
                CREATE_SQL,
                entity.getTraitTypeId(),
                entity.getTraitTypeName(),
                entity.getDescription()
        );
        if (results != 1) {
            return null;
        }
        return readById(entity.getTraitTypeId());
    }

    @Override
    /**
     * NOTE: Only the sale id can be updated
     */
    public TraitTypeDTO update(TraitTypeDTO entity) {
        if (!doesTraitTypeIdExist(entity)) {
            return null;
        }
        List<Object> updateValuesList = new ArrayList<>();
        String updateSQL  = UPDATE_BASE_SQL;
        String traitTypeName = entity.getTraitTypeName();
        boolean shouldUpdateTraitTypeName = traitTypeName != null;
        boolean isCommaNeededToAppend = false;
        if (shouldUpdateTraitTypeName) {
            updateSQL = updateSQL + "traitTypeName = ?";
            updateValuesList.add(traitTypeName);
            isCommaNeededToAppend = true;
        }
        String description = entity.getDescription();
        boolean shouldUpdateDescription = description != null;
        if (shouldUpdateDescription) {
            if (isCommaNeededToAppend) {
                updateSQL = updateSQL + ", ";
            }
            updateSQL = updateSQL + "description = ?";
            updateValuesList.add(description);
        }
        if (!shouldUpdateTraitTypeName && !shouldUpdateDescription) {
            // There's nothing to update from the inputted TraitTypeDTO
            return null;
        }
        Long traitTypeId = entity.getTraitTypeId();
        updateValuesList.add(traitTypeId);
        updateSQL = updateSQL + " WHERE traitTypeId = ?";
        int results = jdbcTemplate.update(
                updateSQL,
                updateValuesList.toArray()
        );
        if (results < 1) {
            return null;
        }
        return readById(traitTypeId);
    }

    @Override
    public boolean delete(TraitTypeDTO entity) {
        if (!doesTraitTypeIdExist(entity)) {
            return false;
        }
        jdbcTemplate.update(
                DELETE_BY_ID_SQL,
                entity.getTraitTypeId()
        );
        return !doesTraitTypeIdExist(entity);
    }

    private boolean doesTraitTypeIdExist(TraitTypeDTO entity) {
        return readById(entity.getTraitTypeId()) != null;
    }

}
