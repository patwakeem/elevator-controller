package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.exception.InvalidElevatorException;
import com.tingco.codechallenge.elevator.exception.InvalidElevatorFloorException;
import com.tingco.codechallenge.elevator.metrics.MetricTags;
import com.tingco.codechallenge.elevator.metrics.MetricsRecorder;
import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import com.tingco.codechallenge.elevator.model.dto.ElevatorUpdateDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElevatorServiceImpl implements ElevatorService {

  @Value("${com.tingco.elevator.top-floor}")
  private int topFloor;

  @Value("${com.tingco.elevator.bottom-floor}")
  private int bottomFloor;

  private final Map<Integer, Elevator> elevatorMap;
  private final Map<Integer, List<Elevator>> elevatorOnFloorMap;
  private final MetricsRecorder recorder;

  @Override
  public Elevator requestElevator(ElevatorCallDto dto) {
    validateFloor(dto.getTargetFloor());
    recorder.incrementCounter(MetricTags.ELEVATORS_CALLED);
    final var resolver = new ElevatorResolver();
    final var elevators = listElevators();

    var foundElevator = resolver.quicklyFindElevatorIfPossible(
        elevatorOnFloorMap.get(dto.getCurrentFloor()), dto.getDirection(), elevators
    );

    if (foundElevator == null) {
      foundElevator = resolver.findBestElevator(elevators, dto);
    }

//  If our requester is on another floor we have to pick them up first.
//  Then we can go where they want.
    if (foundElevator.getCurrentFloor() != dto.getCurrentFloor()) {
      foundElevator.addStop(dto.getCurrentFloor());
    }

    foundElevator.addStop(dto.getTargetFloor());
    return foundElevator;
  }

  @Override
  public List<Elevator> listElevators() {
    return new ArrayList<>(elevatorMap.values());
  }

  @Override
  public void updateElevatorLocation(ElevatorUpdateDto updateDto) {
    validateFloor(updateDto.getCurrentFloor());
    validateElevator(updateDto.getElevatorId());

    final var elevator = elevatorMap.get(updateDto.getElevatorId());
    elevatorOnFloorMap.get(elevator.getCurrentFloor()).remove(elevator);
    elevator.updateToFloor(updateDto.getCurrentFloor());
    elevatorOnFloorMap.get(updateDto.getCurrentFloor()).add(elevator);
    recorder.incrementCounter(MetricTags.ELEVATORS_UPDATED);
  }

  @Override
  public void recallElevatorToFloor(int elevatorId, int floor) {
    final var elevator = elevatorMap.get(elevatorId);
    elevator.addStop(floor);
    recorder.incrementCounter(MetricTags.ELEVATORS_RECALLED);
  }

  private void validateElevator(int elevatorId) {
    if (elevatorId > elevatorMap.size()) {
      recorder.incrementCounter(MetricTags.ELEVATOR_INVALID);
      throw new InvalidElevatorException("Could not find elevator with ID: " + elevatorId);
    }
  }

  private void validateFloor(int floor) {
    if (floor < bottomFloor || floor > topFloor) {
      recorder.incrementCounter(MetricTags.FLOOR_INVALID);
      throw new InvalidElevatorFloorException(
          String.format("Requested floor %s is out of range.", floor)
      );
    }
  }

  protected void setTopFloor(int topFloor) {
    this.topFloor = topFloor;
  }

  protected void setBottomFloor(int bottomFloor) {
    this.bottomFloor = bottomFloor;
  }
}
