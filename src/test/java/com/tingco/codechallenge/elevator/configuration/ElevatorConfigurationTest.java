package com.tingco.codechallenge.elevator.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    elevatorConfiguration.setTopFloor(10);
    elevatorConfiguration.setBottomFloor(-10);

    final var map = elevatorConfiguration.constructElevatorMap();

    assertEquals(3, map.size());
  }

  @Test
  void exceptionIsThrownWhenNoFloors() {
    elevatorConfiguration.setNumberOfElevators(3);
    elevatorConfiguration.setTopFloor(10);
    elevatorConfiguration.setBottomFloor(10);

    assertThrows(ConfigurationException.class, () -> elevatorConfiguration.constructElevatorMap());
  }

  @Test
  void exceptionIsThrownWhenDefaultFloorOutOfRange() {
    elevatorConfiguration.setNumberOfElevators(3);
    elevatorConfiguration.setTopFloor(10);
    elevatorConfiguration.setBottomFloor(-10);
    elevatorConfiguration.setDefaultFloor(100);

    assertThrows(ConfigurationException.class, () -> elevatorConfiguration.constructElevatorMap());
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
}