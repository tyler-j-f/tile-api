package com.tylerfitzgerald.demo_api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Creature {
    private ArrayList<Trait> attributes;
    private String description;
    private String external_url;
    private String image;
    private String name;
}
