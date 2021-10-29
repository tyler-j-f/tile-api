package com.tylerfitzgerald.demo_api.scheduler;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.common.collect.Lists;
import com.tylerfitzgerald.demo_api.config.ContractConfig;
import com.tylerfitzgerald.demo_api.config.SalesConfig;
import com.tylerfitzgerald.demo_api.config.TokenConfig;
import com.google.cloud.storage.StorageOptions;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMintEvents;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class Scheduler {

  @Autowired private HandleMintEvents handleMintEventsAndCreateDBTokensTask;

  /**
   * Execute tasks every schedulerFixedRateMs If you would like to execute tasks on a different
   * cadence, add another method like this with a different @Scheduled notation.
   *
   * @throws ExecutionException
   * @throws InterruptedException
   */
  // @Scheduled(fixedRateString = "${spring.application.schedulerFixedRateMs}")
  public void executeTasks() throws ExecutionException, InterruptedException {
    handleMintEventsAndCreateDBTokensTask.execute();
  }

  @Scheduled(fixedRateString = "${spring.application.schedulerFixedRateMs}")
  static void authExplicit() throws IOException {
    // You can specify a credential file by providing a path to GoogleCredentials.
    // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment
    // variable.
    GoogleCredentials credentials =
        GoogleCredentials.fromStream(
            new FileInputStream("src/main/resources/service-account-1-dev-eth-api-key-file.json"));
    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    System.out.println("Buckets:");
    Page<Bucket> buckets = storage.list();
    for (Bucket bucket : buckets.iterateAll()) {
      System.out.println(bucket.toString());
    }
  }

  //  @Scheduled(fixedRateString = "${spring.application.schedulerFixedRateMs}")
  //  static void authImplicit() {
  //    // If you don't specify credentials when constructing the client, the client library will
  //    // look for credentials via the environment variable GOOGLE_APPLICATION_CREDENTIALS.
  //    Storage storage = StorageOptions.getDefaultInstance().getService();
  //
  //    System.out.println("Buckets:");
  //    Page<Bucket> buckets = storage.list();
  //    for (Bucket bucket : buckets.iterateAll()) {
  //      System.out.println(bucket.toString());
  //    }
  //  }
}
