package com.bluestaq.elevatorsim;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * class Elevator
 * Class representing an elevator
 * Implemented as a singleton to prevent it being called via any other way than
 * via a Building class object
 */

public class Elevator implements Runnable, IElevator {

    private static Elevator instance;
    /**
     * Stores passengers, that has not been serviced yet
     */
    private final CopyOnWriteArrayList<Passenger> waitingPassengers;

    /**
     * Stores passengers, that has not been serviced yet
     */
    private final CopyOnWriteArrayList<Passenger> insidePassengers;

    /**
     * Stores all requests (both from waiting and inside passengers)
     */
    private final ConcurrentSkipListSet<Integer> allRequests;
    private final ElevatorStatusWriter elevatorElevatorStatusWriter;
    public ElevatorState elevatorState;
    private int travelTime;
    private int currentFloor;
    private int departureFloor;
    private int destinationFloor;

    private Elevator() {
        this.setTravelTime(Defaults.TRAVEL_TIME);

        this.waitingPassengers = new CopyOnWriteArrayList<>();
        this.insidePassengers = new CopyOnWriteArrayList<>();
        this.allRequests = new ConcurrentSkipListSet<>();

        this.elevatorState = ElevatorState.IDLE;

        this.setCurrentFloor(1);
        this.setDepartureFloor(0);
        this.setDestinationFloor(0);

        this.elevatorElevatorStatusWriter = new ElevatorStatusWriter();
    }
    public static Elevator getInstance() {
        if (instance == null){
            instance = new Elevator();
        }
        return instance;
    }


    /**
     * Add passenger's request to all requests list.
     * If new request is higher than current destination for moving up or lower for moving down,
     * then it will be a new destination.
     * If elevator is stopped, then change current elevator state and update its source, destination.
     * @param passenger passenger who send request to the elevator
     */

    public void callElevator(Passenger passenger) {
        waitingPassengers.add(passenger);
        allRequests.add(passenger.departureFloor);

        boolean higherFloorRequest = (elevatorState == ElevatorState.ASCENDING && destinationFloor < passenger.departureFloor);
        boolean lowerFloorRequest = (elevatorState == ElevatorState.DESCENDING && destinationFloor > passenger.departureFloor);
        if(higherFloorRequest || lowerFloorRequest) {
            destinationFloor = passenger.departureFloor;
        }

        if(elevatorState == ElevatorState.STOPPING) {
            destinationFloor = passenger.departureFloor;
            elevatorState = destinationFloor - currentFloor > 0 ? ElevatorState.ASCENDING : ElevatorState.DESCENDING;
        }
    }

    /**
     * Take waiting passengers from current floor (if there are passengers for whom sourceFloor==currentFloor).
     * Update passengers lists and request list.
     */
    private void takePassengers() {
        for (Passenger passenger : waitingPassengers) {
            if (passenger.departureFloor == currentFloor) {
                waitingPassengers.remove(passenger);
                insidePassengers.add(passenger);
                allRequests.add(passenger.destinationFloor);
                allRequests.remove(currentFloor);
            }
        }
    }

    /**
     * Release passengers in current floor (if there are inside passengers for whom destinationFloor==currentFloor).
     * Update inside passengers list and request list.
     */
    private void releasePassengers() {
        for (Passenger passenger : insidePassengers) {
            if (passenger.destinationFloor == currentFloor) {
                insidePassengers.remove(passenger);
                allRequests.remove(passenger.destinationFloor);
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            if(elevatorState == ElevatorState.STOPPING) {
                elevatorElevatorStatusWriter.print(this);
            }

            if(allRequests.isEmpty()) {
                try {
                    Thread.sleep(travelTime);
                    continue;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


            int step = destinationFloor - departureFloor > 0 ? 1 : -1;
            while(currentFloor != destinationFloor) {

                elevatorElevatorStatusWriter.print(this);

                if(allRequests.contains(currentFloor)) {
                    releasePassengers();
                    takePassengers();
                }

                try {
                    Thread.sleep(travelTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                currentFloor += step;
            }
            takePassengers();
            releasePassengers();
            elevatorState = ElevatorState.STOPPING;
            departureFloor = currentFloor;
            if(!allRequests.isEmpty()) {
                destinationFloor = allRequests.getFirst();
                elevatorState = destinationFloor - currentFloor > 0 ? ElevatorState.ASCENDING : ElevatorState.DESCENDING;
            }
        }
    }


    //Accessors
    public CopyOnWriteArrayList<Passenger> getWaitingPassengers() {
        return waitingPassengers;
    }

     public CopyOnWriteArrayList<Passenger> getInsidePassengers() {
        return insidePassengers;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

     public int getDepartureFloor() {
        return departureFloor;
    }

    public void setDepartureFloor(int departureFloor) {
        this.departureFloor = departureFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public ElevatorState getElevatorState() {
        return elevatorState;
    }

    public void setElevatorState(ElevatorState elevatorState) {
        this.elevatorState = elevatorState;
    }

     public ConcurrentSkipListSet<Integer> getAllRequests() {
        return allRequests;
    }

    public int getTravelTime() {
        return travelTime;
    }

     public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }


}