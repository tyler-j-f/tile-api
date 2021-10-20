package com.tylerfitzgerald.demo_api.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private Long id;
    private Long tokenId;
    private Long saleId;
}