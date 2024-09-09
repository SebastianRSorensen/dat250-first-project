package FirstJavaSpring.HVL.controllers;

import FirstJavaSpring.HVL.Polls.User;
import FirstJavaSpring.HVL.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  // Endpoint to add a new user
  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody User user) {
    Boolean success = userService.addUser(user);
    if (success) {
      return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
  }

  // Endpoint to retrieve all users
  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  // Endpoint to retrieve a specific user by username
  // TODO: Specify the type better
  @GetMapping("/{username}")
  public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
    User user = userService.getUserByUsername(username);
    if (user != null) {
      return new ResponseEntity<>(user, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
  }
}
