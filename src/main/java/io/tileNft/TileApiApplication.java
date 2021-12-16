package io.tileNft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TileApiApplication {

  public static void main(String[] args) {
    nu.pattern.OpenCV.loadLocally();
    SpringApplication.run(TileApiApplication.class, args);
  }
}
