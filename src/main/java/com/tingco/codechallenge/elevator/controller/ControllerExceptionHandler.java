package com.tingco.codechallenge.elevator.controller;

import com.tingco.codechallenge.elevator.exception.InvalidElevatorException;
import com.tingco.codechallenge.elevator.exception.InvalidElevatorFloorException;
import com.tingco.codechallenge.elevator.model.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = {InvalidElevatorFloorException.class})
  protected ResponseEntity<ResponseDto> handleException(InvalidElevatorFloorException ex,
      WebRequest request) {
    ResponseDto dto = new ResponseDto(ex.getMessage(), false);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
  }

  @ExceptionHandler(value = {InvalidElevatorException.class})
  protected ResponseEntity<ResponseDto> handleException(InvalidElevatorException ex,
      WebRequest request) {
    ResponseDto dto = new ResponseDto(ex.getMessage(), false);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
  }
}
