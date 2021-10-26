package com.tylerfitzgerald.demo_api.token;

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
}
