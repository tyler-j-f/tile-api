package io.tilenft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

  @Autowired private MockMvc mvc;
  //
  //  @Test
  //  public void getRootNotFound() throws Exception {
  //    mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
  //        .andExpect(status().isNotFound())
  //        .andExpect(content().string(equalTo("")));
  //  }
}
