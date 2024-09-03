package FirstJavaSpring.HVL;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users") // Base path for all methods in this controller
public class UserController {

  @Autowired
  private PollManager pollManager;

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    int userId = user.hashCode();
    pollManager.addUser(userId, user);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<User>> listAllUsers() {
    List<User> users = new ArrayList<>(pollManager.getAllUsers().values());
    return new ResponseEntity<>(users, HttpStatus.OK);
  }
}
