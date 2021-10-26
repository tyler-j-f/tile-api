package com.tylerfitzgerald.demo_api;

import com.tylerfitzgerald.demo_api.traits.Trait;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NFT {
  private ArrayList<Trait> attributes;
  private String description;
  private String external_url;
  private String image;
  private String name;
}
