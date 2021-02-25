package com.tingco.codechallenge.elevator.configuration;

import com.tingco.codechallenge.elevator.exception.ConfigurationException;
import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ElevatorConfiguration {

  @Value("${com.tingco.elevator.number-of-elevators:1}")
  private int numberOfElevators;
  @Value("${com.tingco.elevator.top-floor}")
  private int topFloor;
  @Value("${com.tingco.elevator.bottom-floor}")
  private int bottomFloor;
  @Value("${com.tingco.elevator.default-floor:0}")
  private int defaultFloor;

  @Bean
  public Map<Integer, List<Elevator>> constructElevatorOnFloorMap(Map<Integer, Elevator> elevatorMap) {
    final var elevators = new ArrayList<>(elevatorMap.values());
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
    validateConfig();
    validateElevatorNumberOrThrow(numberOfElevators);
    var elevators = new ConcurrentHashMap<Integer, Elevator>();
    log.info("Initializing {} elevators...", numberOfElevators);

    for (int i = 0; i < numberOfElevators; i++) {
      var elevator = new Elevator(
          i,
          defaultFloor
      );
      elevators.put(i, elevator);
    }

    return elevators;
  }

  private void validateElevatorNumberOrThrow(int numberOfElevators) {
    if (numberOfElevators <= 0) {
      throw new ConfigurationException("Number of elevators was 0 or less. You must specify a number greater than 1");
    }
  }

  private void validateConfig() {
    if (topFloor == bottomFloor) {
      throw new ConfigurationException(
          String.format(
              "Your building seems to have no floors. You don't need an elevator. Top floor %s, Bottom floor %s",
              topFloor,
              bottomFloor
          )
      );
    } else if (bottomFloor > topFloor) {
      throw new ConfigurationException(
          String.format(
              "Your configured bottom floor is above your top floor. Please check your configuration. Top floor %s, Bottom floor %s",
              topFloor,
              bottomFloor
          )
      );
    }

    if (!floorInRange(defaultFloor)) {
      throw new ConfigurationException(
          String.format(
              "Your default floor is %s which is not in the range of %s - %s",
              defaultFloor,
              bottomFloor,
              topFloor
          )
      );
    }
  }

  private boolean floorInRange(int floor) {
    return floor >= bottomFloor && floor <= topFloor;
  }

  // Used for testing only.
  public void setNumberOfElevators(int numberOfElevators) {
    this.numberOfElevators = numberOfElevators;
  }

  public void setTopFloor(int topFloor) {
    this.topFloor = topFloor;
  }

  public void setBottomFloor(int bottomFloor) {
    this.bottomFloor = bottomFloor;
  }

  public void setDefaultFloor(int defaultFloor) {
    this.defaultFloor = defaultFloor;
  }
}
