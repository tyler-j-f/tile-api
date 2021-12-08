package com.tylerfitzgerald.demo_api.sql.repositories;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tbls.WeightedTraitTypesTable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightedTraitTypeRepository extends AbstractRepository<WeightedTraitTypeDTO> {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper beanPropertyRowMapper;

  public static final String READ_SQL = "SELECT * FROM " + WeightedTraitTypesTable.TABLE_NAME;
  // CRUD SQL
  public static final String CREATE_SQL =
      "INSERT INTO " + WeightedTraitTypesTable.TABLE_NAME + " VALUES (null, ?, ?, ?)";
  public static final String READ_BY_ID_SQL =
      "SELECT * FROM " + WeightedTraitTypesTable.TABLE_NAME + " WHERE traitTypeId = ?";
  public static final String UPDATE_SQL =
      "UPDATE "
          + WeightedTraitTypesTable.TABLE_NAME
          + " set traitTypeName = ?, description = ? WHERE traitTypeId = ?";
  public static final String UPDATE_BASE_SQL =
      "UPDATE " + WeightedTraitTypesTable.TABLE_NAME + " set ";
  public static final String DELETE_BY_ID_SQL =
      "DELETE FROM " + WeightedTraitTypesTable.TABLE_NAME + " WHERE traitTypeId = ?";

  public WeightedTraitTypeRepository(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    super(jdbcTemplate, WeightedTraitTypesTable.TABLE_NAME);
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  @Override
  public WeightedTraitTypeDTO create(WeightedTraitTypeDTO entity) {
    if (doesTraitTypeIdExist(entity)) {
      return null;
    }
    int results =
        jdbcTemplate.update(
            CREATE_SQL,
            entity.getTraitTypeId(),
            entity.getTraitTypeName(),
            entity.getDescription());
    if (results != 1) {
      return null;
    }
    return readById(entity.getTraitTypeId());
  }

  @Override
  public List<WeightedTraitTypeDTO> read() {
    Stream<WeightedTraitTypeDTO> stream = null;
    try {
      stream = jdbcTemplate.queryForStream(READ_SQL, beanPropertyRowMapper);
      return stream.collect(Collectors.toList());
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  @Override
  public WeightedTraitTypeDTO readById(Long traitId) {
    Stream<WeightedTraitTypeDTO> stream = null;
    try {
      stream = jdbcTemplate.queryForStream(READ_BY_ID_SQL, beanPropertyRowMapper, traitId);
      List<WeightedTraitTypeDTO> traitTypes = stream.collect(Collectors.toList());
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
  /** NOTE: Only the sale id can be updated */
  public WeightedTraitTypeDTO update(WeightedTraitTypeDTO entity) {
    if (!doesTraitTypeIdExist(entity)) {
      return null;
    }
    List<Object> updateValuesList = new ArrayList<>();
    String updateSQL = UPDATE_BASE_SQL;
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
      // There's nothing to update from the inputted WeightedTraitTypeDTO
      return null;
    }
    Long traitTypeId = entity.getTraitTypeId();
    updateValuesList.add(traitTypeId);
    updateSQL = updateSQL + " WHERE traitTypeId = ?";
    int results = jdbcTemplate.update(updateSQL, updateValuesList.toArray());
    if (results < 1) {
      return null;
    }
    return readById(traitTypeId);
  }

  @Override
  public boolean delete(WeightedTraitTypeDTO entity) {
    if (!doesTraitTypeIdExist(entity)) {
      return false;
    }
    jdbcTemplate.update(DELETE_BY_ID_SQL, entity.getTraitTypeId());
    return !doesTraitTypeIdExist(entity);
  }

  private boolean doesTraitTypeIdExist(WeightedTraitTypeDTO entity) {
    return readById(entity.getTraitTypeId()) != null;
  }
}
