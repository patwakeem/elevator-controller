package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.ElevatorWithScore;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElevatorResolver {

  public Elevator findBestElevator(List<Elevator> elevators, ElevatorCallDto callDto) {
//    find any elevators not in use. if found return immediately
    final var elevatorOpt = findElevatorNotInUse(elevators);

    if (elevatorOpt.isPresent()) {
      return elevatorOpt.get();
    }

//    find an elevator that is reasonably close and doesn't have too many other stops
    List<ElevatorWithScore> elevatorWithScores = new ArrayList<>();

    for (Elevator elevator : elevators) {
      final var callCurrent = Math.abs(callDto.getCurrentFloor());
      final var elevatorCurrent = Math.abs(elevator.getCurrentFloor());
      var score = Math.abs(callCurrent - elevatorCurrent);
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
}
