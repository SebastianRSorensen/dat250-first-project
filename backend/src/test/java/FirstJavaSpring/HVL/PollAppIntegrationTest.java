package FirstJavaSpring.HVL;

import static org.junit.jupiter.api.Assertions.assertEquals;

import FirstJavaSpring.HVL.Polls.Poll;
import FirstJavaSpring.HVL.Polls.User;
import FirstJavaSpring.HVL.Polls.Vote;
import FirstJavaSpring.HVL.Polls.VoteOption;
import FirstJavaSpring.HVL.dto.PollRequest;
import FirstJavaSpring.HVL.dto.UserRequest;
import FirstJavaSpring.HVL.dto.VoteRequest;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollAppIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private String baseUrl() {
    return "http://localhost:" + port;
  }

  @Test
  public void testCompleteScenario() {
    // Create a new user
    User user1 = new User("seb", "seb@example.com");
    ResponseEntity<User> user1Response = restTemplate.postForEntity(
      baseUrl() + "/users",
      user1,
      User.class
    );
    assertEquals(HttpStatus.CREATED, user1Response.getStatusCode());

    // List all users (should show the newly created user)
    ResponseEntity<User[]> usersResponse = restTemplate.getForEntity(
      baseUrl() + "/users",
      User[].class
    );
    assertEquals(1, usersResponse.getBody().length);

    // Create another user
    User user2 = new User();
    user2.setUsername("Vilde");
    user2.setEmail("vil@example.com");
    ResponseEntity<User> user2Response = restTemplate.postForEntity(
      baseUrl() + "/users",
      user2,
      User.class
    );
    assertEquals(HttpStatus.CREATED, user2Response.getStatusCode());

    // List all users again (should show two users)
    usersResponse =
      restTemplate.getForEntity(baseUrl() + "/users", User[].class);
    assertEquals(2, usersResponse.getBody().length);

    // User 1 creates a new poll using PollRequest
    PollRequest pollRequest = new PollRequest();
    pollRequest.setCreator(user1.getUsername());
    pollRequest.setQuestion("What is your favorite color?");
    Set<VoteOption> options = new HashSet<>();
    VoteOption option1 = new VoteOption(1, "Red");
    options.add(option1);
    VoteOption option2 = new VoteOption(2, "Blue");
    options.add(option2);
    pollRequest.setOptions(options);

    ResponseEntity<Poll> pollResponse = restTemplate.postForEntity(
      baseUrl() + "/polls",
      pollRequest,
      Poll.class
    );
    assertEquals(HttpStatus.CREATED, pollResponse.getStatusCode());
    Poll poll = pollResponse.getBody();

    // List polls (should show the new poll)
    ResponseEntity<Poll[]> pollsResponse = restTemplate.getForEntity(
      baseUrl() + "/polls",
      Poll[].class
    );
    assertEquals(1, pollsResponse.getBody().length);

    // User 2 casts a vote
    VoteRequest voteRequest = new VoteRequest();
    voteRequest.setUsername(user2.getUsername());
    voteRequest.setSelectedOption(option2);

    ResponseEntity<String> voteResponse = restTemplate.postForEntity(
      baseUrl() + "/polls/" + poll.getPollId(),
      voteRequest,
      String.class
    );
    assertEquals(HttpStatus.OK, voteResponse.getStatusCode());

    // User 2 changes the vote
    voteRequest.setSelectedOption(option1); // Change the vote to option "Red"
    ResponseEntity<String> voteChangeResponse = restTemplate.postForEntity(
      baseUrl() + "/polls/" + poll.getPollId(),
      voteRequest,
      String.class
    );
    assertEquals(HttpStatus.OK, voteChangeResponse.getStatusCode());

    // List votes (should show the most recent vote for User 2)
    ResponseEntity<Vote[]> votesResponse = restTemplate.getForEntity(
      baseUrl() + "/votes",
      Vote[].class
    );
    assertEquals(1, votesResponse.getBody().length); // Ensure the vote count is consistent

    // Delete the poll with User 1 (creator)
    UserRequest deleteRequestUser1 = new UserRequest(user1.getUsername());
    ResponseEntity<String> deleteResponse = restTemplate.exchange(
      baseUrl() + "/polls/" + poll.getPollId(),
      HttpMethod.DELETE,
      new HttpEntity<>(deleteRequestUser1),
      String.class
    );
    assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

    // // List votes (should be empty)
    ResponseEntity<Poll[]> pollsResponseEmpty = restTemplate.getForEntity(
      baseUrl() + "/polls",
      Poll[].class
    );
    assertEquals(0, pollsResponseEmpty.getBody().length);
  }
}
