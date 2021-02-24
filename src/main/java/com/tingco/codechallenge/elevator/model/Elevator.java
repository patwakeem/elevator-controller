package com.tingco.codechallenge.elevator.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Elevator {

  private int id;
  private int currentFloor;
  private int targetFloor;
  private Direction direction;

  @JsonGetter("in_use")
  public boolean isInUse() {
    return currentFloor == targetFloor;
  }
//
//
//  /**
//   * Command to move the elevator to the given floor.
//   *
//   * @param toFloor int where to go.
//   */
//  void moveElevator(int toFloor);

}
