package com.tingco.codechallenge.elevator.simulation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "com.tingco.elevator.simulation.mode-enable", havingValue = "true")
public class Simulator implements ApplicationRunner {

  private final ConfigurableApplicationContext context;

  @Override

  public void run(ApplicationArguments args) throws Exception {
    log.info("### SIMULATION MODE ENABLED ###");

    context.close();
  }
}
