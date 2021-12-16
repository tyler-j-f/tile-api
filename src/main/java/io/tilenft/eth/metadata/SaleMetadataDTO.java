package io.tilenft.eth.metadata;

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
