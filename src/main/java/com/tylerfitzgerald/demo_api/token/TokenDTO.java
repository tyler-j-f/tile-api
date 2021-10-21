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
    private String name;
    private String description;
    private String externalUrl;
    private String imageUrl;
}
//    private static final String CREATE_SQL  = "CREATE TABLE token(id int NOT NULL AUTO_INCREMENT, tokenId int, saleId int, name NVCHAR(MAX), description NVCHAR(MAX), externalUrl NVCHAR(MAX), imageUrl NVCHAR(MAX), PRIMARY KEY (id))";
