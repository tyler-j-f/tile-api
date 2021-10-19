package com.tylerfitzgerald.demo_api.config;

import com.tylerfitzgerald.demo_api.token.TokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SqlConfig {

    private final JdbcTemplate jdbcTemplate;

    public SqlConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public TokenRepository tokenRepository() {
        return new TokenRepository(jdbcTemplate);
    }
}
