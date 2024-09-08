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
  private final Map<Long, Poll> polls = new HashMap<>();

  // Add a user to the manager
  public boolean addUser(User user) {
    String username = user.getUsername();
    if (users.containsKey(username)) {
      return false; // User already exists
    }
    users.put(username, user);
    return true;
  }

  // Retrieve a user by username
  public User getUser(String username) {
    return users.get(username);
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
  public Poll getPollById(Long pollId) {
    return polls.get(pollId);
  }

  // Retrieve all polls
  public Map<Long, Poll> getAllPolls() {
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
  public void removePoll(Long pollId) {
    polls.remove(pollId);
  }
}
