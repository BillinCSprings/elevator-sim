package com.bluestaq.elevatorsim;

public class ElevatorSimulatorTest {

    public static void main(String[] args) {

        Building bldg = Building.get_Instance();
        Elevator elevator =  bldg.getElevator();
        PassengerGenerator passengerGenerator = new PassengerGenerator(.1f,
                7,1);

        Thread elevatorThread = new Thread(elevator);
        Thread passengerGeneratorThread = new Thread(passengerGenerator);

        elevatorThread.start();
        passengerGeneratorThread.start();
    }
}
