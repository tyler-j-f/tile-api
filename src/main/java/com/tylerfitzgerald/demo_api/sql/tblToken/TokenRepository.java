package com.tylerfitzgerald.demo_api.sql.tblToken;

import com.tylerfitzgerald.demo_api.sql.AbstractRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class TokenRepository extends AbstractRepository<TokenDTO> {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper beanPropertyRowMapper;

  public static final String READ_SQL = "SELECT * FROM " + TokenTable.TABLE_NAME;
  // CRUD SQL
  public static final String CREATE_SQL =
      "INSERT INTO " + TokenTable.TABLE_NAME + " VALUES (null, ?, ?, ?, ?, ?, ?)";
  public static final String READ_BY_ID_SQL =
      "SELECT * FROM " + TokenTable.TABLE_NAME + " WHERE tokenId = ?";
  public static final String UPDATE_BASE_SQL = "UPDATE " + TokenTable.TABLE_NAME + " set ";
  public static final String UPDATE_SQL =
      "UPDATE "
          + TokenTable.TABLE_NAME
          + " set saleId = ?, name = ?, description = ?, externalUrl = ?, imageUrl = ? WHERE tokenId = ?";
  public static final String DELETE_BY_ID_SQL =
      "DELETE FROM " + TokenTable.TABLE_NAME + " WHERE tokenId = ?";

  public TokenRepository(JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    super(jdbcTemplate, TokenTable.TABLE_NAME);
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  @Override
  public TokenDTO create(TokenDTO entity) {
    if (doesTokenIdExist(entity)) {
      return null;
    }
    int results =
        jdbcTemplate.update(
            CREATE_SQL,
            entity.getTokenId(),
            entity.getSaleId(),
            entity.getName(),
            entity.getDescription(),
            entity.getExternalUrl(),
            entity.getImageUrl());
    if (results != 1) {
      return null;
    }
    return readById(entity.getTokenId());
  }

  @Override
  public List<TokenDTO> read() {
    Stream<TokenDTO> stream = null;
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
  public TokenDTO readById(Long tokenId) {
    Stream<TokenDTO> stream = null;
    try {
      if (tokenId == 0) {
        // TokenId starts at index 1
        return null;
      }
      stream = jdbcTemplate.queryForStream(READ_BY_ID_SQL, beanPropertyRowMapper, tokenId);
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
  public TokenDTO update(TokenDTO entity) {
    if (!doesTokenIdExist(entity)) {
      return null;
    }
    List<Object> updateValuesList = new ArrayList<>();
    String updateSQL = UPDATE_BASE_SQL;
    // saleId
    Long saleId = entity.getSaleId();
    boolean shouldUpdateSaleId = saleId != null;
    boolean isCommaNeededToAppend = false;
    if (shouldUpdateSaleId) {
      updateSQL = updateSQL + "saleId = ?";
      updateValuesList.add(saleId);
      isCommaNeededToAppend = true;
    }
    // name
    String name = entity.getName();
    boolean shouldUpdateName = name != null;
    if (shouldUpdateName) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "name = ?";
      updateValuesList.add(name);
      isCommaNeededToAppend = true;
    }
    // description
    String description = entity.getDescription();
    boolean shouldUpdateDescription = description != null;
    if (shouldUpdateDescription) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "description = ?";
      updateValuesList.add(description);
      isCommaNeededToAppend = true;
    }
    // externalUrl
    String externalUrl = entity.getExternalUrl();
    boolean shouldUpdateExternalUrl = externalUrl != null;
    if (shouldUpdateExternalUrl) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "externalUrl = ?";
      updateValuesList.add(externalUrl);
      isCommaNeededToAppend = true;
    }
    // imageUrl
    String imageUrl = entity.getImageUrl();
    boolean shouldUpdateImageUrl = imageUrl != null;
    if (shouldUpdateImageUrl) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "imageUrl = ?";
      updateValuesList.add(imageUrl);
      isCommaNeededToAppend = true;
    }
    Long tokenId = entity.getTokenId();
    updateValuesList.add(tokenId);
    updateSQL = updateSQL + " WHERE tokenId = ?";
    int results = jdbcTemplate.update(updateSQL, updateValuesList.toArray());
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
    jdbcTemplate.update(DELETE_BY_ID_SQL, entity.getTokenId());
    return !doesTokenIdExist(entity);
  }

  private boolean doesTokenIdExist(TokenDTO entity) {
    return readById(entity.getTokenId()) != null;
  }
}
