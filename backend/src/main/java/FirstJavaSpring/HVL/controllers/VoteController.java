package FirstJavaSpring.HVL.controllers;

import FirstJavaSpring.HVL.PollManager;
import FirstJavaSpring.HVL.Polls.Vote;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/votes")
public class VoteController {

  @Autowired
  private PollManager pollManager;

  // Endpoint to get all votes across all polls
  @GetMapping
  public ResponseEntity<List<Vote>> getAllVotes() {
    List<Vote> votes = pollManager.getAllVotes();
    return new ResponseEntity<>(votes, HttpStatus.OK);
  }
}
