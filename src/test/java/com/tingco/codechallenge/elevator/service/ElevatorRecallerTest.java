package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.model.Elevator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ElevatorRecallerTest {

  private ElevatorRecaller manager;

  @Mock
  private ElevatorService service;

  @BeforeEach
  void setUp() {
    manager = new ElevatorRecaller(service);
  }

  @Test
  void recallElevatorsTests() {
    final var defaultFloor = 5;
    final var elevatorOne = new Elevator(0, 1);
    final var elevatorTwo = new Elevator(1, 1);
    final var elevatorThree = new Elevator(2, 5);
    manager.setDefaultFloor(defaultFloor);
    Mockito.doReturn(List.of(elevatorOne, elevatorTwo, elevatorThree)).when(service).listElevators();

    manager.recallElevators();

    Mockito.verify(service).recallElevatorToFloor(0, defaultFloor);
    Mockito.verify(service).recallElevatorToFloor(1, defaultFloor);
    Mockito.verify(service, Mockito.times(2)).recallElevatorToFloor(
        Mockito.anyInt(),
        Mockito.anyInt()
    );
  }
}