package FirstJavaSpring.HVL;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PollManager {

  private Map<Integer, User> users = new HashMap<>();
  private Map<Integer, Poll> polls = new HashMap<>();
  private Map<Integer, VoteOption> voteOptions = new HashMap<>();
  private Map<Integer, Vote> votes = new HashMap<>();

  public void addUser(Integer id, User user) {
    users.put(id, user);
  }

  public User getUser(Integer id) {
    return users.get(id);
  }

  public void addPoll(Integer id, Poll poll) {
    polls.put(id, poll);
  }

  public Poll getPoll(Integer id) {
    return polls.get(id);
  }

  public void addVoteOption(Integer id, VoteOption voteOption) {
    voteOptions.put(id, voteOption);
  }

  public VoteOption getVoteOption(Integer id) {
    return voteOptions.get(id);
  }

  public void addVote(Integer id, Vote vote) {
    votes.put(id, vote);
  }

  public Vote getVote(Integer id) {
    return votes.get(id);
  }
}
