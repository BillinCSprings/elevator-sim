package com.bluestaq.elevatorsim;

public class ElevatorSimulatorTest {

    public static void main(String[] args) {

        Building bldg = Building.get_Instance();
        Elevator elevator =  bldg.getElevator();
        CallRequestGenerator callRequestGenerator = new CallRequestGenerator(.1f,
                bldg.getMAX_FLOOR(),1);

        Thread elevatorThread = new Thread(elevator);
        Thread passengerGeneratorThread = new Thread(callRequestGenerator);

        elevatorThread.start();
        passengerGeneratorThread.start();
    }
}
