package me.kopz.starseeker.entity.dto;

import me.kopz.starseeker.exception.AppException;

import java.io.Serial;
import java.io.Serializable;

public class AppExceptionDTO implements Serializable {

  @Serial
  public static final long serialVearsionUID = 1L;

  private String message;
  private Integer statusCode;


  public AppExceptionDTO(String message, Integer statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public AppExceptionDTO(AppException appException) {
    this.message = appException.getMessage();
    this.statusCode = appException.getStatusCode().value();
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }
}