package me.kopz.starseeker.controller.advice;

import me.kopz.starseeker.entity.dto.AppExceptionDTO;
import me.kopz.starseeker.exception.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(AppException.class)
  public ResponseEntity<AppExceptionDTO> handleAppException(AppException appException){
    return ResponseEntity.status(appException.getStatusCode()).body(new AppExceptionDTO(appException));
  }
}