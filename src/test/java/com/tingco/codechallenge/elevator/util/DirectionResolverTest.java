package com.tingco.codechallenge.elevator.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tingco.codechallenge.elevator.model.Direction;
import org.junit.jupiter.api.Test;

class DirectionResolverTest {

  @Test
  void elevatorDirectionIsResolvedCorrectly() {
    assertEquals(Direction.DOWN, DirectionResolver.resolveDirectionFromFloors(1, -2));
    assertEquals(Direction.UP, DirectionResolver.resolveDirectionFromFloors(1, 10));
    assertEquals(Direction.NONE, DirectionResolver.resolveDirectionFromFloors(1, 1));
  }
}