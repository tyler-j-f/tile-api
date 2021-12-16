package io.tileNft.sql.repositories;

import io.tileNft.sql.dtos.WeightlessTraitDTO;
import io.tileNft.sql.tbls.WeightlessTraitsTable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightlessTraitRepository extends AbstractRepository<WeightlessTraitDTO> {
  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper beanPropertyRowMapper;

  public static final String READ_SQL = "SELECT * FROM " + WeightlessTraitsTable.TABLE_NAME;
  // CRUD SQL
  public static final String CREATE_SQL =
      "INSERT INTO " + WeightlessTraitsTable.TABLE_NAME + " VALUES (null, ?, ?, ?, ?, ?)";
  public static final String READ_BY_ID_SQL =
      "SELECT * FROM " + WeightlessTraitsTable.TABLE_NAME + " WHERE traitId = ?";
  public static final String READ_BY_TOKEN_ID_SQL =
      "SELECT * FROM " + WeightlessTraitsTable.TABLE_NAME + " WHERE tokenId = ?";
  public static final String UPDATE_SQL =
      "UPDATE "
          + WeightlessTraitsTable.TABLE_NAME
          + " set tokenId = ?, traitTypeId = ?, value = ?, displayTypeValue = ? WHERE traitId = ?";
  public static final String UPDATE_BASE_SQL =
      "UPDATE " + WeightlessTraitsTable.TABLE_NAME + " set ";
  public static final String DELETE_BY_ID_SQL =
      "DELETE FROM " + WeightlessTraitsTable.TABLE_NAME + " WHERE traitId = ?";

  public WeightlessTraitRepository(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    super(jdbcTemplate, WeightlessTraitsTable.TABLE_NAME);
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  @Override
  public WeightlessTraitDTO create(WeightlessTraitDTO entity) {
    if (doesWeightlessTraitExist(entity)) {
      return null;
    }
    int results =
        jdbcTemplate.update(
            CREATE_SQL,
            entity.getTraitId(),
            entity.getTokenId(),
            entity.getTraitTypeId(),
            entity.getValue(),
            entity.getDisplayTypeValue());
    if (results != 1) {
      return null;
    }
    return readById(entity.getTraitId());
  }

  @Override
  public List<WeightlessTraitDTO> read() {
    Stream<WeightlessTraitDTO> stream = null;
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
  public WeightlessTraitDTO readById(Long weightlessTraitId) {
    Stream<WeightlessTraitDTO> stream = null;
    try {
      stream =
          jdbcTemplate.queryForStream(READ_BY_ID_SQL, beanPropertyRowMapper, weightlessTraitId);
      List<WeightlessTraitDTO> weightlessTraits = stream.collect(Collectors.toList());
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
  public WeightlessTraitDTO update(WeightlessTraitDTO entity) {
    if (!doesWeightlessTraitExist(entity)) {
      return null;
    }
    List<Object> updateValuesList = new ArrayList<>();
    String updateSQL = UPDATE_BASE_SQL;
    Long tokenId = entity.getTokenId();
    boolean shouldUpdateTokenId = tokenId != null;
    boolean isCommaNeededToAppend = false;
    if (shouldUpdateTokenId) {
      updateSQL = updateSQL + "tokenId = ?";
      updateValuesList.add(tokenId);
      isCommaNeededToAppend = true;
    }
    Long weightlessTraitTypeId = entity.getTraitTypeId();
    boolean shouldUpdateWeightlessTraitTypeId = weightlessTraitTypeId != null;
    if (shouldUpdateWeightlessTraitTypeId) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "traitTypeId = ?";
      updateValuesList.add(weightlessTraitTypeId);
      isCommaNeededToAppend = true;
    }
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
    String displayTypeValue = entity.getDisplayTypeValue();
    boolean shouldUpdateDisplayTypeValue = displayTypeValue != null;
    if (shouldUpdateDisplayTypeValue) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "displayTypeValue = ?";
      updateValuesList.add(displayTypeValue);
    }
    if (!shouldUpdateTokenId
        && !shouldUpdateWeightlessTraitTypeId
        && shouldUpdateValue
        && !shouldUpdateDisplayTypeValue) {
      // There's nothing to update from the inputted WeightlessTraitDTO
      return null;
    }
    Long weightlessTraitId = entity.getTraitId();
    updateValuesList.add(weightlessTraitId);
    updateSQL = updateSQL + " WHERE traitId = ?";
    int results = jdbcTemplate.update(updateSQL, updateValuesList.toArray());
    if (results < 1) {
      return null;
    }
    return readById(weightlessTraitId);
  }

  @Override
  public boolean delete(WeightlessTraitDTO entity) {
    if (!doesWeightlessTraitExist(entity)) {
      return false;
    }
    jdbcTemplate.update(DELETE_BY_ID_SQL, entity.getTraitId());
    return !doesWeightlessTraitExist(entity);
  }

  public List<WeightlessTraitDTO> readByTokenId(Long tokenId) {
    Stream<WeightlessTraitDTO> stream = null;
    try {
      stream = jdbcTemplate.queryForStream(READ_BY_TOKEN_ID_SQL, beanPropertyRowMapper, tokenId);
      List<WeightlessTraitDTO> traits = stream.collect(Collectors.toList());
      if (traits.size() == 0) {
        return null;
      }
      return traits;
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  private boolean doesWeightlessTraitExist(WeightlessTraitDTO entity) {
    return readById(entity.getTraitId()) != null;
  }
}
