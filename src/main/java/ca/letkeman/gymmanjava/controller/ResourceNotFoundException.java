package ca.letkeman.gymmanjava.controller;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException{
  private String message;
  private HttpStatus status;

  public ResourceNotFoundException( String message, HttpStatus status) {
    super(message);
    this.message = message;
    this.status = status;
  }

  public ResourceNotFoundException( String message) {
    super(message);
    this.message = message;
    this.status = HttpStatus.NOT_FOUND;
  }

  public ResourceNotFoundException(){

  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }
}