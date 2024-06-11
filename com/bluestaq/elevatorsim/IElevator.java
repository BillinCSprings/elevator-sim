package com.bluestaq.elevatorsim;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public interface IElevator {
    CopyOnWriteArrayList<CallRequest> getWaitingPassengers();
    CopyOnWriteArrayList<CallRequest> getPassengersInElevator();
    int getCurrentFloor();
    int getDepartureFloor();
    int getDestinationFloor();
    ElevatorState getElevatorState();
    ConcurrentSkipListSet<Integer> getCallList();
    void callElevator(CallRequest callRequest);
}
