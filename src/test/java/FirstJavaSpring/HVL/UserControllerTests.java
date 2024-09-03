package FirstJavaSpring.HVL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PollManager pollManager;

  @BeforeEach
  void setUp() {
    User jane = new User();
    jane.setUsername("jane");
    jane.setEmail("jane@example.com");

    Map<Integer, User> userMap = new HashMap<>();

    userMap.put(jane.hashCode(), jane);

    // Assuming you have methods in PollManager to handle these
    Mockito.when(pollManager.getUser(jane.hashCode())).thenReturn(jane);
    Mockito.when(pollManager.getAllUsers()).thenReturn(userMap);
  }

  @Test
  void testUserCreationAndListing() throws Exception {
    // Mock creating a new user
    mockMvc
      .perform(
        post("/users")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"username\":\"john\", \"email\":\"john@example.com\"}")
      )
      .andExpect(status().isCreated());

    // Test listing all users
    mockMvc
      .perform(get("/users"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].username").value("jane"));

    // Add another user
    mockMvc
      .perform(
        post("/users")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"username\":\"vilde\", \"email\":\"vilde@example.com\"}")
      )
      .andExpect(status().isCreated());
  }

  @Test
  void testPollAndVoteScenarios() throws Exception {
    // User 1 creates a new poll
    mockMvc
      .perform(
        post("/polls")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"question\":\"What's your favorite color?\"}")
      )
      .andExpect(status().isCreated());

    // List polls
    mockMvc
      .perform(get("/polls"))
      .andExpect(status().isOk())
      .andExpect(
        jsonPath("$[0].question").value("What's your favorite color?")
      );
    // User 2 votes on the poll
    // Add other test cases following the similar structure
  }
}
