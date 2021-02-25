package com.tingco.codechallenge.elevator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tingco.codechallenge.elevator.configuration.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.exception.InvalidElevatorException;
import com.tingco.codechallenge.elevator.exception.InvalidElevatorFloorException;
import com.tingco.codechallenge.elevator.metrics.MetricTags;
import com.tingco.codechallenge.elevator.metrics.MetricsRecorder;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import com.tingco.codechallenge.elevator.model.dto.ElevatorUpdateDto;
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
class ElevatorServiceImplTest {

  private ElevatorServiceImpl service;

  @Mock
  private MetricsRecorder recorder;

  @BeforeEach
  void setUp() {
    var config = new ElevatorConfiguration();
    config.setNumberOfElevators(2);
    config.setTopFloor(10);
    config.setBottomFloor(-10);
    config.setDefaultFloor(0);
    final var elevatorMap = config.constructElevatorMap();
    service = new ElevatorServiceImpl(
        elevatorMap,
        config.constructElevatorOnFloorMap(elevatorMap),
        recorder
    );
    service.setBottomFloor(-10);
    service.setTopFloor(10);
  }

  @Test
  void requestElevatorTestGoingUp() {
    final var callDto = new ElevatorCallDto();
    callDto.setCurrentFloor(0);
    callDto.setTargetFloor(5);

//    Call elevator on floor 0 twice. Should give us both of our elevators.
    final var elevator = service.requestElevator(callDto);
    service.updateElevatorLocation(new ElevatorUpdateDto(elevator.getId(), 1));
    final var elevatorTwo = service.requestElevator(callDto);
//    Update the location of our elevators to floor 1 and 2 (both are in use).
    service.updateElevatorLocation(new ElevatorUpdateDto(elevatorTwo.getId(), 1));
    service.updateElevatorLocation(new ElevatorUpdateDto(elevatorTwo.getId(), 2));
//    Call elevator on floor 1 going up to floor 6. Should return elevator 0 (it is still on floor 1)
    final var elevatorThree = service.requestElevator(new ElevatorCallDto(6, 1));

    assertEquals(0, elevator.getId());
    assertEquals(1, elevatorTwo.getId());
    assertEquals(elevator, elevatorThree);
  }

  @Test
  void requestElevatorTestGoingDown() {
    final var callDto = new ElevatorCallDto();
    callDto.setCurrentFloor(0);
    callDto.setTargetFloor(-5);

//    Call elevator on floor 0 twice. Should give us both of our elevators.
    final var elevator = service.requestElevator(callDto);
    service.updateElevatorLocation(new ElevatorUpdateDto(elevator.getId(), -1));
    final var elevatorTwo = service.requestElevator(callDto);
//    Update the location of our elevators to floor 1 and 2 (both are in use).
    service.updateElevatorLocation(new ElevatorUpdateDto(elevatorTwo.getId(), -1));
    service.updateElevatorLocation(new ElevatorUpdateDto(elevatorTwo.getId(), -2));
//    Call elevator on floor 1 going up to floor 6. Should return elevator 0 (it is still on floor 1)
    final var elevatorThree = service.requestElevator(new ElevatorCallDto(-6, -1));

    assertEquals(0, elevator.getId());
    assertEquals(1, elevatorTwo.getId());
    assertEquals(elevator, elevatorThree);
  }

  @Test
  void updateElevatorLocationTests() {
    var elevatorId = 1;
    var updateDto = new ElevatorUpdateDto();
    updateDto.setElevatorId(1);
    updateDto.setCurrentFloor(3);
    final var elevator = service.listElevators().get(elevatorId);
    elevator.getStopFloors().add(5);

    service.updateElevatorLocation(updateDto);

    assertEquals(elevator.getCurrentFloor(), 3);
  }

  @Test
  void updateElevatorThrowsExceptionOnInvalidElevator() {
    var updateDto = new ElevatorUpdateDto();
    updateDto.setElevatorId(100);
    updateDto.setCurrentFloor(3);

    assertThrows(InvalidElevatorException.class, () -> service.updateElevatorLocation(updateDto));
  }

  @Test
  void updateElevatorThrowsExceptionOnInvalidFloor() {
    var updateDto = new ElevatorUpdateDto();
    updateDto.setElevatorId(100);
    updateDto.setCurrentFloor(-80);

    assertThrows(InvalidElevatorFloorException.class, () -> service.updateElevatorLocation(updateDto));
  }

  @Test
  void recallElevatorTests() {
    var recallFloor = 2;
    var elevatorId = 1;
    final var elevator = service.listElevators().get(elevatorId);

    service.recallElevatorToFloor(elevatorId, recallFloor);

    assertTrue(elevator.getStopFloors().contains(recallFloor));
    Mockito.verify(recorder).incrementCounter(MetricTags.ELEVATORS_RECALLED);
  }
}