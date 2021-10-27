package com.tylerfitzgerald.demo_api.nft;

import com.tylerfitzgerald.demo_api.nft.traits.Trait;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NFTData {
  private ArrayList<Trait> attributes;
  private String description;
  private String external_url;
  private String image;
  private String name;
}
