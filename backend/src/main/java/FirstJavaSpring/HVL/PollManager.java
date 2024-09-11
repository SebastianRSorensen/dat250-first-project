package FirstJavaSpring.HVL;

import FirstJavaSpring.HVL.Polls.Poll;
import FirstJavaSpring.HVL.Polls.User;
import FirstJavaSpring.HVL.Polls.Vote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PollManager {

  private final Map<String, User> users = new HashMap<>();
  private final Map<String, Poll> polls = new HashMap<>();
  private final Map<String, String> usernameToIdMap = new HashMap<>();

  // Add a user to the manager
  public boolean addUser(User user) {
    String id = user.getId();
    String username = user.getUsername();
    if (users.containsKey(id)) {
      return false; // User already exists
    }
    if (usernameToIdMap.containsKey(username)) {
      return false;
    }
    users.put(id, user);
    usernameToIdMap.put(username, id);
    return true;
  }

  // Retrieve a user by username
  public User getUser(String username) {
    return users.get(username);
  }

  public User getUserById(String id) {
    return users.get(id);
  }

  // Retrieve all users
  public Map<String, User> getAllUsers() {
    return users;
  }

  // Add a poll to the manager
  public void addPoll(Poll poll) {
    polls.put(poll.getPollId(), poll);
  }

  // Retrieve a poll by ID
  public Poll getPollById(String pollId) {
    return polls.get(pollId);
  }

  // Retrieve all polls
  public Map<String, Poll> getAllPolls() {
    return polls;
  }

  public List<Vote> getAllVotes() {
    List<Vote> allVotes = new ArrayList<>();
    for (Poll poll : polls.values()) {
      allVotes.addAll(poll.getVotes().values());
    }
    return allVotes;
  }

  // Remove a poll by ID
  public void removePoll(String pollId) {
    polls.remove(pollId);
  }
}
