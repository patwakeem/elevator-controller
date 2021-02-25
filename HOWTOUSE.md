Using the greatest elevator system designed by man:

Call an elevator using the request endpoint. If an elevator is on your floor and going in the same direction it will be assigned to you. 
- If not you will get an elevator which is not in use.
- If no elevators are left you will get an elevator which should be close.

Elevator software should update the central system when it is in use and reaches a new floor. See update endpoint.
- Postman collection included in the postman folder. Use this to implement your client.


###Configuration Keys - application.properties
com.tingco.elevator.number-of-elevators, number of available elevators.

com.tingco.elevator.top-floor, top floor of your building.

com.tingco.elevator.bottom-floor, bottom floor of your building, may be negative to represent basement levels.

com.tingco.elevator.default-floor, the main floor of the building (usually the lobby). This is the floor the elevator recall routine will send elevators to.

com.tingco.elevator.elevator-reset-interval-ms, The elevator system will recall elevators which are not in use to the default floor. Set this value to the interval (in milliseconds) that you want this recall to happen at.

com.tingco.elevator.simulation.mode-enable, Boolean, setting to true will enable simulation mode.


###Internal Properties - application.properties

logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter, set this to DEBUG if you want to see detailed request logs.

logging.level.com.tingco.codechallenge.elevator, application log level.

management.server.port, port for actuator server.

management.endpoints.web.exposure.include, opens access for endpoints with Spring Actuator.

management.endpoint.health.enabled, enables health endpoint.

management.endpoint.health.show-details, enables health endpoint details (disk space).

spring.jackson.serialization.indent_output, enables pretty json responses.

management.metrics.export.statsd.enabled, enables statsd metrics exporting.

###Running a Coverage Check
Running a coverage check is as simple as running the following maven goal:
Line coverage must be >80% or the build will fail.

verify

###Building a Container
Building a container is as simple as running the following maven goal:
spring-boot:build-image

