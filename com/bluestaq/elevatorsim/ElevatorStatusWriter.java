package com.bluestaq.elevatorsim;

public class ElevatorStatusWriter {
    private final StringBuilder stringBuilder;

    public ElevatorStatusWriter() {

        stringBuilder = new StringBuilder();
    }

    private void appendWaitingPassengerSymbol(Passenger passenger) {
        stringBuilder.append("(")
                .append(passenger.departureFloor)
                .append("-->")
                .append(passenger.destinationFloor)
                .append(")");
    }

    private void appendElevatorSymbol(IElevator elevator) {
        stringBuilder.append("[")
                .append(elevator.getElevatorState().name())
                .append(" | (")
                .append(elevator.getDepartureFloor())
                .append("-->")
                .append(elevator.getDestinationFloor())
                .append(") | ")
                .append(elevator.getInsidePassengers().size())
                .append(" passengers]\t");
    }

    public void print(IElevator elevator) {
        stringBuilder.append("ALL REQUESTS: ").append(elevator.getAllRequests()).append("\n");
        for(int i = Building.get_Instance().getMAX_FLOOR(); i>= 0; i--) {
            stringBuilder.append(i).append("\t");

            if(i == elevator.getCurrentFloor()) {
                appendElevatorSymbol(elevator);
            } else {
                stringBuilder.append("_____________________________________\t");
            }

            for(Passenger passenger : elevator.getWaitingPassengers()) {
                if(passenger.departureFloor == i) {
                    appendWaitingPassengerSymbol(passenger);
                }
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder);
        stringBuilder.delete(0, stringBuilder.length()-1);
    }
}
