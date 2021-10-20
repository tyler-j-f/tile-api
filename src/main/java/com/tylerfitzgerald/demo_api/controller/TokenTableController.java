package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.token.TokenDTO;
import com.tylerfitzgerald.demo_api.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/tokens"})
public class TokenTableController {

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("getAll")
    public String getAllTokens() {
        return tokenRepository.read().toString();
    }


    @GetMapping("get/{id}")
    public String getToken(@PathVariable Long id) {
        return tokenRepository.readById(id).toString();
    }

    @GetMapping("insert/{tokenId}/{saleId}")
    public String insertToken(@PathVariable Long tokenId, @PathVariable Long saleId) {
        TokenDTO tokenDTO = tokenRepository.create(
                TokenDTO.builder().
                        tokenId(tokenId).
                        saleId(saleId).
                        build()
        );
        if (tokenDTO == null) {
            return "Cannot create tokenId: " + tokenId;
        }
        return tokenDTO.toString();
    }

    /**
     * Update the token's sale id
     */
    @GetMapping("update/{tokenId}/{saleId}")
    public String updateToken(@PathVariable Long tokenId, @PathVariable Long saleId) {
        TokenDTO tokenDTO = tokenRepository.update(
                TokenDTO.builder().
                        tokenId(tokenId).
                        saleId(saleId).
                        build()
        );
        if (tokenDTO == null) {
            return "Cannot update tokenId: " + tokenId;
        }
        return tokenDTO.toString();
    }

    /**
     * Delete the token from the sql table.
     * The token will still exist on the blockchain.
     */
    @GetMapping("delete/{tokenId}")
    public String dropToken(@PathVariable Long tokenId) {
        TokenDTO tokenDTO = tokenRepository.update(
                TokenDTO.builder().
                        tokenId(tokenId).
                        build()
        );
        if (!tokenRepository.delete(tokenDTO)) {
            return "Could not delete tokenId: " + tokenId;
        }
        return "Deleted tokenId: " + tokenId;
    }

}
