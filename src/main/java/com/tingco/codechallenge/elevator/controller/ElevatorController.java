package com.tingco.codechallenge.elevator.controller;

import com.tingco.codechallenge.elevator.model.dto.ElevatorCallDto;
import com.tingco.codechallenge.elevator.model.dto.ResponseDto;
import com.tingco.codechallenge.elevator.service.ElevatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public final class ElevatorController {

  private final ElevatorService elevatorService;

  @PostMapping("/elevator/call")
  public ResponseEntity<ResponseDto> callElevator(@RequestBody ElevatorCallDto requestDto) {
    elevatorService.requestElevator(requestDto);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(new ResponseDto("Elevator called to floor: " + requestDto.getToFloor(), true));
  }

}
