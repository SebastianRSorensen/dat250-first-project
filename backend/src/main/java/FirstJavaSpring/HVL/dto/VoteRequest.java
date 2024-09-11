package FirstJavaSpring.HVL.dto;

import FirstJavaSpring.HVL.Polls.*;

public class VoteRequest {

  private String userId;
  private VoteOption selectedOption;

  public VoteRequest() {}

  public VoteRequest(String userId, VoteOption selectedOption) {
    this.userId = userId;
    this.selectedOption = selectedOption;
  }

  // Getters and setters
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public VoteOption getSelectedOption() {
    return selectedOption;
  }

  public void setSelectedOption(VoteOption selectedOption) {
    this.selectedOption = selectedOption;
  }
}
