package com.tingco.codechallenge.elevator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tingco.codechallenge.elevator.model.Direction;
import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import java.util.List;
import org.junit.jupiter.api.Test;

class ElevatorResolverTest {

  @Test
  void elevatorResolverFindsElevatorGoingInSameDirectionOnSameFloor() {
    var elevator = new Elevator(0, 0);
    var elevatorTwo = new Elevator(1, 0);
    var elevatorThree = new Elevator(2, 0);
    elevatorThree.addStop(5);
    elevator.addStop(-10);
    elevator.addStop(-10);

    var found = new ElevatorResolver().quicklyFindElevatorIfPossible(
        List.of(elevatorThree, elevatorTwo, elevator),
        Direction.UP,
        List.of(elevatorThree, elevatorTwo, elevator)
    );

    assertEquals(elevatorThree, found);
  }

  @Test
  void elevatorResolverFindsElevatorNotInUse() {
    var elevator = new Elevator(0, 0);
    var elevatorTwo = new Elevator(1, 0);
    var elevatorThree = new Elevator(2, 0);
    elevatorThree.addStop(1);
    elevator.addStop(10);

    var found = new ElevatorResolver().findBestElevator(
        List.of(elevatorThree, elevatorTwo, elevator),
        new ElevatorCallDto()
    );

    assertEquals(elevatorTwo, found);
  }

  @Test
  void elevatorResolverFindsCloseElevator() {
    var elevatorNeg = new Elevator(0, -1000);
    var elevator = new Elevator(1, 0);
    var elevatorTwo = new Elevator(2, 500);
    var elevatorThree = new Elevator(3, 1000);
    elevatorThree.addStop(10);
    elevator.addStop(10);
    elevatorTwo.addStop(10);
    elevatorNeg.addStop(-800);

    var found = new ElevatorResolver().findBestElevator(
        List.of(elevatorThree, elevatorTwo, elevator, elevatorNeg),
        new ElevatorCallDto(50, 10)
    );

    assertEquals(found, elevator);
  }
}