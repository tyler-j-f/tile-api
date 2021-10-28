package com.tylerfitzgerald.demo_api.erc721.token;

import com.tylerfitzgerald.demo_api.erc721.traits.Trait;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenDataDTO {
  private ArrayList<Object> attributes;
  private String description;
  private String external_url;
  private String image;
  private String name;
}
