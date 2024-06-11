package com.bluestaq.elevatorsim;

/**
 * class CallRequest
 * Class representing an elevator passenger's call request
 */
public class CallRequest {
    public int departureFloor;
    public int destinationFloor;

    public CallRequest(int deptFloor, int destFloor) {
        this.departureFloor = deptFloor;
        this.destinationFloor = destFloor;
    }
}
