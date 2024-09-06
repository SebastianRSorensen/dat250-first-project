package FirstJavaSpring.HVL;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Lists;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public class Polls {

  public static class User {

    private String username;
    private String email;

    @JsonManagedReference("poll-user") // User manages the relationship with Poll
    private List<Poll> createdPolls;

    @JsonManagedReference("vote-user") // User manages the relationship with Vote
    private List<Vote> castVotes;

    public User() {}

    public User(String username, String email) {
      this.username = username;
      this.email = email;
      this.createdPolls = Lists.newArrayList();
      this.castVotes = Lists.newArrayList();
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public List<Poll> getCreatedPolls() {
      return createdPolls;
    }

    public void setCreatedPolls(List<Poll> createdPolls) {
      this.createdPolls = createdPolls;
    }

    // Getter and setter for cast votes
    public List<Vote> getCastVotes() {
      return castVotes;
    }

    public void setCastVotes(List<Vote> castVotes) {
      this.castVotes = castVotes;
    }

    public void addVote(Vote vote) {
      this.castVotes.add(vote);
    }
  }

  public static class Poll {

    private String question;
    private Set<VoteOption> options;

    @JsonBackReference("poll-user") // Poll is the child and refers back to User
    private User creator;

    @JsonManagedReference("poll-vote") // Poll manages the relationship with Vote
    private List<Vote> votes;

    private Instant closesAt;

    public Poll(String question, Set<VoteOption> options, User creator) {
      this.question = question;
      this.options = options;
      this.creator = creator;
      this.votes = Lists.newArrayList();
      //creator.getCreatedPolls().add(this);  // this line is dangerous...
    }

    public Instant getClosesAt() {
      return closesAt;
    }

    public void setClosesAt(Instant closesAt) {
      this.closesAt = closesAt;
    }

    public String getQuestion() {
      return question;
    }

    public void setQuestion(String question) {
      this.question = question;
    }

    public Set<VoteOption> getOptions() {
      return options;
    }

    public void setOptions(Set<VoteOption> options) {
      this.options = options;
    }

    public User getCreator() {
      return creator;
    }

    public void setCreator(User creator) {
      this.creator = creator;
    }

    // Getter and setter for votes
    public List<Vote> getVotes() {
      return votes;
    }

    public void addVote(Vote vote) {
      this.votes.add(vote);
    }
  }

  public static class VoteOption {

    private int order;

    private String caption;

    public VoteOption(int order, String caption) {
      this.order = order;
      this.caption = caption;
    }

    public VoteOption() {}

    public int getOrder() {
      return order;
    }

    public void setOrder(int order) {
      this.order = order;
    }

    public String getCaption() {
      return caption;
    }

    public void setCaption(String caption) {
      this.caption = caption;
    }
  }

  public static class Vote {

    @JsonBackReference("vote-user") // Vote is the child and refers back to User
    private User voter;

    @JsonBackReference("poll-vote") // Vote is the child and refers back to Poll
    private Poll poll;

    private VoteOption selectedOption;
    private Instant voteTime;

    public Vote(User voter, Poll poll, VoteOption selectedOption) {
      this.voter = voter;
      this.poll = poll;
      this.selectedOption = selectedOption;
      this.voteTime = Instant.now(); // Record the time the vote was cast
    }

    public User getVoter() {
      return voter;
    }

    public void setVoter(User voter) {
      this.voter = voter;
    }

    public Poll getPoll() {
      return poll;
    }

    public void setPoll(Poll poll) {
      this.poll = poll;
    }

    public VoteOption getSelectedOption() {
      return selectedOption;
    }

    public void setSelectedOption(VoteOption selectedOption) {
      this.selectedOption = selectedOption;
    }

    public Instant getVoteTime() {
      return voteTime;
    }

    public void setVoteTime(Instant voteTime) {
      this.voteTime = voteTime;
    }

    public void castVote(User voter, Poll poll, VoteOption selectedOption) {
      // Create a new vote
      Vote vote = new Vote(voter, poll, selectedOption);

      // Add the vote to the poll and the user
      poll.addVote(vote);
      voter.addVote(vote);
    }
  }
}
