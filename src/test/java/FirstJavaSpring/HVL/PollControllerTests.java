package FirstJavaSpring.HVL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(PollController.class)
public class PollControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PollManager pollManager;

  @BeforeEach
  void setUp() {
    // Mock setup for Polls
    Poll newPoll = new Poll();
    newPoll.setQuestion("What's your favorite sport?");
    Mockito.when(pollManager.getAllPolls()).thenReturn(Arrays.asList(newPoll));
  }

  @Test
  void testCreatePoll() throws Exception {
    mockMvc
      .perform(
        post("/polls")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"question\":\"What's your favorite color?\"}")
      )
      .andExpect(status().isCreated())
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void testListPolls() throws Exception {
    mockMvc
      .perform(get("/polls"))
      .andExpect(status().isOk())
      .andExpect(
        jsonPath("$[0].question").value("What's your favorite sport?")
      );
  }
  // Additional tests can be added here to cover more scenarios
}
