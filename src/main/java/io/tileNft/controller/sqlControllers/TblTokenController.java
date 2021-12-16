package io.tileNft.controller.sqlControllers;

import io.tileNft.controller.BaseController;
import io.tileNft.sql.dtos.TokenDTO;
import io.tileNft.sql.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/tokens"})
public class TblTokenController extends BaseController {

  @Autowired private TokenRepository tokenRepository;

  @GetMapping("getAll")
  public String getAllTokens() {
    return tokenRepository.read().toString();
  }

  @GetMapping("get/{id}")
  public String getToken(@PathVariable Long id) {
    return tokenRepository.readById(id).toString();
  }

  @GetMapping("insert/{tokenId}")
  public String insertToken(
      @PathVariable Long tokenId,
      @RequestParam Long saleId,
      @RequestParam String name,
      @RequestParam String description,
      @RequestParam String externalUrl,
      @RequestParam String imageUrl) {
    if (saleId == null
        || name == null
        || description == null
        || externalUrl == null
        || imageUrl == null) {
      return "Please pass a 'saleId', 'nftName', 'description', 'externalUrl', AND 'imageUrl' to create a token";
    }
    TokenDTO tokenDTO =
        tokenRepository.create(
            TokenDTO.builder()
                .tokenId(tokenId)
                .saleId(saleId)
                .name(name)
                .description(description)
                .externalUrl(externalUrl)
                .imageUrl(imageUrl)
                .build());
    if (tokenDTO == null) {
      return "Cannot create tokenId: " + tokenId;
    }
    return tokenDTO.toString();
  }

  /** Update the token's sale id */
  @GetMapping("update/{tokenId}")
  public String updateToken(
      @PathVariable Long tokenId,
      @RequestParam(required = false) Long saleId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String externalUrl,
      @RequestParam(required = false) String imageUrl) {
    if (saleId == null
        && name == null
        && description == null
        && externalUrl == null
        && imageUrl == null) {
      return "Please pass a 'saleId', 'nftName', 'description', 'externalUrl', OR 'imageUrl' to create a token";
    }
    TokenDTO.TokenDTOBuilder tokenDTOBuilder = TokenDTO.builder().tokenId(tokenId);
    if (saleId != null) {
      tokenDTOBuilder = tokenDTOBuilder.saleId(saleId);
    }
    if (name != null) {
      tokenDTOBuilder = tokenDTOBuilder.name(name);
    }
    if (description != null) {
      tokenDTOBuilder = tokenDTOBuilder.description(description);
    }
    if (externalUrl != null) {
      tokenDTOBuilder = tokenDTOBuilder.externalUrl(externalUrl);
    }
    if (imageUrl != null) {
      tokenDTOBuilder = tokenDTOBuilder.imageUrl(imageUrl);
    }
    TokenDTO tokenDTO = tokenRepository.update(tokenDTOBuilder.build());
    if (tokenDTO == null) {
      return "Cannot update tokenId: " + tokenId;
    }
    return tokenDTO.toString();
  }

  /** Delete the token from the sql table. The token will still exist on the blockchain. */
  @GetMapping("delete/{tokenId}")
  public String deleteToken(@PathVariable Long tokenId) {
    TokenDTO tokenDTO = tokenRepository.readById(tokenId);
    if (!tokenRepository.delete(tokenDTO)) {
      return "Could not delete tokenId: " + tokenId;
    }
    return "Deleted tokenId: " + tokenId;
  }
}
