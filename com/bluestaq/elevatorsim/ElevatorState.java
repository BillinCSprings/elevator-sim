package com.bluestaq.elevatorsim;

public enum ElevatorState {
    ACCELERATING{
        /**
         * method toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Accelerating";
        }
    },

    ASCENDING{
        /**
         * method toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Ascending";
        }
    },
    DECCELERATING{
        /**
         * method toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Deccelerating";
        }
    },
    DESCENDING{
        /**
         * method toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Descending";
        }
    },
    IDLE{
        /**
         * method toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Idle";
        }
    },
    STOPPING{
        /**
         * method toString
         * @return String representation of enum value in title case
         */
        @Override

        public String toString() {
            return "Stopping";
        }
    }
 }
