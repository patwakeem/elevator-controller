package com.tingco.codechallenge.elevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ElevatorTest {

  @Test
  void elevatorDirectionIsResolvedCorrectly() {
    var downElevator = new Elevator(0, 1);
    var upElevator = new Elevator(0, 1);
    var noneElevator = new Elevator(0, 0);

    downElevator.addStop(-1);
    upElevator.addStop(10);

    assertEquals(Direction.DOWN, downElevator.getDirection());
    assertEquals(Direction.UP, upElevator.getDirection());
    assertEquals(Direction.NONE, noneElevator.getDirection());
  }

  @Test
  void elevatorNotInUseIsCorrectlyMarkedAsSuch() {
    var elevator = new Elevator(
        0,
        1
    );

    assertFalse(elevator.isInUse());
  }

  @Test
  void elevatorInUseIsCorrectlyMarkedAsSuch() {
    var elevator = new Elevator(
        0,
        0
    );
    elevator.addStop(1);

    assertTrue(elevator.isInUse());
  }

  @Test
  void addStopIgnoresAddWhenCurrentFloorIsStop() {
    var elevator = new Elevator(0, 0);

    elevator.addStop(0);

    assertTrue(elevator.getStopFloors().isEmpty());
  }

  @Test
  void elevatorIsUpdatedToFloorWhenItReachesNewFloor() {
    var elevator = new Elevator(0, 0);

    elevator.addStop(1);
    elevator.updateToFloor(1);

    assertTrue(elevator.getStopFloors().isEmpty());
  }
}