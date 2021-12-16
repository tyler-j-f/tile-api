package io.tilenft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIntegrationTest {

  @Autowired private TestRestTemplate template;
  //
  //  @Test
  //  public void getHello() throws Exception {
  //    ResponseEntity<String> response = template.getForEntity("/", String.class);
  //    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  //  }
}
