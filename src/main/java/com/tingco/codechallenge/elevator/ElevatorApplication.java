package com.tingco.codechallenge.elevator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ElevatorApplication {
  public static void main(final String[] args) {
    SpringApplication.run(ElevatorApplication.class, args);
  }
}
