package com.tingco.codechallenge.elevator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
    "com.tingco.elevator.simulation.mode-enable=false"
})
@ActiveProfiles({"test"})
class ElevatorApplicationTest {

  @Test
  void contextLoads() {
  }
}