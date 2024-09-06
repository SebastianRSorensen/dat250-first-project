package FirstJavaSpring.HVL.controllers;

import FirstJavaSpring.HVL.Polls.User;
import FirstJavaSpring.HVL.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  // Endpoint to retrieve all users
  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  // Endpoint to retrieve a specific user by username
  @GetMapping("/{username}")
  public User getUserByUsername(@PathVariable String username) {
    User user = userService.getUserByUsername(username);
    if (user != null) {
      return user;
    } else {
      throw new RuntimeException("User not found");
    }
  }

  // Endpoint to add a new user
  @PostMapping
  public User createUser(@RequestBody User user) {
    userService.addUser(user);
    return user;
  }
}
