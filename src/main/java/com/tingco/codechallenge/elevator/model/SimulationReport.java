package com.tingco.codechallenge.elevator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SimulationReport {
  private int errors;
  private int loops;
  private int calls;
  private int elevatorMovePerStep;
  private String reportDate;
  private List<Elevator> finalElevatorState;
}
