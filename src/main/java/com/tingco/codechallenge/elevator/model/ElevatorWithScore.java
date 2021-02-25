package com.tingco.codechallenge.elevator.model;

import lombok.Data;

@Data
public class ElevatorWithScore {

  private int elevatorId;
  private double elevatorScore;
  private Elevator elevator;
}
