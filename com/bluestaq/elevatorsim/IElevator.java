package com.bluestaq.elevatorsim;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public interface IElevator {
     CopyOnWriteArrayList<Passenger> getWaitingPassengers();
    CopyOnWriteArrayList<Passenger> getInsidePassengers();
    int getCurrentFloor();
    int getDepartureFloor();
    int getDestinationFloor();
    ElevatorState getElevatorState();
    ConcurrentSkipListSet<Integer> getAllRequests();
    void callElevator(Passenger passenger);
}
