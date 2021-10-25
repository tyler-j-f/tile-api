package com.tylerfitzgerald.demo_api.token.traits;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TraitsRepositoryTest {

    private static JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setup() {
        TraitsRepositoryTest.jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    }

    @Test
    void testConstructor() {
        assertThat(new TraitsRepository(jdbcTemplate, new BeanPropertyRowMapper(TraitDTO.class))).isInstanceOf(TraitsRepository.class);
    }

    @Test
    void testRead() {
        BeanPropertyRowMapper rowMapper = new BeanPropertyRowMapper(TraitDTO.class);
        new TraitsRepository(jdbcTemplate, rowMapper).read();
        Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForStream(
                TraitsRepository.READ_SQL,
                rowMapper
        );
    }

}
