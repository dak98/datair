package data.source;

import java.util.Comparator;
import java.util.List;

/**
 * Abstract class representing air data from particular sensor.
 */
public abstract class SensorData {
    public abstract class Values implements Comparable<Values> {
        /**
         * @return Date of the measurements
         */
        public abstract String getDate();
        /**
         * @return Value measured.
         */
        public abstract String getValue();
    }
    /**
     * @return Key of sensor data.
     */
    public abstract String getKeyName();
    /**
     * @return List of date-value pairs for the measurement.
     */
    public abstract List<Values> getListOfValues();
}
