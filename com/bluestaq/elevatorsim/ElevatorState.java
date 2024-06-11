package com.bluestaq.elevatorsim;

public enum ElevatorState {
    ACCELERATING{
        /**
         * @toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Accelerating";
        }
    },

    ASCENDING{
        /**
         * @toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Ascending";
        }
    },
    DECCELERATING{
        /**
         * @toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Deccelerating";
        }
    },
    DESCENDING{
        /**
         * @toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Descending";
        }
    },
    IDLE{
        /**
         * @toString
         * @return String representation of enum value in title case
         */
        @Override
        public String toString() {
            return "Idle";
        }
    },
    STOPPING{
        /**
         * @toString
         * @return String representation of enum value in title case
         */
        @Override

        public String toString() {
            return "Stopping";
        }
    };
 }
