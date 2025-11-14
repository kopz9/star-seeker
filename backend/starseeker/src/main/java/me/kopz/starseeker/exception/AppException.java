package me.kopz.starseeker.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class AppException extends Exception {

  @Serial
  public static final long serialVersionUID = 1L;

  private HttpStatus statusCode;

  public AppException() {
  }

  public AppException(String message, HttpStatus statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(HttpStatus statusCode) {
    this.statusCode = statusCode;
  }
}
