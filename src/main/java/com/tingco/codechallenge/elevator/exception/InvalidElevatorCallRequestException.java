package com.tingco.codechallenge.elevator.exception;

public class InvalidElevatorCallRequestException extends RuntimeException {
  public InvalidElevatorCallRequestException(String message) {
    super(message);
  }
}
