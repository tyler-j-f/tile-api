package io.tileNft.controller;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.tileNft.config.external.ContractConfig;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
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

  @GetMapping("auth")
  public String authExplicit() throws IOException {
    String out = "";
    Resource resource = new ClassPathResource("classpath:old-dev-eth-api-d91a5dc6df11.json");
    InputStream inputStream = resource.getInputStream();
    // You can specify a credential file by providing a path to GoogleCredentials.
    // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
    String jsonPath = "classpath:old-dev-eth-api-d91a5dc6df11.json";
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
    Resource resource = new ClassPathResource("classpath:old-dev-eth-api-d91a5dc6df11.json");
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
    Resource resource = new ClassPathResource("classpath:old-dev-eth-api-d91a5dc6df11.json");
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

  @GetMapping("five")
  public String five() throws IOException, URISyntaxException {

    Resource resource = new ClassPathResource("classpath:old-dev-eth-api-d91a5dc6df11.json");
    InputStream inputStream = resource.getInputStream();
    // You can specify a credential file by providing a path to GoogleCredentials.
    // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
    String jsonPath = "classpath:old-dev-eth-api-d91a5dc6df11.json";
    GoogleCredentials credentials =
        GoogleCredentials.fromStream(inputStream)
            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    // Note: For Java users, the Cloud SQL JDBC Socket Factory can provide authenticated connections
    // which is preferred to using the Cloud SQL Proxy with Unix sockets.
    // See https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory for details.

    // The configuration object specifies behaviors for the connection pool.
    HikariConfig config = new HikariConfig();

    // The following URL is equivalent to setting the config options below:
    // jdbc:mysql:///<DB_NAME>?cloudSqlInstance=<INSTANCE_CONNECTION_NAME>&
    // socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=<DB_USER>&password=<DB_PASS>
    // See the link below for more info on building a JDBC URL for the Cloud SQL JDBC Socket Factory
    // https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory#creating-the-jdbc-url

    String DB_NAME = "db1";
    String DB_USER = "testing123";
    String DB_PASS = "testing123";
    String INSTANCE_CONNECTION_NAME = "dev-eth-api:us-east4:sql-1";

    // Configure which instance and what database user to connect with.
    config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
    config.setUsername(DB_USER); // e.g. "root", "mysql"
    config.setPassword(DB_PASS); // e.g. "my-password"

    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);

    // The ipTypes argument can be used to specify a comma delimited list of preferred IP types
    // for connecting to a Cloud SQL instance. The argument ipTypes=PRIVATE will force the
    // SocketFactory to connect with an instance's associated private IP.
    config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");

    // ... Specify additional connection properties here.
    // ...

    // Initialize the connection pool using the configuration object.
    DataSource pool = new HikariDataSource(config);
    return "test";
  }

  @GetMapping("six")
  public String six() {
    return "Testing the new NFT contract";
  }
}
