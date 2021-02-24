package com.tingco.codechallenge.elevator.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tingco.codechallenge.elevator.exception.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ElevatorConfigurationTest {

  private ElevatorConfiguration elevatorConfiguration;

  @BeforeEach
  void setUp() {
    elevatorConfiguration = new ElevatorConfiguration();
  }

  @Test
  void correctNumberOfElevatorsAreInitialized() {
    elevatorConfiguration.setNumberOfElevators(3);

    final var map = elevatorConfiguration.constructElevatorMap();

    assertEquals(3, map.size());
  }

  @Test
  void exceptionIsThrownWithNegativeElevators() {

    elevatorConfiguration.setNumberOfElevators(-1);

    assertThrows(ConfigurationException.class, () -> elevatorConfiguration.constructElevatorMap());
  }

  @Test
  void exceptionIsThrownWithZeroElevators() {

    elevatorConfiguration.setNumberOfElevators(0);

    assertThrows(ConfigurationException.class, () -> elevatorConfiguration.constructElevatorMap());
  }

  @Test
  void elevatorListIsSetFromMap() {
    elevatorConfiguration.setNumberOfElevators(1);
    final var map = elevatorConfiguration.constructElevatorMap();

    final var elevators = elevatorConfiguration.constructElevatorList(map);

    assertTrue(map.values().containsAll(elevators));
  }
}