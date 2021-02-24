package com.tingco.codechallenge.elevator.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ElevatorResponseDto extends ResponseDto {

  private int elevatorId;

  public ElevatorResponseDto(String message, boolean success, int elevatorId) {
    super(message, success);
    this.elevatorId = elevatorId;
  }
}
