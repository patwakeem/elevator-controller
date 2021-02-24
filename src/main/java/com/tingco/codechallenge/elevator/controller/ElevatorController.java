package com.tingco.codechallenge.elevator.controller;

import com.tingco.codechallenge.elevator.model.Elevator;
import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import com.tingco.codechallenge.elevator.model.dto.ElevatorUpdateDto;
import com.tingco.codechallenge.elevator.model.dto.ResponseDto;
import com.tingco.codechallenge.elevator.service.ElevatorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping(value = "/elevator/call", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDto> callElevator(@RequestBody ElevatorCallDto requestDto) {
    elevatorService.requestElevator(requestDto);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(new ResponseDto("Elevator called to floor: " + requestDto.getToFloor(), true));
  }

  @GetMapping("/elevator")
  public ResponseEntity<List<Elevator>> listElevators() {
    return ResponseEntity.ok(elevatorService.listElevators());
  }

  @PutMapping(value = "/elevator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDto> updateElevator(@RequestBody ElevatorUpdateDto updateDto) {
    elevatorService.updateElevator(updateDto);
    return ResponseEntity.ok(new ResponseDto("Elevator updated.", true));
  }
}
