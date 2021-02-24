package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElevatorManager {

  private final ElevatorService service;

  @Value("${com.tingco.elevator.default-floor:0}")
  private int defaultFloor;

  @Scheduled(fixedDelayString = "${com.tingco.elevator.elevator-recall-interval-ms:600000}", initialDelay = 10000)
  public void recallElevators() {
    log.debug("Beginning elevator recall routine. Elevators not in use will be recalled to floor: {}", defaultFloor);

    for (Elevator elevator : service.listElevators()) {
//      TODO fix me, error on recalling elevator
      if (!elevator.isInUse() && elevator.getCurrentFloor() != defaultFloor) {
        log.debug("Elevator with ID {} is not in use. Will be recalled to default floor.", elevator.getId());
        final var elevatorUpdateDto = new ElevatorUpdateDto();
        elevatorUpdateDto.setElevatorId(elevator.getId());
        elevatorUpdateDto.setCurrentFloor(defaultFloor);
        service.updateElevator(elevatorUpdateDto);
      }
    }
  }
}
