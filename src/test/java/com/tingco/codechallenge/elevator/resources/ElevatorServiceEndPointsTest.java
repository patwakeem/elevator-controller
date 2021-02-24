package com.tingco.codechallenge.elevator.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tingco.codechallenge.elevator.controller.ElevatorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ElevatorServiceEndPointsTest {

  @Autowired
  private ElevatorController endPoints;

  @Test
  public void ping() {
    assertEquals("pong", endPoints.ping());
  }

}
