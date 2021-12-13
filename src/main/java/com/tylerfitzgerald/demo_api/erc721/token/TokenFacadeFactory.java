package com.tylerfitzgerald.demo_api.erc721.token;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenFacadeFactory implements FactoryBean<TokenFacade> {

  @Autowired private TokenFacade tokenFacade;

  @Override
  public TokenFacade getObject() {
    return tokenFacade;
  }

  @Override
  public Class<?> getObjectType() {
    return TokenFacade.class;
  }
}
