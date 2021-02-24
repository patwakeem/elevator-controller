package com.tingco.codechallenge.elevator;

import com.tingco.codechallenge.elevator.service.ElevatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ElevatorITCase {

  @Autowired
  private ElevatorServiceImpl service;

  @Test
  void name() {
    System.out.println(service.listElevators());
  }
}