package com.tylerfitzgerald.demo_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerfitzgerald.demo_api.config.TileNftConfig;
import com.tylerfitzgerald.demo_api.token.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class APIController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private Web3j web3j;

    @Autowired
    private TileNftConfig appConfig;

    public APIController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
        Creature creature = new Creature(
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
        Creature creature = new Creature(
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
        Web3j web3 = Web3j.build(new HttpService(appConfig.getAlchemyURI()));  // defaults to http://localhost:8545/
        BigInteger currentBlockNumber = web3.ethBlockNumber().sendAsync().get().getBlockNumber();
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(currentBlockNumber.subtract(new BigInteger("5760"))),
                DefaultBlockParameter.valueOf(currentBlockNumber), appConfig.getNftFactoryContractAddress()
        );
        List<EthLog.LogResult> logs = web3.ethGetLogs(filter).sendAsync().get().getLogs();
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

    @GetMapping("/api/getTblTokens")
    public String getTblTokens() {
//        String sql = "CREATE TABLE pet (name VARCHAR(20), owner VARCHAR(20))";
//        this.jdbcTemplate.execute(sql);
//        String sql2 = "INSERT INTO pet VALUES ('Garfield-Dog', 'Ronald')";
//        this.jdbcTemplate.execute(sql2);
        String sql = "SELECT * FROM token";
        Stream<TokenDTO> stream = this.jdbcTemplate.queryForStream(sql, new BeanPropertyRowMapper(TokenDTO.class));
        return stream.collect(Collectors.toList()).toString();
    }

    @GetMapping("/api/createSqlTables")
    public String createSqlTables() {
        String sql = "CREATE TABLE token(id int NOT NULL AUTO_INCREMENT, tokenId int, saleId int, PRIMARY KEY (id))";
        this.jdbcTemplate.execute(sql);
        String sql2 = "INSERT INTO token VALUES (null , 1, 2)";
        this.jdbcTemplate.execute(sql2);
        String sql3 = "INSERT INTO token VALUES (null , 2, 5)";
        this.jdbcTemplate.execute(sql3);
        return "hahaha";
    }

    @GetMapping("/api/dropSqlTables")
    public String dropSqlTables() {
        String sql = "DROP TABLE token";
        this.jdbcTemplate.execute(sql);
        return "hahaha";
    }

}
