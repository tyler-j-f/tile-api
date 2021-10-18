package com.tylerfitzgerald.demo_api;

import lombok.Builder;

import java.util.List;

@Builder
public class MintEvent {
    private List<String> topics;

    public String getSaleOptionSupplyPostMint() {
        return topics.get(1);
    }

    public String getSaleOptionId() {
        return topics.get(2);
    }

    public String getTokenId() {
        return topics.get(3);
    }

}
