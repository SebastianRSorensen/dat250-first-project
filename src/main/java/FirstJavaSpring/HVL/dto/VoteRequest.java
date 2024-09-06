package FirstJavaSpring.HVL.dto;

import FirstJavaSpring.HVL.Polls.*;

public class VoteRequest {

  private User voter;
  private VoteOption selectedOption;

  // Getters and setters
  public User getVoter() {
    return voter;
  }

  public void setVoter(User voter) {
    this.voter = voter;
  }

  public VoteOption getSelectedOption() {
    return selectedOption;
  }

  public void setSelectedOption(VoteOption selectedOption) {
    this.selectedOption = selectedOption;
  }
}
