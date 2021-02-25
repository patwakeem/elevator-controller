package com.tingco.codechallenge.elevator.util;

import com.tingco.codechallenge.elevator.model.Direction;

public class DirectionResolver {

  public static Direction resolveDirectionFromFloors(int currentFloor, int targetFloor) {
    if (currentFloor > targetFloor) {
      return Direction.DOWN;
    } else if (currentFloor < targetFloor) {
      return Direction.UP;
    }

    return Direction.NONE;
  }
}
