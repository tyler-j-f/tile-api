package com.tylerfitzgerald.demo_api.scheduler;

import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.config.TraitsConfig;
import com.tylerfitzgerald.demo_api.events.MintEvent;
import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import com.tylerfitzgerald.demo_api.token.TokenDTO;
import com.tylerfitzgerald.demo_api.token.TokenRepository;
import com.tylerfitzgerald.demo_api.token.nft.NFTFacadeDTO;
import com.tylerfitzgerald.demo_api.token.nft.NFTInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class Scheduler {

  @Autowired private TokenRepository tokenRepository;

  @Autowired private MintEventRetriever mintEventRetriever;

  @Autowired private EnvConfig appConfig;

  @Autowired private TraitsConfig traitsConfig;

  @Autowired private NFTInitializer nftInitializer;

  // @Scheduled(fixedRateString = "${spring.application.schedulerFixedRateMs}")
  public void getMintEvents() throws ExecutionException, InterruptedException {
    List<MintEvent> events =
        mintEventRetriever.getMintEvents(
            new BigInteger(appConfig.getSchedulerNumberOfBlocksToLookBack()));
    if (events.size() == 0) {
      System.out.println("No events found");
      return;
    }
    for (MintEvent event : events) {
      handleMintEvent(event);
    }
  }

  private void handleMintEvent(MintEvent event) {
    Long tokenId = getLongFromHexString(event.getTokenId());
    TokenDTO existingTokenDTO = tokenRepository.readById(tokenId);
    if (existingTokenDTO != null) {
      /*
       * If a tokenDTO exists, then we've already added this tokenID to tblToken.
       * Nothing more to do with this tokenID
       */
      // System.out.println("Token mint event has already been added to tblToken. tokenId: " +
      // tokenId);
      return;
    }
    TokenDTO tokenDTO =
        tokenRepository.create(
            TokenDTO.builder()
                .tokenId(tokenId)
                .saleId(getLongFromHexString(event.getSaleOptionId()))
                .name(appConfig.getNftName())
                .description(appConfig.getNftDescription())
                .externalUrl(appConfig.getNftExternalUrl())
                .imageUrl(appConfig.getNftBaseImageUrl())
                .build());
    if (tokenDTO == null) {
      System.out.println("Token mint event failed to add to tblToken. tokenId: " + tokenId);
      return;
    }
    System.out.println("Token mint event successfully added to tblToken. tokenId: " + tokenId);
  }

  private Long getLongFromHexString(String hexString) {
    return Long.parseLong(hexString.split("0x")[1], 16);
  }

  //  @Scheduled(fixedRateString = "${spring.application.schedulerFixedRateMs}")
  public void test() throws ExecutionException, InterruptedException {
    System.out.println("\nDEBUG:\n" + traitsConfig.toString());
  }

  @Scheduled(fixedRateString = "${spring.application.schedulerFixedRateMs}")
  public void testTwo() throws ExecutionException, InterruptedException {
    Long tokenId = 221L;
    NFTFacadeDTO nft = nftInitializer.initialize(tokenId);
    if (nft == null) {
      System.out.println(
          "\nDEBUG: nftInitializer->initialize failed. tokenId: " + tokenId.toString());
      return;
    }
    System.out.println("\nDEBUG:\nnft: " + nft.toString());
  }
}
