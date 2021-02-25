# The Greatest Elevator System Ever Devised

Your elevator call buttons should call an elevator using the request endpoint. If an elevator is on your floor and going in the same direction it will be assigned to you. 
- If no elevators are on your floor and going the same direction as you, you will be assigned an elevator which should be close and doesn't have many other stops.
- An example of the request endpoint can be found in the postman collection. See the "call elevator" request.

Elevator software should update the central system when it is in use and reaches a new floor. See update endpoint.
- Postman collection included in the postman folder. Use this to implement your client. See the "update elevator" request.

The system can be polled for the current elevator state by using the list elevator endpoint.
- For an example of this endpoint see the postman collection "list elevators" call.

The system will recall elevators to the default floor periodically. This recall routine can be configured (see below).


###Configuration Keys - application.properties
The elevator system should be configured using the following keys in the application.properties file.

com.tingco.elevator.number-of-elevators, number of available elevators.

com.tingco.elevator.top-floor, top floor of your building.

com.tingco.elevator.bottom-floor, bottom floor of your building, may be negative to represent basement levels.

com.tingco.elevator.default-floor, the main floor of the building (usually the lobby). This is the floor the elevator recall routine will send elevators to.

com.tingco.elevator.elevator-reset-interval-ms, The elevator system will recall elevators which are not in use to the default floor. Set this value to the interval (in milliseconds) that you want this recall to happen at.


###Internal Properties - application.properties

logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter, set this to DEBUG if you want to see detailed request logs.

logging.level.com.tingco.codechallenge.elevator, application log level.

management.server.port, port for actuator server.

management.endpoints.web.exposure.include, opens access for endpoints with Spring Actuator.

management.endpoint.health.enabled, enables health endpoint.

management.endpoint.health.show-details, enables health endpoint details (disk space).

spring.jackson.serialization.indent_output, enables pretty json responses.

management.metrics.export.statsd.enabled, enables statsd metrics exporting.

##Running a Coverage Check
Running a coverage check is as simple as running the following maven goal:
Line coverage must be >80% or the build will fail.

```
mvn clean verify
```

##Building a Container
Building a container is as simple as running the following maven goal:
spring-boot:build-image

##Starting the Application
You may also run the application by building it directly. The application requires java 11+.

The application is configured using an application.properties file. You can create one and point to it on the command line.
```
mvn clean verify package
java -jar target/elevator-2020.02.27-0.jar --spring.config.location=/path/to/application.properties
```

##Editing the Application
Using intellij is recommended to work with this application. Additionally you must have the lombok plugin installed (built into newer versions of intellij).

##Running a simulation
The application can be configured to run a simulation. For instructions on how to do this see SIMULATION.md.


## Monitoring the Application - Metrics
The application can be configured to ship metrics to a statsd instance. Simply set the following property to true to enable this: "management.metrics.export.statsd.enabled".
You can configure metrics exporting with the following keys: management.metrics.export.statsd.* They are documented here: https://github.com/micrometer-metrics/micrometer-docs/blob/master/src/docs/spring/spring-configuring.adoc#statsd


## Monitoring the Application - Healthcheck
The application includes a healthcheck endpoint for use by operators or alternatively, a load balancer. With the default properties it is available at: <SERVER_ADDRESS>:9000/actuator/health

This endpoint returns a simple JSON healthcheck indicating that the server is up. A request is in the postman collection if you need help performing this request.
