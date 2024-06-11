package com.bluestaq.elevatorsim;

/**
 * @class Building
 * A class representing a building
 * Implemented as a singleton - there can only be one building
 */
public class Building {

    private int MAX_FLOOR = Defaults.MAX_FLOOR;
    private final int MIN_FLOOR = Defaults.MIN_FLOOR;
    //Setting as final to prevent modification

    private Elevator elevator;

    private static Building instance;

    /**
     * constructor: Building
     * Making private so it cannot be called externally
     */
    private Building(){}

    /**
     * @method: get_Instance
     * standard instance retrieval method signature for singleton
     * @return
     */
    public static Building get_Instance(){
        if (instance == null) {
            instance = new Building();
            instance.setElevator(Elevator.getInstance());
        }
        return instance;
     }

    /**
     * Adding accessor methods
    */
    public int getMAX_FLOOR() {
        return MAX_FLOOR;
    }

    public void setMAX_FLOOR(int MAX_FLOOR) {
        this.MAX_FLOOR = MAX_FLOOR;
    }

    public int getMIN_FLOOR() {
        return MIN_FLOOR;
    }

    /**
     * method getElevator
     * @return Elevator object
     */
    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }
}
