package com.tingco.codechallenge.elevator.configuration;

import com.tingco.codechallenge.elevator.exception.ConfigurationException;
import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElevatorConfiguration {

  @Value("${com.tingco.elevator.number-of-elevators:1}")
  private int numberOfElevators;
  @Value("${com.tingco.elevator.top-floor}")
  private int topFloor;
  @Value("${com.tingco.elevator.bottom-floor}")
  private int bottomFloor;

  @Bean
  public Map<Integer, List<Elevator>> constructElevatorOnFloorMap(List<Elevator> elevators) {
    var map = new ConcurrentHashMap<Integer, List<Elevator>>();

    for (int i = bottomFloor; i <= topFloor; i++) {
      map.put(i, new ArrayList<>());
    }

    map.get(0).addAll(elevators);
    return map;
  }

  @Bean
  public ConcurrentHashMap<Integer, List<ElevatorCallDto>> constructRequestOnFloorMap() {
    return new ConcurrentHashMap<>();
  }

  @Bean
  public Map<Integer, Elevator> constructElevatorMap() {
    validateElevatorNumberOrThrow(numberOfElevators);
    var elevators = new ConcurrentHashMap<Integer, Elevator>();

    for (int i = 0; i < numberOfElevators; i++) {
      var elevator = new Elevator(
          i,
          0
      );
      elevators.put(i, elevator);
    }

    return elevators;
  }

  @Bean
  public List<Elevator> constructElevatorList(Map<Integer, Elevator> elevatorMap) {
    return new ArrayList<>(elevatorMap.values());
  }

  private void validateElevatorNumberOrThrow(int numberOfElevators) {
    if (numberOfElevators <= 0) {
      throw new ConfigurationException("Number of elevators was 0 or less. You must specify a number greater than 1");
    }
  }

// Used for testing only.
  protected void setNumberOfElevators(int numberOfElevators) {
    this.numberOfElevators = numberOfElevators;
  }
}
