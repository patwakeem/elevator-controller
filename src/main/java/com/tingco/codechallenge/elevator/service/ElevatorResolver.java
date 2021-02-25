package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.model.Direction;
import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.ElevatorWithScore;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElevatorResolver {

  public Elevator findElevatorGoingInSameDirection(List<Elevator> elevators, Direction wantedDirection) {
    for (Elevator elevator : elevators) {
      if (elevator.getDirection() == wantedDirection || elevator.getDirection().equals(Direction.NONE)) {
        log.debug("Found elevator {} on same floor not in use or en route.", elevator.getId());
        return elevator;
      }
    }
    return null;
  }

  public Elevator findBestElevator(List<Elevator> elevators, ElevatorCallDto callDto) {
//    Find an elevator that is close and doesnt have too many stops.
//    Elevators with other stops get larger scores and are thus less likely to be used.
    List<ElevatorWithScore> elevatorWithScores = new ArrayList<>();

    for (Elevator elevator : elevators) {
      final double callCurrent = Math.abs(callDto.getCurrentFloor());
      final double elevatorCurrent = Math.abs(elevator.getCurrentFloor());
      double score = Math.abs(callCurrent - elevatorCurrent);

      if (!elevator.getStopFloors().isEmpty()) {
        score += elevator.getStopFloors().size();
        score *= 1.5;
      }

      var elevatorWithScore = new ElevatorWithScore();
      elevatorWithScore.setElevatorId(elevator.getId());
      elevatorWithScore.setElevatorScore(score);
      elevatorWithScore.setElevator(elevator);
      elevatorWithScores.add(elevatorWithScore);
    }

    elevatorWithScores.sort(Comparator.comparingDouble(ElevatorWithScore::getElevatorScore));
    return elevatorWithScores.get(0).getElevator();
  }
}
