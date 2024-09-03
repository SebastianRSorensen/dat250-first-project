package FirstJavaSpring.HVL;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PollController {

  @Autowired
  private PollManager pollManager;

  @PostMapping("/polls")
  public ResponseEntity<String> createPoll(@RequestBody Poll poll) {
    int id = poll.hashCode();
    pollManager.addPoll(id, poll);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body("Poll created with ID: " + id);
  }

  @GetMapping("/polls/{id}")
  public Poll getPoll(@PathVariable int id) {
    return pollManager.getPoll(id);
  }

  @GetMapping("/polls")
  public Collection<Poll> getAllPolls() {
    return pollManager.getAllPolls();
  }
}
