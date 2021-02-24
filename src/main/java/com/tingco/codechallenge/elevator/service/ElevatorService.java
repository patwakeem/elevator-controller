package com.tingco.codechallenge.elevator.service;

import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
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
  List<Elevator> getElevators();

  /**
   * Telling the controller that the given elevator is free for new operations.
   *
   * @param elevator the elevator that shall be released.
   */
  void releaseElevator(Elevator elevator);

}
