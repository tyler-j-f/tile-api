package io.tilenft.sql.repositories;

import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.tbls.WeightedTraitTypeWeightsTable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightedTraitTypeWeightRepository
    extends AbstractRepository<WeightedTraitTypeWeightDTO> {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper beanPropertyRowMapper;

  public static final String READ_SQL = "SELECT * FROM " + WeightedTraitTypeWeightsTable.TABLE_NAME;
  // CRUD SQL
  public static final String CREATE_SQL =
      "INSERT INTO " + WeightedTraitTypeWeightsTable.TABLE_NAME + " VALUES (null, ?, ?, ?, ?, ?)";
  public static final String READ_BY_ID_SQL =
      "SELECT * FROM " + WeightedTraitTypeWeightsTable.TABLE_NAME + " WHERE traitTypeWeightId = ?";
  public static final String UPDATE_SQL =
      "UPDATE "
          + WeightedTraitTypeWeightsTable.TABLE_NAME
          + " set traitTypeId = ?, likelihood = ?, value = ?, displayTypeValue = ? WHERE traitTypeWeightId = ?";
  public static final String UPDATE_BASE_SQL =
      "UPDATE " + WeightedTraitTypeWeightsTable.TABLE_NAME + " set ";
  public static final String DELETE_BY_ID_SQL =
      "DELETE FROM " + WeightedTraitTypeWeightsTable.TABLE_NAME + " WHERE traitTypeWeightId = ?";

  public WeightedTraitTypeWeightRepository(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    super(jdbcTemplate, WeightedTraitTypeWeightsTable.TABLE_NAME);
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  @Override
  public List<WeightedTraitTypeWeightDTO> read() {
    Stream<WeightedTraitTypeWeightDTO> stream = null;
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
  public WeightedTraitTypeWeightDTO readById(Long traitTypeWeightId) {
    Stream<WeightedTraitTypeWeightDTO> stream = null;
    try {
      if (traitTypeWeightId == 0) {
        // TokenId starts at index 1
        return null;
      }
      stream =
          jdbcTemplate.queryForStream(READ_BY_ID_SQL, beanPropertyRowMapper, traitTypeWeightId);
      List<WeightedTraitTypeWeightDTO> traitTypeWeights = stream.collect(Collectors.toList());
      if (traitTypeWeights.size() == 0) {
        return null;
      }
      return traitTypeWeights.get(0);
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  @Override
  public WeightedTraitTypeWeightDTO create(WeightedTraitTypeWeightDTO entity) {
    if (doesTraitTypeWeightIdExist(entity)) {
      return null;
    }
    int results =
        jdbcTemplate.update(
            CREATE_SQL,
            entity.getTraitTypeWeightId(),
            entity.getTraitTypeId(),
            entity.getLikelihood(),
            entity.getValue(),
            entity.getDisplayTypeValue());
    if (results != 1) {
      return null;
    }
    return readById(entity.getTraitTypeWeightId());
  }

  @Override
  /** NOTE: Only the sale id can be updated */
  public WeightedTraitTypeWeightDTO update(WeightedTraitTypeWeightDTO entity) {
    if (!doesTraitTypeWeightIdExist(entity)) {
      return null;
    }
    List<Object> updateValuesList = new ArrayList<>();
    String updateSQL = UPDATE_BASE_SQL;
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
    Long likelihood = entity.getLikelihood();
    boolean shouldUpdateLikelihood = likelihood != null;
    if (shouldUpdateLikelihood) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "likelihood = ?";
      updateValuesList.add(likelihood);
      isCommaNeededToAppend = true;
    }
    // value
    String value = entity.getValue();
    boolean shouldUpdateValue = value != null;
    if (shouldUpdateValue) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "value = ?";
      updateValuesList.add(value);
      isCommaNeededToAppend = true;
    }
    // displayTypeValue
    String displayTypeValue = entity.getDisplayTypeValue();
    boolean shouldUpdateDisplayTypeValue = displayTypeValue != null;
    if (shouldUpdateDisplayTypeValue) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "displayTypeValue = ?";
      updateValuesList.add(displayTypeValue);
    }
    if (!shouldUpdateTraitTypeId
        && !shouldUpdateLikelihood
        && !shouldUpdateValue
        && !shouldUpdateDisplayTypeValue) {
      // There's nothing to update from the inputted WeightedTraitTypeDTO
      return null;
    }
    Long traitTypeWeightId = entity.getTraitTypeWeightId();
    updateValuesList.add(traitTypeWeightId);
    updateSQL = updateSQL + " WHERE traitTypeWeightId = ?";
    int results = jdbcTemplate.update(updateSQL, updateValuesList.toArray());
    if (results < 1) {
      return null;
    }
    return readById(traitTypeWeightId);
  }

  @Override
  public boolean delete(WeightedTraitTypeWeightDTO entity) {
    if (!doesTraitTypeWeightIdExist(entity)) {
      return false;
    }
    jdbcTemplate.update(DELETE_BY_ID_SQL, entity.getTraitTypeWeightId());
    return !doesTraitTypeWeightIdExist(entity);
  }

  private boolean doesTraitTypeWeightIdExist(WeightedTraitTypeWeightDTO entity) {
    return readById(entity.getTraitTypeWeightId()) != null;
  }
}
