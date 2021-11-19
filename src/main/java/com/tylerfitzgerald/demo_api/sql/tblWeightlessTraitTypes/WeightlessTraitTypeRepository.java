package com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes;

import com.tylerfitzgerald.demo_api.sql.RepositoryInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightlessTraitTypeRepository
    implements RepositoryInterface<WeightlessTraitTypeDTO, Long> {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper beanPropertyRowMapper;

  public static final String READ_SQL = "SELECT * FROM " + WeightlessTraitTypesTable.TABLE_NAME;
  // CRUD SQL
  public static final String CREATE_SQL =
      "INSERT INTO " + WeightlessTraitTypesTable.TABLE_NAME + " VALUES (null, ?, ?, ?)";
  public static final String READ_BY_ID_SQL =
      "SELECT * FROM " + WeightlessTraitTypesTable.TABLE_NAME + " WHERE weightlessTraitTypeId = ?";
  public static final String UPDATE_SQL =
      "UPDATE "
          + WeightlessTraitTypesTable.TABLE_NAME
          + " set weightlessTraitTypeName = ?, description = ? WHERE weightlessTraitTypeId = ?";
  public static final String UPDATE_BASE_SQL =
      "UPDATE " + WeightlessTraitTypesTable.TABLE_NAME + " set ";
  public static final String DELETE_BY_ID_SQL =
      "DELETE FROM " + WeightlessTraitTypesTable.TABLE_NAME + " WHERE weightlessTraitTypeId = ?";

  public WeightlessTraitTypeRepository(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  @Override
  public WeightlessTraitTypeDTO create(WeightlessTraitTypeDTO entity) {
    if (doesWeightlessTraitTypeExist(entity)) {
      return null;
    }
    int results =
        jdbcTemplate.update(
            CREATE_SQL,
            entity.getWeightlessTraitTypeId(),
            entity.getWeightlessTraitTypeName(),
            entity.getDescription());
    if (results != 1) {
      return null;
    }
    return readById(entity.getWeightlessTraitTypeId());
  }

  @Override
  public List<WeightlessTraitTypeDTO> read() {
    Stream<WeightlessTraitTypeDTO> stream = null;
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
  public WeightlessTraitTypeDTO readById(Long weightlessTraitTypeId) {
    Stream<WeightlessTraitTypeDTO> stream = null;
    try {
      stream =
          jdbcTemplate.queryForStream(READ_BY_ID_SQL, beanPropertyRowMapper, weightlessTraitTypeId);
      List<WeightlessTraitTypeDTO> weightlessTraits = stream.collect(Collectors.toList());
      if (weightlessTraits.size() == 0) {
        return null;
      }
      return weightlessTraits.get(0);
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  @Override
  /** NOTE: Only the sale id can be updated */
  public WeightlessTraitTypeDTO update(WeightlessTraitTypeDTO entity) {
    if (!doesWeightlessTraitTypeExist(entity)) {
      return null;
    }
    List<Object> updateValuesList = new ArrayList<>();
    String updateSQL = UPDATE_BASE_SQL;
    String weightlessTraitTypeName = entity.getWeightlessTraitTypeName();
    boolean shouldUpdateWeightlessTraitTypeName = weightlessTraitTypeName != null;
    boolean isCommaNeededToAppend = false;
    if (shouldUpdateWeightlessTraitTypeName) {
      updateSQL = updateSQL + "weightlessTraitTypeName = ?";
      updateValuesList.add(weightlessTraitTypeName);
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
    if (!shouldUpdateWeightlessTraitTypeName && !shouldUpdateDescription) {
      // There's nothing to update from the inputted WeightlessTraitTypeDTO
      return null;
    }
    Long weightlessTraitTypeId = entity.getWeightlessTraitTypeId();
    updateValuesList.add(weightlessTraitTypeId);
    updateSQL = updateSQL + " WHERE weightlessTraitTypeId = ?";
    int results = jdbcTemplate.update(updateSQL, updateValuesList.toArray());
    if (results < 1) {
      return null;
    }
    return readById(weightlessTraitTypeId);
  }

  @Override
  public boolean delete(WeightlessTraitTypeDTO entity) {
    if (!doesWeightlessTraitTypeExist(entity)) {
      return false;
    }
    jdbcTemplate.update(DELETE_BY_ID_SQL, entity.getWeightlessTraitTypeId());
    return !doesWeightlessTraitTypeExist(entity);
  }

  private boolean doesWeightlessTraitTypeExist(WeightlessTraitTypeDTO entity) {
    return readById(entity.getWeightlessTraitTypeId()) != null;
  }
}
