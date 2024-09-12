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

  public void castVote(
    String userId,
    String pollId,
    String selectedOption,
    boolean isUpVote
  ) {
    Vote vote = new Vote(userId, pollId, selectedOption, isUpVote);
    Poll poll = pollManager.getPollById(pollId);

    if (poll.hasVoted(userId)) {
      return;
    }

    poll.addVote(userId, vote);
    VoteOption voteOption = pollManager.getPollOption(pollId, selectedOption);
    if (isUpVote) {
      voteOption.incrementVoteCount();
    } else {
      voteOption.decrementVoteCount();
    }
  }

  // Delete a poll
  public void deletePoll(String pollId) {
    pollManager.getAllPolls().remove(pollId);
  }

  // Poll has option
  public boolean pollHasOption(String pollId, String optionId) {
    return pollManager.pollHasOption(pollId, optionId);
  }

  // Return poll option of specific poll option ID
  public VoteOption getPollOption(String pollId, String optionId) {
    return pollManager.getPollOption(pollId, optionId);
  }
}
