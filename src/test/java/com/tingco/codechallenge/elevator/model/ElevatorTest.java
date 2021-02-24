package com.tingco.codechallenge.elevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ElevatorTest {

  @Test
  void elevatorDirectionIsResolvedCorrectly() {
    assertEquals(Direction.DOWN, new Elevator(0, 1).getDirection());
    assertEquals(Direction.UP, new Elevator(0, 1).getDirection());
    assertEquals(Direction.NONE, new Elevator(0, 1).getDirection());
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
        1
    );

    assertTrue(elevator.isInUse());
  }
}