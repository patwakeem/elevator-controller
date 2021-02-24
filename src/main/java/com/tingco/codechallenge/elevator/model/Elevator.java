package com.tingco.codechallenge.elevator.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tingco.codechallenge.elevator.util.DirectionResolver;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Elevator {

  private int id;
  private int currentFloor;
  private final List<Integer> stopFloors = new ArrayList<>();

  @JsonGetter("in_use")
  public boolean isInUse() {
    return !stopFloors.isEmpty();
  }

  @JsonGetter("direction")
  public Direction getDirection() {
    if (stopFloors.isEmpty()) {
      return Direction.NONE;
    }
    return DirectionResolver.resolveDirectionFromFloors(currentFloor, stopFloors.get(0));
  }

  public void addStop(int floor) {
    if (floor != currentFloor) {
      stopFloors.add(floor);
    }
  }

  public void updateToFloor(int floor) {
    if (stopFloors.get(0) == floor) {
      stopFloors.remove(0);
    }
    currentFloor = floor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Elevator elevator = (Elevator) o;
    return id == elevator.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
