package com.bluestaq.elevatorsim;

/**
 * class ElevatorStatusWriter
 * Class responsible for outputting the elevator's location and status
 */
public class ElevatorStatusWriter {
    private final StringBuilder stringBuilder;

    public ElevatorStatusWriter() {

        stringBuilder = new StringBuilder();
    }

    private void appendWaitingPassengerSymbol(CallRequest callRequest) {
        stringBuilder.append("(")
                .append(callRequest.departureFloor)
                .append("-->")
                .append(callRequest.destinationFloor)
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
                .append(elevator.getPassengersInElevator().size())
                .append(" passengers]\t");
    }

    public void WriteElevatorStatus(IElevator elevator) {
        stringBuilder.append("Current Floor: ").append(elevator.getCurrentFloor()).append("\n")
                     .append("Requests: ").append(elevator.getCallList()).append("\n");

        for(int i = Building.get_Instance().getMAX_FLOOR(); i>= Building.get_Instance().getMIN_FLOOR(); i--) {
            stringBuilder.append(i).append("\t");

            stringBuilder.append("Floor: ").append(elevator.getCurrentFloor());

            if(i == elevator.getCurrentFloor()) {
                appendElevatorSymbol(elevator);
            }


            for(CallRequest callRequest : elevator.getWaitingPassengers()) {
                if(callRequest.departureFloor == i) {
                    appendWaitingPassengerSymbol(callRequest);
                }
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length()-1);
    }
}
