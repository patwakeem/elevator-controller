package com.tingco.codechallenge.elevator.controller;

import com.tingco.codechallenge.elevator.exception.InvalidElevatorCallRequestException;
import com.tingco.codechallenge.elevator.model.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = {InvalidElevatorCallRequestException.class})
  protected ResponseEntity<ResponseDto> handleSendInvalidResultException(InvalidElevatorCallRequestException ex,
      WebRequest request) {
    ResponseDto dto = new ResponseDto(ex.getMessage(), false);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
  }
}
