package FirstJavaSpring.HVL.dto;

import FirstJavaSpring.HVL.Polls.*;
import java.util.Set;

public class PollRequest {

  private String question;
  private Set<VoteOption> options;
  private String creatorID;

  // Getters and setters
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

  public String getCreator() {
    return creatorID;
  }

  public void setCreator(String creatorID) {
    this.creatorID = creatorID;
  }
}
