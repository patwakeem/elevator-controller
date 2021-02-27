package com.tingco.codechallenge.elevator.simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingco.codechallenge.elevator.controller.ElevatorController;
import com.tingco.codechallenge.elevator.exception.InvalidElevatorFloorException;
import com.tingco.codechallenge.elevator.model.Direction;
import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.SimulationReport;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import com.tingco.codechallenge.elevator.model.dto.ElevatorUpdateDto;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
  private final ElevatorController controller;

  @Value("${com.tingco.elevator.top-floor}")
  private int topFloor;

  @Value("${com.tingco.elevator.bottom-floor}")
  private int bottomFloor;

  @Value("${com.tingco.elevator.simulation.enable-invalid-calls:false}")
  private boolean generateInvalidRequests;

  @Value("${com.tingco.elevator.simulation.simulation-loops:100}")
  private int simulationLoops;

  @Value("${com.tingco.elevator.simulation.simulation-users-per-loop:1}")
  private int usersPerLoop;

  @Value("${com.tingco.elevator.simulation.elevator-move-per-step:3}")
  private int elevatorMovePerStep;

  @Value("${com.tingco.elevator.simulation.loop-sleep-time-ms:10000}")
  private long loopSleepTimeMs;

  @Value("${com.tingco.elevator.simulation.exit-when-done:true}")
  private boolean exitWhenDone;

  @Value("${com.tingco.elevator.simulation.report-path:}")
  private String reportPath;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("### SIMULATION MODE ENABLED ###");
    log.info("### SIMULATION LOOPS - {}", simulationLoops);
    log.info("### SIMULATION USERS PER LOOP - {}", usersPerLoop);
    log.info("### SIMULATION ELEVATOR MOVE PER STEP - {}", elevatorMovePerStep);
    log.info("### SIMULATION INVALID CALLS ENABLED - {}", generateInvalidRequests);
    log.info("### SIMULATION LOOP SLEEP TIME MS - {}", loopSleepTimeMs);
    int errors = 0;
    int calls = 0;

    for (int i = 0; i < simulationLoops; i++) {
      log.info("Starting loop: {}", i + 1);
      log.info("Starting user movement phase.");
      for (ElevatorCallDto generateCallDto : generateCallDtos()) {
        calls++;
        try {
          controller.callElevator(generateCallDto);
        } catch (InvalidElevatorFloorException ignored) {
          errors++;
        }
      }

      log.info("Starting elevator movement phase.");
      for (Elevator elevator : controller.listElevators().getBody()) {
        for (int j = 0; j < elevatorMovePerStep; j++) {
          if (elevator.getDirection() == Direction.DOWN) {
            var floor = elevator.getCurrentFloor() - 1;
            log.debug("Elevator {} moved {} to floor {}.", elevator.getId(), elevator.getDirection(), floor);
            controller.updateElevator(
                elevator.getId(),
                new ElevatorUpdateDto(floor)
            );
          } else if (elevator.getDirection() == Direction.UP) {
            var floor = elevator.getCurrentFloor() + 1;
            log.debug("Elevator {} moved {} to floor {}.", elevator.getId(), elevator.getDirection(), floor);
            controller.updateElevator(
                elevator.getId(),
                new ElevatorUpdateDto(floor)
            );
          }
        }
      }

      Thread.sleep(loopSleepTimeMs);
    }

    log.info("### SIMULATION COMPLETE ###");

    if (!reportPath.isBlank()) {
      final var reportDate = Instant.now().toString();
      final var mapper = new ObjectMapper();
      final var simulationReport = new SimulationReport(
          errors,
          simulationLoops,
          calls,
          elevatorMovePerStep,
          reportDate,
          controller.listElevators().getBody()
      );
      final var reportFile = Path.of(reportPath, "elevator-report-" + reportDate + ".json");

      try {
        log.info("Writing report to {}", reportFile);
        var prettyReport = mapper.writerWithDefaultPrettyPrinter()
            .writeValueAsBytes(simulationReport);

        Files.write(
            reportFile,
            prettyReport
        );
      } catch (Exception e) {
        log.error("Could not write report file.", e);
      }
    }

    if (exitWhenDone) {
      log.info("### EXITING ###");
      context.close();
    }
  }

  private List<ElevatorCallDto> generateCallDtos() {
    var dtos = new ArrayList<ElevatorCallDto>();

    for (int i = 0; i < usersPerLoop; i++) {
      dtos.add(new ElevatorCallDto(
          getRandomFloor(),
          getRandomFloor()
      ));
    }

    return dtos;
  }

  private int getRandomFloor() {
    if (generateInvalidRequests) {
      return ThreadLocalRandom.current().nextInt(bottomFloor - 2, topFloor + 2);
    } else {
      return ThreadLocalRandom.current().nextInt(bottomFloor, topFloor + 1);
    }
  }
}
