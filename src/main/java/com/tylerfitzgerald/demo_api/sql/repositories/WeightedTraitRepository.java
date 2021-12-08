package com.tylerfitzgerald.demo_api.sql.repositories;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tbls.WeightedTraitsTable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class WeightedTraitRepository extends AbstractRepository<WeightedTraitDTO> {

  private final BeanPropertyRowMapper beanPropertyRowMapper;

  // CRUD SQL
  public static final String READ_SQL = "SELECT * FROM " + WeightedTraitsTable.TABLE_NAME;
  public static final String CREATE_SQL =
      "INSERT INTO " + WeightedTraitsTable.TABLE_NAME + " VALUES (null, ?, ?, ?, ?)";
  public static final String READ_BY_ID_SQL =
      "SELECT * FROM " + WeightedTraitsTable.TABLE_NAME + " WHERE traitId = ?";
  public static final String READ_BY_TOKEN_ID_SQL =
      "SELECT * FROM " + WeightedTraitsTable.TABLE_NAME + " WHERE tokenId = ?";
  public static final String UPDATE_SQL_BASE = "UPDATE " + WeightedTraitsTable.TABLE_NAME + " set ";
  public static final String UPDATE_SQL =
      "UPDATE "
          + WeightedTraitsTable.TABLE_NAME
          + " set tokenId = ?, traitTypeId = ?, traitTypeWeightId = ? WHERE traitId = ?";
  public static final String DELETE_BY_ID_SQL =
      "DELETE FROM " + WeightedTraitsTable.TABLE_NAME + " WHERE traitId = ?";

  public WeightedTraitRepository(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    super(jdbcTemplate, WeightedTraitsTable.TABLE_NAME);
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  @Override
  public WeightedTraitDTO create(WeightedTraitDTO entity) {
    if (doesTraitIdExist(entity)) {
      return null;
    }
    int results =
        jdbcTemplate.update(
            CREATE_SQL,
            entity.getTraitId(),
            entity.getTokenId(),
            entity.getTraitTypeId(),
            entity.getTraitTypeWeightId());
    if (results != 1) {
      return null;
    }
    return readById(entity.getTraitId());
  }

  @Override
  public List<WeightedTraitDTO> read() {
    Stream<WeightedTraitDTO> stream = null;
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
  public WeightedTraitDTO readById(Long traitId) {
    Stream<WeightedTraitDTO> stream = null;
    try {
      stream = jdbcTemplate.queryForStream(READ_BY_ID_SQL, beanPropertyRowMapper, traitId);
      List<WeightedTraitDTO> traits = stream.collect(Collectors.toList());
      if (traits.size() == 0) {
        return null;
      }
      return traits.get(0);
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  public List<WeightedTraitDTO> readByTokenId(Long tokenId) {
    Stream<WeightedTraitDTO> stream = null;
    try {
      stream = jdbcTemplate.queryForStream(READ_BY_TOKEN_ID_SQL, beanPropertyRowMapper, tokenId);
      List<WeightedTraitDTO> traits = stream.collect(Collectors.toList());
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

  @Override
  /** NOTE: Only the sale id can be updated */
  public WeightedTraitDTO update(WeightedTraitDTO entity) {
    if (!doesTraitIdExist(entity)) {
      return null;
    }
    List<Object> updateValuesList = new ArrayList<>();
    String updateSQL = UPDATE_SQL_BASE;
    // tokenId
    Long tokenId = entity.getTokenId();
    boolean shouldUpdateTokenId = tokenId != null;
    boolean isCommaNeededToAppend = false;
    if (shouldUpdateTokenId) {
      updateSQL = updateSQL + "tokenId = ?";
      updateValuesList.add(tokenId);
      isCommaNeededToAppend = true;
    }
    // traitTypeId
    Long traitTypeId = entity.getTraitTypeId();
    boolean shouldUpdateTraitTypeId = traitTypeId != null;
    if (shouldUpdateTraitTypeId) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
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
    int results = jdbcTemplate.update(updateSQL, updateValuesList.toArray());
    if (results < 1) {
      return null;
    }
    return readById(entity.getTraitId());
  }

  @Override
  public boolean delete(WeightedTraitDTO entity) {
    if (!doesTraitIdExist(entity)) {
      return false;
    }
    jdbcTemplate.update(DELETE_BY_ID_SQL, entity.getTraitId());
    return !doesTraitIdExist(entity);
  }

  private boolean doesTraitIdExist(WeightedTraitDTO entity) {
    return readById(entity.getTraitId()) != null;
  }
}
