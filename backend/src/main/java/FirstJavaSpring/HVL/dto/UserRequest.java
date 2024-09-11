package FirstJavaSpring.HVL.dto;

public class UserRequest {

  private String userId;

  // Constructors
  public UserRequest() {}

  public UserRequest(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }
}
