package FirstJavaSpring.HVL.service;

import FirstJavaSpring.HVL.PollManager;
import FirstJavaSpring.HVL.Polls.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private PollManager pollManager;

  // Method to retrieve all users
  public List<User> getAllUsers() {
    return pollManager
      .getAllUsers()
      .values()
      .stream()
      .collect(Collectors.toList());
  }

  // Method to retrieve a specific user by username
  public User getUserByUsername(String username) {
    return pollManager.getUser(username);
  }

  // Method to add a user
  public boolean addUser(User user) {
    return pollManager.addUser(user);
  }
}
