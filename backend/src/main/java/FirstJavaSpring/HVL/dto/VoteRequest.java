package FirstJavaSpring.HVL.dto;

public class VoteRequest {

  private String userId;
  private String selectedOption;
  private boolean isUpVote;

  public VoteRequest() {}

  public VoteRequest(String userId, String selectedOption, boolean isUpVote) {
    this.userId = userId;
    this.selectedOption = selectedOption;
    this.isUpVote = isUpVote;
  }

  // Getters and setters
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSelectedOption() {
    return selectedOption;
  }

  public void setSelectedOption(String selectedOption) {
    this.selectedOption = selectedOption;
  }

  public boolean getIsUpVote() {
    return isUpVote;
  }
}
