package com.tylerfitzgerald.demo_api.sql;

import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenRepositoryTest {

  private JdbcTemplate jdbcTemplate;
  private BeanPropertyRowMapper beanPropertyRowMapper;
  // Value set 1
  private static Long ID = 1L;
  private static Long TOKEN_ID = 2L;
  private static Long SALE_ID = 3L;
  private static String NAME = "STRING_A";
  private static String DESCRIPTION = "STRING_B";
  private static String EXTERNAL_URL = "STRING_C";
  private static String IMAGE_URL = "STRING_D";
  // Value set 2
  private static Long ID_2 = 1L;
  private static Long TOKEN_ID_2 = 2L;
  private static Long SALE_ID_2 = 3L;
  private static String NAME_2 = "STRING_A";
  private static String DESCRIPTION_2 = "STRING_B";
  private static String EXTERNAL_URL_2 = "STRING_C";
  private static String IMAGE_URL_2 = "STRING_D";

  @BeforeEach
  public void setup() {
    this.jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(TokenDTO.class);
  }

  @Test
  void testConstructor() {
    assertThat(new TokenRepository(jdbcTemplate, beanPropertyRowMapper))
        .isInstanceOf(TokenRepository.class);
  }

  @Test
  void testCreateNonExisting() {
    TokenDTO tokenDTO =
        TokenDTO.builder()
            .id(ID)
            .tokenId(TOKEN_ID)
            .saleId(SALE_ID)
            .name(NAME)
            .description(DESCRIPTION)
            .externalUrl(EXTERNAL_URL)
            .imageUrl(IMAGE_URL)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID))
        .thenReturn(Stream.empty(), Stream.of(tokenDTO));
    Mockito.when(
            jdbcTemplate.update(
                TokenRepository.CREATE_SQL,
                TOKEN_ID,
                SALE_ID,
                NAME,
                DESCRIPTION,
                EXTERNAL_URL,
                IMAGE_URL))
        .thenReturn(1);
    TokenDTO tokenDTOResult =
        new TokenRepository(jdbcTemplate, beanPropertyRowMapper).create(tokenDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            TokenRepository.CREATE_SQL,
            TOKEN_ID,
            SALE_ID,
            NAME,
            DESCRIPTION,
            EXTERNAL_URL,
            IMAGE_URL);
    assertThat(tokenDTOResult).isEqualTo(tokenDTO);
  }

  @Test
  void testCreateExisting() {
    TokenDTO tokenDTO =
        TokenDTO.builder()
            .id(ID)
            .tokenId(TOKEN_ID)
            .saleId(SALE_ID)
            .name(NAME)
            .description(DESCRIPTION)
            .externalUrl(EXTERNAL_URL)
            .imageUrl(IMAGE_URL)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID))
        .thenReturn(Stream.of(tokenDTO));
    TokenDTO traitTypeWeightDTOResult =
        new TokenRepository(jdbcTemplate, beanPropertyRowMapper).create(tokenDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            TokenRepository.CREATE_SQL,
            TOKEN_ID,
            SALE_ID,
            NAME,
            DESCRIPTION,
            EXTERNAL_URL,
            IMAGE_URL);
    assertThat(traitTypeWeightDTOResult).isEqualTo(null);
  }

  @Test
  void testRead() {
    TokenDTO tokenDTO =
        TokenDTO.builder()
            .id(ID)
            .tokenId(TOKEN_ID)
            .saleId(SALE_ID)
            .name(NAME)
            .description(DESCRIPTION)
            .externalUrl(EXTERNAL_URL)
            .imageUrl(IMAGE_URL)
            .build();
    TokenDTO tokenDTO2 =
        TokenDTO.builder()
            .id(ID_2)
            .tokenId(TOKEN_ID_2)
            .saleId(SALE_ID_2)
            .name(NAME_2)
            .description(DESCRIPTION_2)
            .externalUrl(EXTERNAL_URL_2)
            .imageUrl(IMAGE_URL_2)
            .build();
    Mockito.when(jdbcTemplate.queryForStream(TokenRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.of(tokenDTO, tokenDTO2));
    List<TokenDTO> tokens = new TokenRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TokenRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(tokens.get(0)).isEqualTo(tokenDTO);
    assertThat(tokens.get(1)).isEqualTo(tokenDTO2);
  }

  @Test
  void testReadEmptyTable() {
    Mockito.when(jdbcTemplate.queryForStream(TokenRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.empty());
    List<TokenDTO> tokens = new TokenRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TokenRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(tokens.isEmpty()).isEqualTo(true);
  }

  @Test
  void testReadExistingById() {
    TokenDTO tokenDTO =
        TokenDTO.builder()
            .id(ID)
            .tokenId(TOKEN_ID)
            .saleId(SALE_ID)
            .name(NAME)
            .description(DESCRIPTION)
            .externalUrl(EXTERNAL_URL)
            .imageUrl(IMAGE_URL)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID))
        .thenReturn(Stream.of(tokenDTO));
    TokenDTO tokenDTOResult = new TokenRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID);
    assertThat(tokenDTOResult).isEqualTo(tokenDTO);
  }

  @Test
  void testReadNonExistingById() {
    Mockito.when(
            jdbcTemplate.queryForStream(
                TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID))
        .thenReturn(Stream.empty());
    TokenDTO tokenDTOResult =
        new TokenRepository(jdbcTemplate, beanPropertyRowMapper).readById(TOKEN_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID);
    assertThat(tokenDTOResult).isEqualTo(null);
  }

  @Test
  void testUpdateExistingEntry() {
    TokenDTO tokenDTO =
        TokenDTO.builder()
            .id(ID)
            .tokenId(TOKEN_ID)
            .saleId(SALE_ID)
            .name(NAME)
            .description(DESCRIPTION)
            .externalUrl(EXTERNAL_URL)
            .imageUrl(IMAGE_URL)
            .build();
    // tokenDTO2 Will have the same id and token id as tokenDTO
    TokenDTO tokenDTO2 =
        TokenDTO.builder()
            .id(ID_2)
            .tokenId(TOKEN_ID_2)
            .saleId(SALE_ID_2)
            .name(NAME_2)
            .description(DESCRIPTION_2)
            .externalUrl(EXTERNAL_URL_2)
            .imageUrl(IMAGE_URL_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID))
        .thenReturn(Stream.of(tokenDTO), Stream.of(tokenDTO2));
    Mockito.when(
            jdbcTemplate.update(
                TokenRepository.UPDATE_SQL,
                SALE_ID,
                NAME,
                DESCRIPTION,
                EXTERNAL_URL,
                IMAGE_URL,
                TOKEN_ID))
        .thenReturn(1);
    TokenDTO tokenDTOResults =
        new TokenRepository(jdbcTemplate, beanPropertyRowMapper).update(tokenDTO2);
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            TokenRepository.UPDATE_SQL,
            SALE_ID_2,
            NAME_2,
            DESCRIPTION_2,
            EXTERNAL_URL_2,
            IMAGE_URL_2,
            TOKEN_ID);
    assertThat(tokenDTOResults).isEqualTo(tokenDTO2);
  }

  @Test
  void testUpdateNonExistingEntry() {
    TokenDTO tokenDTO =
        TokenDTO.builder()
            .id(ID)
            .tokenId(TOKEN_ID)
            .saleId(SALE_ID)
            .name(NAME)
            .description(DESCRIPTION)
            .externalUrl(EXTERNAL_URL)
            .imageUrl(IMAGE_URL)
            .build();
    // Return a Stream.empty() from the read by id call to imitate a non-existing entry.
    Mockito.when(
            jdbcTemplate.queryForStream(
                TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID))
        .thenReturn(Stream.empty());
    TokenDTO tokenDTOResults =
        new TokenRepository(jdbcTemplate, beanPropertyRowMapper).update(tokenDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            TokenRepository.UPDATE_SQL,
            SALE_ID_2,
            NAME_2,
            DESCRIPTION_2,
            EXTERNAL_URL_2,
            IMAGE_URL_2,
            TOKEN_ID);
    assertThat(tokenDTOResults).isEqualTo(null);
  }

  @Test
  void testDeleteExistingEntry() {
    TokenDTO tokenDTO =
        TokenDTO.builder()
            .id(ID)
            .tokenId(TOKEN_ID)
            .saleId(SALE_ID)
            .name(NAME)
            .description(DESCRIPTION)
            .externalUrl(EXTERNAL_URL)
            .imageUrl(IMAGE_URL)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID))
        .thenReturn(Stream.of(tokenDTO), Stream.empty());
    boolean isDeletedSuccessfully =
        new TokenRepository(jdbcTemplate, beanPropertyRowMapper).delete(tokenDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(2))
        .queryForStream(TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(TokenRepository.DELETE_BY_ID_SQL, TOKEN_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(true);
  }

  @Test
  void testDeleteNonExistingEntry() {
    TokenDTO tokenDTO =
        TokenDTO.builder()
            .id(ID)
            .tokenId(TOKEN_ID)
            .saleId(SALE_ID)
            .name(NAME)
            .description(DESCRIPTION)
            .externalUrl(EXTERNAL_URL)
            .imageUrl(IMAGE_URL)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID))
        .thenReturn(Stream.empty());
    boolean isDeletedSuccessfully =
        new TokenRepository(jdbcTemplate, beanPropertyRowMapper).delete(tokenDTO);
    // Read by id is called twice. Once at the start of the delete method and once at the end of the
    // delete method.
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TokenRepository.READ_BY_ID_SQL, beanPropertyRowMapper, TOKEN_ID);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(TokenRepository.DELETE_BY_ID_SQL, TOKEN_ID);
    assertThat(isDeletedSuccessfully).isEqualTo(false);
  }
}
