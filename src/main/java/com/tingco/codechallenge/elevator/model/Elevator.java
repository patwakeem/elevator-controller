package com.tingco.codechallenge.elevator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Elevator {
  private int number;
  private boolean inUse;
  private int currentFloor;
}
