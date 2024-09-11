package FirstJavaSpring.HVL;

import FirstJavaSpring.HVL.Polls.Vote;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.collect.Lists;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Polls {

  @JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
  )
  public static class User {

    private final String id = UUID.randomUUID().toString();
    private String username;
    private String email;

    @JsonIgnore // Prevents the map from being serialized/deserialized
    private final Map<String, Poll> polls = new HashMap<>();

    public User() {}

    public User(String username, String email) {
      this.username = username;
      this.email = email;
    }

    public String getId() {
      return id;
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

    // expose polls when needed
    @JsonProperty("polls")
    public List<Poll> getPolls() {
      return Lists.newArrayList(polls.values());
    }

    public void addPoll(Poll poll) {
      this.polls.put(poll.getPollId(), poll);
    }
  }

  @JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "pollId"
  )
  public static class Poll {

    private final String pollId = UUID.randomUUID().toString();
    private String question;
    private Set<VoteOption> options;

    @JsonIdentityReference(alwaysAsId = true) // Only serialize the creator's ID
    private String creatorId;

    @JsonManagedReference // Manage votes reference in Poll
    private Map<String, Vote> votes = new HashMap<>();

    private final Instant createdAt = Instant.now();
    private Instant closesAt;

    // Default constructor (required for deserialization)
    public Poll() {
      this.options = new HashSet<>();
      this.closesAt = Instant.now().plusSeconds(60 * 60 * 24); // Default to 24 hours
    }

    public Poll(String question, Set<VoteOption> options, String creatorId) {
      this.question = question;
      this.options = (options != null) ? options : new HashSet<>();
      this.creatorId = creatorId;
      this.closesAt = Instant.now().plusSeconds(60 * 60 * 24); // Default to 24 hours
    }

    // Overloaded constructor to set a custom closing time
    // TODO: Implement the functionality to set a custom closing time
    public Poll(
      String question,
      Set<VoteOption> options,
      String creatorId,
      Instant closesAt
    ) {
      this.question = question;
      this.options = (options != null) ? options : new HashSet<>();
      this.creatorId = creatorId;
      this.closesAt = closesAt;
    }

    public Instant getCreatedAt() {
      return createdAt;
    }

    public String getPollId() {
      return pollId;
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

    public void addOption(VoteOption option) {
      this.options.add(option);
    }

    public String getCreator() {
      return creatorId;
    }

    public void setCreator(String creatorId) {
      this.creatorId = creatorId;
    }

    // Refactored: Getter and setter for votes
    public Map<String, Vote> getVotes() {
      return votes;
    }

    // Add or update a vote in the map (vote cast or changed)
    public void addVote(String voterId, Vote vote) {
      this.votes.put(voterId, vote);
    }

    public boolean hasVoted(String voter) {
      return this.votes.containsKey(voter); // Check if the user has already voted
    }
  }

  public static class VoteOption {

    private final String id = UUID.randomUUID().toString();
    private int presentationOrder;

    private String caption;

    public VoteOption(int presentationOrder, String caption) {
      this.presentationOrder = presentationOrder;
      this.caption = caption;
    }

    public VoteOption() {}

    public String getVoteOptionId() {
      return id;
    }

    public int getPresentationOrder() {
      return presentationOrder;
    }

    public void setPresentationOrder(int presentationOrder) {
      this.presentationOrder = presentationOrder;
    }

    public String getCaption() {
      return caption;
    }

    public void setCaption(String caption) {
      this.caption = caption;
    }
  }

  public static class Vote {

    private String voter;

    @JsonBackReference // Prevent infinite recursion between Poll and Vote
    private Poll poll;

    private final String voteId = UUID.randomUUID().toString();
    private VoteOption selectedOption;
    private Instant voteTime;

    // Default constructor (required for deserialization)
    public Vote() {}

    public Vote(String voter, Poll poll, VoteOption selectedOption) {
      this.voter = voter;
      this.poll = poll;
      this.selectedOption = selectedOption;
      this.voteTime = Instant.now(); // Record the time the vote was cast
    }

    public String getVoteId() {
      return voteId;
    }

    public String getVoterId() {
      return voter;
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
  }
}
