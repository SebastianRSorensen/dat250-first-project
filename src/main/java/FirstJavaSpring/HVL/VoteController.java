package FirstJavaSpring.HVL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
public class VoteController {

  @Autowired
  private PollManager pollManager;

  // Endpoint to create a vote
  @PostMapping
  public ResponseEntity<?> createVote(@RequestBody Vote vote) {
    int id = vote.hashCode(); // Simple ID generation (not recommended for production)
    pollManager.addVote(id, vote);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body("Vote created with ID: " + id);
  }

  // Endpoint to update a vote
  @PutMapping("/{id}")
  public ResponseEntity<?> updateVote(
    @PathVariable int id,
    @RequestBody Vote updatedVote
  ) {
    Vote existingVote = pollManager.getVote(id);
    if (existingVote == null) {
      return ResponseEntity.notFound().build();
    }
    existingVote.setVoteOptionId(updatedVote.getVoteOptionId()); // Update the vote option
    pollManager.addVote(id, existingVote); // Save the updated vote
    return ResponseEntity.ok("Vote updated with ID: " + id);
  }

  // Endpoint to list all votes
  @GetMapping
  public ResponseEntity<?> listVotes() {
    return ResponseEntity.ok(pollManager.getAllVotes());
  }

  // Endpoint to delete a vote
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteVote(@PathVariable int id) {
    if (!pollManager.removeVote(id)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok("Vote deleted with ID: " + id);
  }
}
