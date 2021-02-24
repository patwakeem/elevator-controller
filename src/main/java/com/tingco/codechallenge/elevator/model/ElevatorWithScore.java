package com.tingco.codechallenge.elevator.model;

import lombok.Data;

@Data
public class ElevatorWithScore {
  private int elevatorId;
  private int elevatorScore;
  private Elevator elevator;
}
