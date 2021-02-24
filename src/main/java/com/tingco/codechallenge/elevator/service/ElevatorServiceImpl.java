package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.exception.InvalidElevatorException;
import com.tingco.codechallenge.elevator.exception.InvalidElevatorFloorException;
import com.tingco.codechallenge.elevator.metrics.MetricTags;
import com.tingco.codechallenge.elevator.metrics.MetricsRecorder;
import com.tingco.codechallenge.elevator.model.Direction;
import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.ElevatorWithScore;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import com.tingco.codechallenge.elevator.model.dto.ElevatorUpdateDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    Elevator foundElevator = getElevatorGoingInSameDirection(
        elevatorOnFloorMap.get(dto.getCurrentFloor()), dto.getDirection()
    );

    if (foundElevator == null) {
      foundElevator = findBestElevator(dto);
    }

//  If our requester is on another floor we have to pick them up first.
//  Then we can go where they want.
    if (foundElevator.getCurrentFloor() != dto.getCurrentFloor()) {
      foundElevator.addStop(dto.getCurrentFloor());
    }

    foundElevator.addStop(dto.getTargetFloor());
    recorder.incrementCounter(MetricTags.ELEVATORS_CALLED);
    return foundElevator;
  }

  private Elevator findBestElevator(ElevatorCallDto callDto) {
//    find any elevators not in use. if found return immediately
    final var elevators = listElevators();
    final var elevatorOpt = findElevatorNotInUse(elevators);

    if (elevatorOpt.isPresent()) {
      return elevatorOpt.get();
    }

//    find an elevator that is reasonably close and doesn't have too many other stops
    List<ElevatorWithScore> elevatorWithScores = new ArrayList<>();

    for (Elevator elevator : elevators) {
      var score = Math.abs(callDto.getCurrentFloor() - elevator.getCurrentFloor());
      score += elevator.getStopFloors().size();
      var elevatorWithScore = new ElevatorWithScore();
      elevatorWithScore.setElevatorId(elevator.getId());
      elevatorWithScore.setElevatorScore(score);
      elevatorWithScore.setElevator(elevator);
      elevatorWithScores.add(elevatorWithScore);
    }

    elevatorWithScores.sort(Comparator.comparingInt(ElevatorWithScore::getElevatorScore));
    return elevatorOpt.orElse(elevatorWithScores.get(0).getElevator());
  }

  private Optional<Elevator> findElevatorNotInUse(List<Elevator> elevators) {
    return elevators.stream().filter(x -> !x.isInUse()).findFirst();
  }

  private Elevator getElevatorGoingInSameDirection(List<Elevator> elevators, Direction wantedDirection) {
    for (Elevator elevator : elevators) {
      if (elevator.getDirection() == wantedDirection || elevator.getDirection().equals(Direction.NONE)) {
        return elevator;
      }
    }
    return null;
  }

  @Override
  public List<Elevator> listElevators() {
    return new ArrayList<>(elevatorMap.values());
  }

  @Override
  public void updateElevator(ElevatorUpdateDto updateDto) {
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
}
