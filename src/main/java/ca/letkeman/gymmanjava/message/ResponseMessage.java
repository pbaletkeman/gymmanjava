package ca.letkeman.gymmanjava.message;

import java.util.StringJoiner;

public class ResponseMessage {
  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ResponseMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ResponseMessage.class.getSimpleName() + "[", "]")
        .add("message='" + message + "'")
        .toString();
  }
}
