package com.tingco.codechallenge.elevator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
  private String message;
  private boolean success;
}
