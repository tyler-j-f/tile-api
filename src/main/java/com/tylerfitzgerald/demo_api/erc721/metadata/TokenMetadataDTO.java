package com.tylerfitzgerald.demo_api.erc721.metadata;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenMetadataDTO {
  private ArrayList<Object> attributes;
  private String description;
  private String external_url;
  private String image;
  private String name;
}
