package com.tylerfitzgerald.demo_api.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private int id;
    private int tokenId;
    private int saleId;
}
