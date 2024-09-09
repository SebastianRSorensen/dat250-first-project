package FirstJavaSpring.HVL.dto;

public class UserRequest {

  private String username;

  // Constructors
  public UserRequest() {}

  public UserRequest(String username) {
    this.username = username;
  }

  // Getter and Setter
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
