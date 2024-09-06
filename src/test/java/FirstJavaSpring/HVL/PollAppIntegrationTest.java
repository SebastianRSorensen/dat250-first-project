package FirstJavaSpring.HVL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import FirstJavaSpring.HVL.old.PollOld;
import FirstJavaSpring.HVL.old.User;
import FirstJavaSpring.HVL.old.Vote;
import FirstJavaSpring.HVL.old.VoteOption;
import java.time.Instant;
import java.util.UUID;
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
    User user1 = new User();
    user1.setUsername("user1");
    user1.setEmail("user1@example.com");
    ResponseEntity<User> user1Response = restTemplate.postForEntity(
      baseUrl() + "/users",
      user1,
      User.class
    );
    assertEquals(HttpStatus.OK, user1Response.getStatusCode());

    // List all users (should show the newly created user)
    ResponseEntity<User[]> usersResponse = restTemplate.getForEntity(
      baseUrl() + "/users",
      User[].class
    );
    assertEquals(1, usersResponse.getBody().length);

    // Create another user
    User user2 = new User();
    user2.setUsername("user2");
    user2.setEmail("user2@example.com");
    ResponseEntity<User> user2Response = restTemplate.postForEntity(
      baseUrl() + "/users",
      user2,
      User.class
    );
    assertEquals(HttpStatus.OK, user2Response.getStatusCode());

    // List all users again (should show two users)
    usersResponse =
      restTemplate.getForEntity(baseUrl() + "/users", User[].class);
    assertEquals(2, usersResponse.getBody().length);

    System.out.println(usersResponse.getBody());

    // User 1 creates a new poll
    PollOld poll = new PollOld();
    poll.setQuestion("What is your favorite color?");
    poll.setPublishedAt(Instant.now());
    poll.setValidUntil(Instant.now().plusSeconds(86400)); // valid for 1 day
    VoteOption option1 = new VoteOption();
    option1.setCaption("Red");
    option1.setPresentationOrder(1);
    poll.getVoteOptions().add(option1);
    VoteOption option2 = new VoteOption();
    option1.setCaption("Blue");
    option1.setPresentationOrder(1);
    poll.getVoteOptions().add(option2);
    ResponseEntity<PollOld> pollResponse = restTemplate.postForEntity(
      baseUrl() + "/polls",
      poll,
      PollOld.class
    );
    assertEquals(HttpStatus.OK, pollResponse.getStatusCode());

    // List polls (should show the new poll)
    ResponseEntity<PollOld[]> pollsResponse = restTemplate.getForEntity(
      baseUrl() + "/polls",
      PollOld[].class
    );
    assertEquals(1, pollsResponse.getBody().length);

    System.out.println("Listing polls: ");
    for (PollOld p : pollsResponse.getBody()) {
      System.out.println("Questions" + p.getQuestion());
      System.out.println("Options" + p.getVoteOptions());
    }
    // User 2 votes on the poll
    Vote vote = new Vote();
    vote.setPublishedAt(Instant.now());
    vote.setVoteOption(pollResponse.getBody().getVoteOptions().get(0));
    ResponseEntity<Vote> voteResponse = restTemplate.postForEntity(
      baseUrl() + "/votes",
      vote,
      Vote.class
    );
    assertEquals(HttpStatus.OK, voteResponse.getStatusCode());

    // User 2 changes his vote
    vote.setVoteOption(pollResponse.getBody().getVoteOptions().get(1)); // change to another option
    ResponseEntity<Vote> updatedVoteResponse = restTemplate.exchange(
      baseUrl() + "/votes/" + UUID.randomUUID().toString(),
      HttpMethod.PUT,
      new HttpEntity<>(vote),
      Vote.class
    );
    assertEquals(HttpStatus.OK, updatedVoteResponse.getStatusCode());

    // List votes (should show the most recent vote for User 2)
    ResponseEntity<Vote[]> votesResponse = restTemplate.getForEntity(
      baseUrl() + "/votes",
      Vote[].class
    );
    assertEquals(1, votesResponse.getBody().length); // Ensure the vote count is consistent

    // Delete the one poll
    restTemplate.delete(
      baseUrl() + "/polls/" + pollResponse.getBody().getQuestion()
    );

    // List votes (should be empty)
    votesResponse =
      restTemplate.getForEntity(baseUrl() + "/votes", Vote[].class);
    assertTrue(votesResponse.getBody().length == 0);
  }
}
