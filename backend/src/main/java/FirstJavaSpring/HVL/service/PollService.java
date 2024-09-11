package FirstJavaSpring.HVL.service;

import FirstJavaSpring.HVL.PollManager;
import FirstJavaSpring.HVL.Polls.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollService {

  @Autowired
  private PollManager pollManager;

  // Add a poll
  public void addPoll(Poll poll) {
    pollManager.addPoll(poll);
  }

  // Retrieve all polls
  public List<Poll> getAllPolls() {
    return pollManager
      .getAllPolls()
      .values()
      .stream()
      .collect(Collectors.toList());
  }

  // Find poll by ID
  public Poll findPollById(String pollId) {
    return pollManager.getPollById(pollId);
  }

  public void castVote(String userId, Poll poll, VoteOption selectedOption) {
    Vote vote = new Vote(userId, poll, selectedOption);
    poll.addVote(userId, vote);
  }

  // Delete a poll
  public void deletePoll(String pollId) {
    pollManager.getAllPolls().remove(pollId);
  }
}
