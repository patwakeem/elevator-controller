package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import com.tingco.codechallenge.elevator.model.dto.ElevatorUpdateDto;
import java.util.List;

public interface ElevatorService {

  /**
   * Request an elevator to the specified floor. This queues the request.
   *
   * @param dto Call DTO that contains your floor.
   * @return The Elevator that is going to the floor, if there is one to move.
   */
  Elevator requestElevator(ElevatorCallDto dto);

  /**
   * A snapshot list of all elevators in the system.
   *
   * @return A List with all {@link Elevator} objects.
   */
  List<Elevator> listElevators();

  /**
   * Called by elevators as they travel from floor to floor.
   * Keeps track of where elevators are and the stops they have.
   */
  void updateElevator(ElevatorUpdateDto updateDto);
}
