package FirstJavaSpring.HVL.controllers;

import FirstJavaSpring.HVL.Polls.*;
import FirstJavaSpring.HVL.dto.PollRequest;
import FirstJavaSpring.HVL.dto.UserRequest;
import FirstJavaSpring.HVL.dto.VoteRequest;
import FirstJavaSpring.HVL.service.PollService;
import FirstJavaSpring.HVL.service.UserService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/polls")
public class PollController {

  @Autowired
  private PollService pollService;

  @Autowired
  private UserService userService;

  // Endpoint to create a poll
  @PostMapping
  public ResponseEntity<?> createPoll(@RequestBody PollRequest pollRequest) {
    String creatorId = pollRequest.getCreator();
    // Check if the creator exists
    User existingUser = userService.getUserById(creatorId);
    if (existingUser == null) {
      return new ResponseEntity<>(
        "User does not exist, poll creation failed",
        HttpStatus.BAD_REQUEST
      );
    }

    // Proceed with poll creation if the user exists
    HashMap<String, VoteOption> options = new HashMap<>();
    for (VoteOption voteOption : pollRequest.getOptions()) {
      options.put(voteOption.getVoteOptionId(), voteOption);
    }

    Poll poll = new Poll(pollRequest.getQuestion(), options, creatorId);
    pollService.addPoll(poll); // Add poll to the global poll list
    existingUser.addPoll(poll); // Add poll to the user's polls list TODO: Reffer to the polls array using poll id instead

    return new ResponseEntity<Poll>(poll, HttpStatus.CREATED);
  }

  // Endpoint to cast a vote
  @PostMapping("/{pollId}")
  public ResponseEntity<?> castVote(
    @PathVariable("pollId") String pollId,
    @RequestBody VoteRequest voteRequest
  ) {
    String voterId = voteRequest.getUserId();

    boolean userExists = userService.userExistsWithId(voterId);
    if (!userExists) {
      return new ResponseEntity<>(
        "Voter with voter id (" + voterId + ") does not exist",
        HttpStatus.BAD_REQUEST
      );
    }

    Poll poll = pollService.findPollById(pollId);
    if (poll == null) {
      return new ResponseEntity<>("Poll not found!", HttpStatus.NOT_FOUND);
    }

    String selectedOption = voteRequest.getSelectedOption();
    if (selectedOption == null) {
      return new ResponseEntity<>(
        "Selected option is required to cast a vote",
        HttpStatus.BAD_REQUEST
      );
    }
    if (!pollService.pollHasOption(pollId, selectedOption)) {
      return new ResponseEntity<>(
        "Selected option ( " + selectedOption + " ) does not exist in the poll",
        HttpStatus.NOT_FOUND
      );
    }
    pollService.castVote(
      voterId,
      pollId,
      selectedOption,
      voteRequest.getIsUpVote()
    );

    return new ResponseEntity<>(voteRequest, HttpStatus.OK);
  }

  // Endpoint to get all polls
  @GetMapping
  public ResponseEntity<List<Poll>> getAllPolls() {
    List<Poll> polls = pollService.getAllPolls();
    return new ResponseEntity<>(polls, HttpStatus.OK);
  }

  // Endpoint to delete a poll (Only the creator can delete)
  @DeleteMapping("/{pollId}")
  public ResponseEntity<String> deletePoll(
    @PathVariable("pollId") String pollId,
    @RequestBody UserRequest user
  ) {
    // Find the poll by ID
    Poll poll = pollService.findPollById(pollId);
    if (poll == null) {
      return new ResponseEntity<>("Poll not found!", HttpStatus.NOT_FOUND);
    }

    // Check if the user trying to delete is the creator
    if (!poll.getCreator().equals(user.getUserId())) {
      return new ResponseEntity<>(
        "Only the creator of the poll can delete it",
        HttpStatus.FORBIDDEN
      );
    }

    // Proceed with deletion
    pollService.deletePoll(pollId);
    return new ResponseEntity<>("Poll deleted successfully", HttpStatus.OK);
  }
}
