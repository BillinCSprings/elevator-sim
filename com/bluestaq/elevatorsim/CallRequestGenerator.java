package com.bluestaq.elevatorsim;

import java.util.concurrent.ThreadLocalRandom;

/**
 * class CallRequestGenerator
 * Class representing an elevator passenger's call request
 */
public class CallRequestGenerator implements Runnable {
    private final Elevator elevator;


    /**
     * Stores the mathematical probability that a randomly generated number will result in a call request being generated
     */
    private final float probability;
    /**
     * Stores the number of floors that a randomly generated number will result in a call request being generated
     */
    private final int floors;
    /**
     * Stores the speed to travel between floors to be multiplied to
     */
    private final int travelTimeMultiplier;

    public CallRequestGenerator(float probability, int floors, int timeUnit) {
        this.elevator = Building.get_Instance().getElevator();
        this.probability = probability;
        this.floors = floors;
        this.travelTimeMultiplier = timeUnit;
    }

    @Override
    public void run() {
        try {
            while(true) {
                if (ThreadLocalRandom.current().nextInt(101) <= this.probability * 100) {
                    int departureFloor = ThreadLocalRandom.current().nextInt(this.floors + 1);
                    int destinationFloor = ThreadLocalRandom.current().nextInt(this.floors + 1);
                    if (departureFloor == destinationFloor) {
                        continue;
                    }
                    CallRequest callRequest = new CallRequest(departureFloor, destinationFloor);
                    this.elevator.callElevator(callRequest);
                }
                Thread.sleep(this.travelTimeMultiplier * elevator.getTravelTime());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        }
    }
}

