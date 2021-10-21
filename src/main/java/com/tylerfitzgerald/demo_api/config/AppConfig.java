package com.tylerfitzgerald.demo_api.config;

import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import com.tylerfitzgerald.demo_api.token.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.TokenTable;
import com.tylerfitzgerald.demo_api.token.traits.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.TraitTypesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class AppConfig {

    @Autowired
    private EnvConfig appConfig;

    private final JdbcTemplate jdbcTemplate;

    public AppConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public TokenRepository tokenRepository() {
        return new TokenRepository(jdbcTemplate);
    }

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(appConfig.getAlchemyURI()));
    }

    @Bean
    public TokenTable tokenTable() {
        return new TokenTable(jdbcTemplate);
    }

    @Bean
    public MintEventRetriever mintEventRetriever() {
        return new MintEventRetriever();
    }

    @Bean
    public TraitTypesTable traitTypesTable() {
        return new TraitTypesTable(jdbcTemplate);
    }
    @Bean
    public TraitTypeRepository traitTypeRepository() {
        return new TraitTypeRepository(jdbcTemplate);
    }
}
