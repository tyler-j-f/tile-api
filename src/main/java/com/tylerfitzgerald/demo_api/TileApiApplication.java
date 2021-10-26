package com.tylerfitzgerald.demo_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TileApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(TileApiApplication.class, args);
  }
}
