package FirstJavaSpring.HVL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voteOptions")
public class VoteOptionController {

  @Autowired
  private PollManager pollManager;

  // Create a new vote option
  @PostMapping
  public ResponseEntity<?> createVoteOption(
    @RequestBody VoteOption voteOption
  ) {
    int id = voteOption.hashCode(); // Simple hash-based ID (not recommended for production)
    pollManager.addVoteOption(id, voteOption);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body("Vote option created with ID: " + id);
  }

  // Update a vote option
  @PutMapping("/{id}")
  public ResponseEntity<?> updateVoteOption(
    @PathVariable int id,
    @RequestBody VoteOption updatedVoteOption
  ) {
    VoteOption existingVoteOption = pollManager.getVoteOption(id);
    if (existingVoteOption == null) {
      return ResponseEntity.notFound().build();
    }
    existingVoteOption.setCaption(updatedVoteOption.getCaption());
    existingVoteOption.setPresentationOrder(
      updatedVoteOption.getPresentationOrder()
    );
    pollManager.addVoteOption(id, existingVoteOption);
    return ResponseEntity.ok("Vote option updated with ID: " + id);
  }

  // Get a specific vote option
  @GetMapping("/{id}")
  public ResponseEntity<?> getVoteOption(@PathVariable int id) {
    VoteOption voteOption = pollManager.getVoteOption(id);
    if (voteOption == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(voteOption);
  }

  // List all vote options
  @GetMapping
  public ResponseEntity<?> listVoteOptions() {
    return ResponseEntity.ok(pollManager.getAllVoteOptions());
  }
}
