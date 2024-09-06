package FirstJavaSpring.HVL.service;

import FirstJavaSpring.HVL.Polls.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private List<User> users = new ArrayList<>();

  // Method to retrieve all users
  public List<User> getAllUsers() {
    return users;
  }

  // Method to retrieve a specific user by username
  public User getUserByUsername(String username) {
    return users
      .stream()
      .filter(user -> user.getUsername().equalsIgnoreCase(username))
      .findFirst()
      .orElse(null);
  }

  // Method to add a user
  public void addUser(User user) {
    if (user.getCreatedPolls() == null) {
      user.setCreatedPolls(new ArrayList<>());
    }
    if (user.getCastVotes() == null) {
      user.setCastVotes(new ArrayList<>());
    }
    users.add(user);
  }
}
