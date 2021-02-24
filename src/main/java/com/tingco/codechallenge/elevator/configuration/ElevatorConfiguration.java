package com.tingco.codechallenge.elevator.configuration;

import com.tingco.codechallenge.elevator.exception.ConfigurationException;
import com.tingco.codechallenge.elevator.model.Direction;
import com.tingco.codechallenge.elevator.model.Elevator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElevatorConfiguration {

  @Value("${com.tingco.elevator.number-of-elevators:1}")
  private int numberOfElevators;

  @Bean
  public List<Elevator> constructElevatorList() {
    validateElevatorNumberOrThrow(numberOfElevators);
    var elevators = new ArrayList<Elevator>();

    for (int i = 0; i < numberOfElevators; i++) {
      var elevator = new Elevator(
          i + 1,
          0,
          0,
          Direction.NONE
      );
      elevators.add(elevator);
    }

    return elevators;
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
