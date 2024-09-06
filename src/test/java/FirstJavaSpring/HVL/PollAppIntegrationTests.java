// package FirstJavaSpring.HVL;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultHandler;
// import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class PollAppIntegrationTests {

//   public static ResultHandler printCustom() {
//     return result -> {
//       System.out.println("Custom print statement:");
//       System.out.println("Status: " + result.getResponse().getStatus());
//       System.out.println(
//         "Content: " + result.getResponse().getContentAsString()
//       );
//       // Add more details as required
//     };
//   }

//   @Autowired
//   private MockMvc mockMvc;

//   @Test
//   void testFullWorkflow() throws Exception {
//     // Create a new user
//     mockMvc
//       .perform(
//         post("/users")
//           .contentType(MediaType.APPLICATION_JSON)
//           .content("{\"username\":\"john\", \"email\":\"john@example.com\"}")
//       )
//       .andExpect(status().isCreated());

//     // List all users (-> shows the newly created user)
//     mockMvc
//       .perform(get("/users"))
//       .andExpect(status().isOk())
//       .andExpect(jsonPath("$.length()").value(1))
//       .andExpect(jsonPath("$[0].username").value("john"))
//       .andDo(printCustom());

//     // Create another user
//     mockMvc
//       .perform(
//         post("/users")
//           .contentType(MediaType.APPLICATION_JSON)
//           .content("{\"username\":\"jane\", \"email\":\"jane@example.com\"}")
//       )
//       .andExpect(status().isCreated());

//     // List all users again (-> shows two users)
//     mockMvc
//       .perform(get("/users"))
//       .andExpect(status().isOk())
//       .andExpect(jsonPath("$.length()").value(2));

//     // User 1 creates a new poll
//     mockMvc
//       .perform(
//         post("/polls")
//           .contentType(MediaType.APPLICATION_JSON)
//           .content("{\"question\":\"What's your favorite color?\"}")
//       )
//       .andDo(printCustom())
//       .andExpect(status().isCreated());

//     // Add vote options to the newly created poll
//     String[] colors = { "Red", "Blue" };
//     for (int i = 0; i < colors.length; i++) {
//       mockMvc
//         .perform(
//           post("/voteOptions")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(
//               "{\"caption\":\"" +
//               colors[i] +
//               "\", \"presentationOrder\":" +
//               (i + 1) +
//               "}"
//             )
//         )
//         .andDo(printCustom())
//         .andExpect(status().isCreated());
//     }

//     // List polls (-> shows the new poll)
//     mockMvc
//       .perform(get("/polls"))
//       .andDo(printCustom())
//       .andExpect(status().isOk())
//       .andExpect(jsonPath("$.length()").value(1))
//       .andExpect(
//         jsonPath("$[0].question").value("What's your favorite color?")
//       );

//     // User 2 votes on the poll
//     mockMvc
//       .perform(
//         post("/votes")
//           .contentType(MediaType.APPLICATION_JSON)
//           .content("{\"pollId\":1, \"optionId\":1, \"userId\":2}")
//       )
//       .andDo(printCustom())
//       .andExpect(status().isCreated());

//     // User 2 changes his vote
//     mockMvc
//       .perform(
//         put("/votes/1")
//           .contentType(MediaType.APPLICATION_JSON)
//           .content("{\"optionId\":2}")
//       )
//       .andExpect(status().isOk());

//     // List votes (-> shows the most recent vote for User 2)
//     mockMvc
//       .perform(get("/votes"))
//       .andExpect(status().isOk())
//       .andExpect(jsonPath("$.length()").value(1))
//       .andExpect(jsonPath("$[0].optionId").value(2));

//     // Delete the one poll
//     mockMvc.perform(delete("/polls/1")).andExpect(status().isOk());

//     // List votes (-> should be empty)
//     mockMvc
//       .perform(get("/votes"))
//       .andExpect(status().isOk())
//       .andExpect(jsonPath("$.length()").value(0));
//   }
// }
