package com.tingco.codechallenge.elevator.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import com.tingco.codechallenge.elevator.model.dto.ElevatorUpdateDto;
import com.tingco.codechallenge.elevator.service.ElevatorService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ElevatorControllerTest {

  private ElevatorController controller;

  @Mock
  private ElevatorService service;

  @BeforeEach
  void setUp() {
    controller = new ElevatorController(service);
  }

  @Test
  void callElevatorTests() {
    final var elevator = new Elevator(0, 1);
    final var elevatorCallDto = new ElevatorCallDto();
    Mockito.doReturn(elevator).when(service).requestElevator(elevatorCallDto);

    final var response = controller.callElevator(elevatorCallDto);

    assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    assertEquals(elevator.getId(), response.getBody().getElevatorId());
    Mockito.verify(service).requestElevator(elevatorCallDto);
  }

  @Test
  void updateElevatorTests() {
    final var elevatorUpdateDto = new ElevatorUpdateDto();
    elevatorUpdateDto.setElevatorId(1);

    final var response = controller.updateElevator(elevatorUpdateDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(elevatorUpdateDto.getElevatorId(), response.getBody().getElevatorId());
    Mockito.verify(service).updateElevator(elevatorUpdateDto);
  }

  @Test
  void listElevatorsTests() {
    final var elevatorOne = new Elevator(1, 1);
    final var elevatorTwo = new Elevator(1, 1);
    Mockito.doReturn(List.of(elevatorOne, elevatorTwo)).when(service).listElevators();

    final var response = controller.listElevators();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
    assertTrue(response.getBody().contains(elevatorTwo));
    assertTrue(response.getBody().contains(elevatorOne));
    Mockito.verify(service).listElevators();
  }
}