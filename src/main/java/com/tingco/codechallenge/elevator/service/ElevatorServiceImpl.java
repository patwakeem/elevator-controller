package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.exception.InvalidElevatorCallRequestException;
import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ElevatorServiceImpl implements ElevatorService {

  @Value("${com.tingco.elevator.top-floor}")
  private int topFloor;

  @Value("${com.tingco.elevator.bottom-floor}")
  private int bottomFloor;

  @Override
  public Elevator requestElevator(ElevatorCallDto dto) {
    validateCallRequestOrThrow(dto);
    return null;
  }

  @Override
  public List<Elevator> getElevators() {
    return null;
  }

  @Override
  public void releaseElevator(Elevator elevator) {

  }

  private void validateCallRequestOrThrow(ElevatorCallDto dto) {
    if (dto.getToFloor() < bottomFloor || dto.getToFloor() > topFloor) {
      throw new InvalidElevatorCallRequestException(
          String.format("Requested floor %s is out of range.", dto.getToFloor())
      );
    }
  }
}
