package io.tilenft.erc721;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleMetadataDTO {
  private ArrayList<Object> attributes;
  private String description;
  private String external_url;
  private String image;
  private String name;
}
