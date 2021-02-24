package com.tingco.codechallenge.elevator.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.NamingConvention;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class MetricsRecorder {

  private final String SERVICE_PREFIX = "elevator.";

  private final MeterRegistry registry;

  public MetricsRecorder(MeterRegistry registry) {
    this.registry = registry;
    this.registry.config().namingConvention(NamingConvention.dot);
  }

  public void incrementCounter(String counterName) {
    registry.counter(SERVICE_PREFIX + counterName).increment();
  }
}
