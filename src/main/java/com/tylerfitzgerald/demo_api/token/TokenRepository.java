package com.tylerfitzgerald.demo_api.token;

import com.tylerfitzgerald.demo_api.RepositoryInterface;

import java.util.List;

public class TokenRepository implements RepositoryInterface<TokenDTO, Long> {

    @Override
    public List<TokenDTO> read() {
        return null;
    }

    @Override
    public TokenDTO readById(Long id) {
        return null;
    }

    @Override
    public TokenDTO create(TokenDTO entity) {
        return null;
    }

    @Override
    public TokenDTO update(TokenDTO entity) {
        return null;
    }

    @Override
    public TokenDTO delete(TokenDTO entity) {
        return null;
    }
}
