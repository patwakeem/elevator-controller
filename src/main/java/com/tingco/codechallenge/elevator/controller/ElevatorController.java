package com.tingco.codechallenge.elevator.controller;

import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import com.tingco.codechallenge.elevator.model.dto.ElevatorResponseDto;
import com.tingco.codechallenge.elevator.model.dto.ElevatorUpdateDto;
import com.tingco.codechallenge.elevator.service.ElevatorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public final class ElevatorController {

  private final ElevatorService elevatorService;

  @PostMapping(value = "/call", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ElevatorResponseDto> callElevator(@RequestBody ElevatorCallDto requestDto) {
    final var elevator = elevatorService.requestElevator(requestDto);
    final var responseDto = new ElevatorResponseDto(
        "Elevator called to floor: " + requestDto.getCurrentFloor(), true, elevator.getId());
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDto);
  }

  @GetMapping("/elevator")
  public ResponseEntity<List<Elevator>> listElevators() {
    return ResponseEntity.ok(elevatorService.listElevators());
  }

  @PutMapping(value = "/elevator/{elevatorId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ElevatorResponseDto> updateElevator(@PathVariable int elevatorId, @RequestBody ElevatorUpdateDto updateDto) {
    elevatorService.updateElevatorLocation(updateDto, elevatorId);
    return ResponseEntity.ok(new ElevatorResponseDto("Elevator updated.", true, elevatorId));
  }
}
