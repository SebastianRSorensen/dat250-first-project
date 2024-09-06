package FirstJavaSpring.HVL.controllers;

import FirstJavaSpring.HVL.Polls.*;
import FirstJavaSpring.HVL.dto.PollRequest;
import FirstJavaSpring.HVL.dto.VoteRequest;
import FirstJavaSpring.HVL.service.PollService;
import FirstJavaSpring.HVL.service.UserService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polls")
public class PollController {

  @Autowired
  private PollService pollService;

  @Autowired
  private UserService userService;

  // Endpoint to create a poll
  @PostMapping
  public ResponseEntity<String> createPoll(
    @RequestBody PollRequest pollRequest
  ) {
    User creator = pollRequest.getCreator();

    // Check if the creator exists in the UserService
    User existingUser = userService.getUserByUsername(creator.getUsername());
    if (existingUser == null) {
      return new ResponseEntity<>(
        "User does not exist, poll creation failed",
        HttpStatus.BAD_REQUEST
      );
    }

    // Proceed with poll creation if the user exists
    Set<VoteOption> options = pollRequest.getOptions();
    Poll poll = new Poll(pollRequest.getQuestion(), options, existingUser);
    existingUser.getCreatedPolls().add(poll);
    pollService.addPoll(poll);

    return new ResponseEntity<>(
      "Poll created successfully",
      HttpStatus.CREATED
    );
  }

  // Endpoint to cast a vote
  @PostMapping("/{pollId}/vote")
  public String castVote(
    @PathVariable Long pollId,
    @RequestBody VoteRequest voteRequest
  ) {
    User voter = voteRequest.getVoter();
    Poll poll = findPollById(pollId); // Implement the logic to find poll by id
    VoteOption selectedOption = voteRequest.getSelectedOption();
    pollService.castVote(voter, poll, selectedOption);
    return "Vote cast successfully!";
  }

  // Helper method to find poll by ID (You should implement this properly)
  private Poll findPollById(Long pollId) {
    // Mocking the poll retrieval for this example
    return new Poll(
      "Example poll?",
      Set.of(new VoteOption(1, "Option 1")),
      new User("creator", "email@example.com")
    );
  }

  @GetMapping
  public ResponseEntity<List<Poll>> getAllPolls() {
    List<Poll> polls = pollService.getAllPolls();
    return new ResponseEntity<>(polls, HttpStatus.OK);
  }
}
