package FirstJavaSpring.HVL;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import FirstJavaSpring.HVL.Poll;
import FirstJavaSpring.HVL.PollController;
import FirstJavaSpring.HVL.PollManager;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(PollController.class)
public class PollControllerTests {

  public static ResultHandler printCustom() {
    return result -> {
      System.out.println("Custom print statement:");
      System.out.println("Status: " + result.getResponse().getStatus());
      System.out.println(
        "Content: " + result.getResponse().getContentAsString()
      );
      // Add more details as required
    };
  }

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
      .andDo(printCustom());
  }

  @Test
  void testListPolls() throws Exception {
    mockMvc
      .perform(get("/polls"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(
        jsonPath("$[0].question").value("What's your favorite sport?")
      );
  }
}
