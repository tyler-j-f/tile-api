package com.tylerfitzgerald.demo_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.NFT;
import com.tylerfitzgerald.demo_api.token.TokenTable;
import com.tylerfitzgerald.demo_api.traits.DisplayTypeTrait;
import com.tylerfitzgerald.demo_api.events.MintEvent;
import com.tylerfitzgerald.demo_api.traits.Trait;
import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.token.TokenDTO;
import com.tylerfitzgerald.demo_api.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class APIController {

    @Autowired
    private TokenTable tokenTable;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private Web3j web3j;

    @Autowired
    private EnvConfig appConfig;

    @GetMapping("/api/creature/{id}")
    public String getCreatureJSON(@PathVariable String id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Trait> traits = new ArrayList<Trait>(
                Arrays.asList(
                        new Trait("base", "jellyfish"),
                        new Trait("eyes", "big"),
                        new Trait("mouth", "happy"),
                        new Trait("level", "5"),
                        new Trait("stamina", "1.4"),
                        new Trait("personality", "happy"),
                        new DisplayTypeTrait("aqua_power", "10", "boost_number"),
                        new DisplayTypeTrait("stamina_increase", "5", "boost_percentage"),
                        new DisplayTypeTrait("generation", "1", "number")
                )
        );
        NFT creature = new NFT(
                traits,
                "Friendly OpenSea Creature that enjoys long swims in the ocean.",
                "https://example.com/?token_id=0",
                "https://storage.googleapis.com/opensea-prod.appspot.com/creature/0.png",
                "Herbie Starbelly"
        );
        return objectMapper.writeValueAsString(creature);
    }

    @GetMapping("/api/sale/{id}")
    public String getSaleJSON(@PathVariable String id) throws JsonProcessingException {
        int totalSaleOptions = appConfig.getTileCount() * appConfig.getBitsPerTile();
        if (Integer.parseInt(id) > (totalSaleOptions - 1)) {
            // There is only one sale that exists in the factory contract.
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Trait> traits = new ArrayList<Trait>(
                Arrays.asList(
                        new Trait("number_inside", "1")
                )
        );
        NFT creature = new NFT(
                traits,
                "TESTING!!!! Friendly OpenSea Creature that enjoys long swims in the ocean.",
                "https://example.com/?token_id=0",
                "https://storage.googleapis.com/opensea-prod.appspot.com/creature/0.png",
                "Herbie Starbelly"
        );
        return objectMapper.writeValueAsString(creature);
    }

    @GetMapping("/api/test/{id}")
    public String getTestJSON(@PathVariable String id) throws ExecutionException, InterruptedException {
        BigInteger currentBlockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(currentBlockNumber.subtract(new BigInteger("5760"))),
                DefaultBlockParameter.valueOf(currentBlockNumber), appConfig.getNftFactoryContractAddress()
        );
        List<EthLog.LogResult> logs = web3j.ethGetLogs(filter).sendAsync().get().getLogs();
        int size = logs.size();
        if (size == 0) {
            String output = "No events found";
            System.out.println(output);
            return output;
        }
        List<MintEvent> events = new ArrayList<>();
        for (EthLog.LogResult log : logs) {
            List<String> topics = ((Log) log).getTopics();
            if (topics.get(0).equals(appConfig.getMintEventHashSignature())) {
                events.add(
                        MintEvent.builder()
                                .topics(topics)
                                .build()
                );
            }
        }

        System.out.println("events size:  " + events.size());
        System.out.println("event 1: " + events.get(0).toString());
        System.out.println("event 2: " + events.get(1).toString());
        //System.out.println("event 3: " + events.get(2));
        return "END";
    }

    @GetMapping("/api/getAllTokens")
    public String getTblTokens() {
        return tokenRepository.read().toString();
    }


    @GetMapping("/api/getToken/{id}")
    public String getToken(@PathVariable Long id) {
        return tokenRepository.readById(id).toString();
    }

    @GetMapping("/api/insertToken/{tokenId}/{saleId}")
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


    @GetMapping("/api/updateToken/{tokenId}/{saleId}")
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

    @GetMapping("/api/deleteToken/{tokenId}")
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

    @GetMapping("/api/createSqlTables")
    public String createSqlTables() {
        if (tokenTable.delete()) {
            return "Token table created successfully";
        }
        return "Token table failed to create";
    }

    @GetMapping("/api/dropSqlTables")
    public String dropSqlTables() {
       if (tokenTable.delete()) {
            return "Token table deleted successfully";
       }
        return "Token table failed to delete";
    }

}
