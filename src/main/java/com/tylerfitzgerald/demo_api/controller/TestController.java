package com.tylerfitzgerald.demo_api.controller;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageClass;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.tylerfitzgerald.demo_api.config.ContractConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/test"})
public class TestController extends BaseController {

  @Autowired private ContractConfig contractConfig;

  //  @GetMapping("one")
  //  public String one() {
  //    // The ID of your GCP project
  //    String projectId = "dev-eth-api";
  //
  //    // The ID to give your GCS bucket
  //    String bucketName = "dev-eth-bucket-1";
  //
  //    Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
  //
  //    // See the StorageClass documentation for other valid storage classes:
  //    //
  // https://googleapis.dev/java/google-cloud-clients/latest/com/google/cloud/storage/StorageClass.html
  //    StorageClass storageClass = StorageClass.STANDARD;
  //
  //    // See this documentation for other valid locations:
  //    // http://g.co/cloud/storage/docs/bucket-locations#location-mr
  //    String location = "US";
  //
  //    Bucket bucket =
  //        storage.create(
  //            BucketInfo.newBuilder(bucketName)
  //                .setStorageClass(storageClass)
  //                .setLocation(location)
  //                .build());
  //
  //    String output =
  //        "Created bucket "
  //            + bucket.getName()
  //            + " in "
  //            + bucket.getLocation()
  //            + " with storage class "
  //            + bucket.getStorageClass();
  //
  //    System.out.println(output);
  //    return output;
  //  }

  @GetMapping("auth")
  public String authExplicit() throws IOException {
    String out = "";
    Resource resource = new ClassPathResource("classpath:dev-eth-api-d91a5dc6df11.json");
    InputStream inputStream = resource.getInputStream();
    // You can specify a credential file by providing a path to GoogleCredentials.
    // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
    String jsonPath = "classpath:dev-eth-api-d91a5dc6df11.json";
    GoogleCredentials credentials =
        GoogleCredentials.fromStream(inputStream)
            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    System.out.println("Buckets:");
    out = out + "Buckets:";
    Page<Bucket> buckets = storage.list();
    for (Bucket bucket : buckets.iterateAll()) {
      out = out + "\n" + bucket.toString();
      System.out.println(bucket.toString());
    }
    return out;
  }

  @GetMapping("two")
  public String getContractJSON() {
    // If you don't specify credentials when constructing the client, the client library will
    // look for credentials via the environment variable GOOGLE_APPLICATION_CREDENTIALS.
    Storage storage = StorageOptions.getDefaultInstance().getService();

    System.out.println("Buckets:");
    Page<Bucket> buckets = storage.list();
    String output = "";
    for (Bucket bucket : buckets.iterateAll()) {
      output = output + "\n" + bucket.toString();
      System.out.println(bucket.toString());
    }
    return output;
  }

  @GetMapping("three")
  public String three() throws IOException {
    String out = "";
    Resource resource = new ClassPathResource("classpath:dev-eth-api-d91a5dc6df11.json");
    InputStream inputStream = resource.getInputStream();
    try {
      byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
      String data = new String(bdata, StandardCharsets.UTF_8);
      out = data.toString();
    } catch (IOException e) {
      out = e.toString();
    }
    return out;
  }

  @GetMapping("four")
  public String four() throws IOException, URISyntaxException {
    String out = "";
    Resource resource = new ClassPathResource("classpath:dev-eth-api-d91a5dc6df11.json");
    InputStream inputStream = resource.getInputStream();
    try {
      byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
      String data = new String(bdata, StandardCharsets.UTF_8);
      out = data.toString();
    } catch (IOException e) {
      out = e.toString();
    }
    return out;
  }
}
