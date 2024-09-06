package FirstJavaSpring.HVL.service;

import FirstJavaSpring.HVL.Polls.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PollService {

  private List<Poll> polls = new ArrayList<>();

  // Method to add a poll to the list
  public void addPoll(Poll poll) {
    polls.add(poll);
  }

  // Method to retrieve all polls
  public List<Poll> getAllPolls() {
    return polls;
  }

  public void castVote(User voter, Poll poll, VoteOption selectedOption) {
    // Check if the user has already voted in the poll
    Vote existingVote = null;
    for (Vote vote : poll.getVotes()) {
      if (vote.getVoter().equals(voter)) {
        existingVote = vote;
        break;
      }
    }

    // Remove existing vote if found
    if (existingVote != null) {
      poll.getVotes().remove(existingVote);
      voter.getCastVotes().remove(existingVote);
    }

    // Create a new vote and add it
    Vote newVote = new Vote(voter, poll, selectedOption);
    poll.addVote(newVote);
    voter.addVote(newVote);
  }
}
