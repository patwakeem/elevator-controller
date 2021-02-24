package com.tingco.codechallenge.elevator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Resource.
 *
 * @author Sven Wesley
 */
@RestController
@RequestMapping("/rest/v1")
public final class ElevatorController {

  /**
   * Ping service to test if we are alive.
   *
   * @return String pong
   */
  @RequestMapping(value = "/ping", method = RequestMethod.GET)
  public String ping() {
    return "pong";
  }
}
