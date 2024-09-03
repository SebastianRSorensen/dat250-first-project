package FirstJavaSpring.HVL;

import java.time.Instant;

public class Vote {

  private Instant publishedAt;
  private int pollId; // Identifier for the poll this vote is associated with
  private int voteOptionId; // Identifier for the vote option chosen

  public Vote() {}

  public Instant getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Instant publishedAt) {
    this.publishedAt = publishedAt;
  }

  public int getPollId() {
    return pollId;
  }

  public void setPollId(int pollId) {
    this.pollId = pollId;
  }

  public int getVoteOptionId() {
    return voteOptionId;
  }

  public void setVoteOptionId(int voteOptionId) {
    this.voteOptionId = voteOptionId;
  }
}
