package com.tingco.codechallenge.elevator.model.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tingco.codechallenge.elevator.model.Direction;
import com.tingco.codechallenge.elevator.util.DirectionResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ElevatorCallDto {

  private int toFloor;
  private int fromFloor;

  @JsonGetter("direction")
  private Direction getDirection() {
    return DirectionResolver.resolveDirectionFromFloors(fromFloor, toFloor);
  }
}
