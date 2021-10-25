package com.tylerfitzgerald.demo_api.controller;

import com.tylerfitzgerald.demo_api.events.MintEvent;
import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = {"/api/events/mint"})
public class MintEventsController extends BaseController {

    @Autowired
    private MintEventRetriever mintEventRetriever;

    @GetMapping(
            value = {"getAll/{numberOfBlocksAgo}", "getAll"}
    )
    public String getMintEventsAndAddToDB(@PathVariable(required = false) String numberOfBlocksAgo) throws ExecutionException, InterruptedException {
        if (numberOfBlocksAgo == null) {
            numberOfBlocksAgo = "5760";
        }
        List<MintEvent> events = mintEventRetriever.getMintEvents(new BigInteger(numberOfBlocksAgo));
        String output;
        if (events.size() == 0) {
            output = "No events found";
            System.out.println(output);
            return output;
        }
        output = events.toString();
        System.out.println(output);
        return output;
    }

}
