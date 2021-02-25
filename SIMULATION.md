# Enabling Simulation Mode

It is possible to run a simulation that emulates user interaction and elevator movement. To do so set the following property to true:

com.tingco.elevator.simulation.mode-enable

## How a Simulation Works
The simulation will run in a loop until it reaches the "com.tingco.elevator.simulation.simulation-loops" value. 
Each loop will start with creating elevator requests, the amount of requests created is equal to the property value of "com.tingco.elevator.simulation.simulation-users-per-loop". 

If "com.tingco.elevator.simulation.enable-invalid-calls" is set to true the generated calls can be out of the floor range (above/below the min/max floors). After the calls have been generated the elevators get to move, each elevator moves an amount of times equal to "com.tingco.elevator.simulation.elevator-move-per-step". 
After a loop completes the simulation will sleep for milliseconds equal to "com.tingco.elevator.simulation.loop-sleep-time-ms". When all loops have completed the simulation will write a report to the path specified in "com.tingco.elevator.simulation.report-path". If no path is present the report will not be written.
Finally if the following property is set to true "com.tingco.elevator.simulation.exit-when-done" the application will exit.


## Simulation Control Properties
When this is set to true the following config keys become available to you:

com.tingco.elevator.simulation.enable-invalid-calls (boolean): Enabling this will cause invalid call requests to be generated. Invalid requests will be to floors that are above or below the max and min floor values. 

com.tingco.elevator.simulation.simulation-loops (int): Amount of loops the simulation will go for.

com.tingco.elevator.simulation.simulation-users-per-loop (int): Amount of elevator requests which will be generated per loop.

com.tingco.elevator.simulation.loop-sleep-time-ms (long): After every loop the simulation will sleep for this amount of ms. You can use this time to call the list elevators endpoint or run your own request/update calls.

com.tingco.elevator.simulation.exit-when-done (boolean): When set to true the application will exit when the simulation completes.

com.tingco.elevator.simulation.report-path (string): Directory to where a simulation report will be written. If empty or ommitted no report will be written.

com.tingco.elevator.simulation.elevator-move-per-step (int): Elevator movement per loop. After users have called elevators each elevator will move this amount of times.