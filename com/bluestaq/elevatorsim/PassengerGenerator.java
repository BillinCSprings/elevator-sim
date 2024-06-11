package com.bluestaq.elevatorsim;

import java.util.concurrent.ThreadLocalRandom;

public class PassengerGenerator implements Runnable {
    private final Elevator elevator;
    private final float probability;
    private final int floors;
    private final int travelTimeMultiplier;


    public PassengerGenerator(float probability, int floors, int timeUnit) {
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
                    Passenger passenger = new Passenger(departureFloor, destinationFloor);
                    this.elevator.callElevator(passenger);
                }
                Thread.sleep(this.travelTimeMultiplier * 1000L);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        }
    }
}

