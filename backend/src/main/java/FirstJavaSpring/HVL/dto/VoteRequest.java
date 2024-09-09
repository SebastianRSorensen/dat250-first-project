package FirstJavaSpring.HVL.dto;

import FirstJavaSpring.HVL.Polls.*;

public class VoteRequest {

  private String username;
  private VoteOption selectedOption;

  // Getters and setters
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public VoteOption getSelectedOption() {
    return selectedOption;
  }

  public void setSelectedOption(VoteOption selectedOption) {
    this.selectedOption = selectedOption;
  }
}
