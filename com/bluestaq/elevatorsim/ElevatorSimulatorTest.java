package com.bluestaq.elevatorsim;

public class ElevatorSimulatorTest {

    public static void main(String[] args) {

        System.out.println("Test Starting");

        Building bldg = Building.get_Instance();
        Elevator elevator =  bldg.getElevator();
        CallRequestGenerator callRequestGenerator = new CallRequestGenerator(.1f,
                10,1);

        Thread elevatorThread = new Thread(elevator);
        Thread passengerGeneratorThread = new Thread(callRequestGenerator);

        elevatorThread.start();
        passengerGeneratorThread.start();
    }
}
