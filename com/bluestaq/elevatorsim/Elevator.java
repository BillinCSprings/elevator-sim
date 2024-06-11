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
    private final CopyOnWriteArrayList<CallRequest> waitingCallRequests;

    /**
     * Stores passengers, that has not been serviced yet
     */
    private final CopyOnWriteArrayList<CallRequest> insideCallRequests;

    /**
     * Stores all requests (both from waiting and on-board passengers)
     */
    private final ConcurrentSkipListSet<Integer> callList;
    private final ElevatorStatusWriter elevatorElevatorStatusWriter;
    /**
     * Stores the elevator's current state e.g. IDLE, STOPPED, etc.
     */
    public ElevatorState elevatorState;

    /**
     * Stores the amount of time the elevator needs tot travel between floors
     */
    private int travelTime;

    /**
     * Stores the elevator's current floor number
     */
    private int currentFloor;

    /**
     * Stores the floor number from which the elevator departed
     */
    private int departureFloor;

    /**
     * Stores the floor number from where the elevator is headed
     */
    private int destinationFloor;

    private Elevator() {
        this.setTravelTime(Defaults.TRAVEL_TIME);

        this.waitingCallRequests = new CopyOnWriteArrayList<>();
        this.insideCallRequests = new CopyOnWriteArrayList<>();
        this.callList = new ConcurrentSkipListSet<>();

        this.elevatorState = ElevatorState.IDLE;

        this.setCurrentFloor(1);
        this.setDepartureFloor(0);
        this.setDestinationFloor(0);

        this.elevatorElevatorStatusWriter = new ElevatorStatusWriter();
    }
    /**
     * method: get_Instance
     * standard instance retrieval method signature for singleton
     * @return Elevator instance
     */
    public static Elevator getInstance() {
        if (instance == null){
            instance = new Elevator();
        }
        return instance;
    }


    /**
     * method: callElevator
     * Add CallRequest's request to the call requests list.
     * If new request is higher than current destination and it jives with the current direction is
     * i.e. ASCENDING or it is lower nd the staate is DESCNDING, then it will become a new destination.
     * If elevator is stopped, then the current elevator state,  it will be updated as well as
     * its departure and destination floors
     * @param callRequest CallRequest who send request to the elevator
     */

    public void callElevator(CallRequest callRequest) {
        waitingCallRequests.add(callRequest);
        callList.add(callRequest.departureFloor);

        boolean higherFloorRequest = (elevatorState == ElevatorState.ASCENDING && destinationFloor < callRequest.departureFloor);
        boolean lowerFloorRequest = (elevatorState == ElevatorState.DESCENDING && destinationFloor > callRequest.departureFloor);
        if(higherFloorRequest || lowerFloorRequest) {
            destinationFloor = callRequest.departureFloor;
        }

        if(elevatorState == ElevatorState.STOPPED) {
            destinationFloor = callRequest.departureFloor;
            elevatorState = destinationFloor - currentFloor > 0 ? ElevatorState.ASCENDING : ElevatorState.DESCENDING;
        }
    }

    /**
     * method takePassengers
     * Take waiting passengers from current floor (if there are passengers for whom sourceFloor==currentFloor).
     * Update passengers lists and request list.
     */
    private void takePassengers() {
        for (CallRequest callRequest : waitingCallRequests) {
            if (callRequest.departureFloor == currentFloor) {
                waitingCallRequests.remove(callRequest);
                insideCallRequests.add(callRequest);
                callList.add(callRequest.destinationFloor);
                callList.remove(currentFloor);
            }
        }
    }

    /**
     * method releasePassengers
     * Release passengers in current floor (if there are inside passengers for whom destinationFloor==currentFloor).
     * Update inside passengers list and request list.
     */
    private void releasePassengers() {
        for (CallRequest callRequest : insideCallRequests) {
            if (callRequest.destinationFloor == currentFloor) {
                insideCallRequests.remove(callRequest);
                callList.remove(callRequest.destinationFloor);
            }
        }
    }


    /**
     * method run
     * The execution loop for the Elevator thread.
     * It processes the call list, then updates the elevator's state,
     * as well as the departure and destination floors
     */

    @Override
    public void run() {
        while(true) {
            if(elevatorState == ElevatorState.STOPPED || elevatorState == ElevatorState.IDLE ) {
                elevatorElevatorStatusWriter.WriteElevatorStatus(this);
            }

            if(callList.isEmpty()) {
                 try {
                    Thread.sleep(travelTime);
                    continue;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


            int step = destinationFloor - departureFloor > 0 ? 1 : -1;
            while(currentFloor != destinationFloor) {

                elevatorElevatorStatusWriter.WriteElevatorStatus(this);

                if(callList.contains(currentFloor)) {
                    releasePassengers();
                    takePassengers();
                }

                try {
                    Thread.sleep(travelTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                currentFloor += step;
                System.out.println("Current Floor: "+ currentFloor);
            }
            takePassengers();
            releasePassengers();
            elevatorState = ElevatorState.STOPPED;
            departureFloor = currentFloor;
            if(!callList.isEmpty()) {
                destinationFloor = callList.getFirst();
                elevatorState = destinationFloor - currentFloor > 0 ? ElevatorState.ASCENDING : ElevatorState.DESCENDING;
            }
        }
    }


    //Accessors
    public CopyOnWriteArrayList<CallRequest> getWaitingPassengers() {
        return waitingCallRequests;
    }

     public CopyOnWriteArrayList<CallRequest> getPassengersInElevator() {
        return insideCallRequests;
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

     public ConcurrentSkipListSet<Integer> getCallList() {
        return callList;
    }

    public int getTravelTime() {
        return travelTime;
    }

     public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }


}