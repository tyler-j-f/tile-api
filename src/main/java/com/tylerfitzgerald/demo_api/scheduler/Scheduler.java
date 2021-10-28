package com.tylerfitzgerald.demo_api.scheduler;

import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.events.MintEvent;
import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class Scheduler {

  @Autowired private TokenRepository tokenRepository;

  @Autowired private MintEventRetriever mintEventRetriever;

  @Autowired private EnvConfig appConfig;

  @Autowired private TraitsConfig traitsConfig;

  @Autowired private TokenInitializer tokenInitializer;

  @Autowired private TokenRetriever tokenRetriever;
}
