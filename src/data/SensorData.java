package data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Measuring data for a single sensor.
 */
public class SensorData {
    private String key;
    private Measurement[] measurements;
    public class Measurement implements Comparable<Measurement> {
        private Date date;
        private float value;
        public int compareTo(Measurement measurement) {
            return Float.compare(value, measurement.value);
        }
        /**
         * @return Date of the measurement in the format yyyy-MM-dd HH:mm:ss.
         */
        public Date getDate() {
            return date;
        }
        /**
         * @return Value of the measurement.
         */
        public float getValue() {
            return value;
        }
        @Override
        public String toString() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "date  = "    + sdf.format(date) +
                    "\nvalue = " + value            +
                    '\n';
        }
    }

    /**
     * @return Code of the parameter measured by the sensor.
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key Code of the parameter measured by the sensor.
     */
    public void setKey(String key) {
        this.key = key;
    }
    /**
     * @return Date-value pairs of measurements for the sensor.
     */
    public Measurement[] getMeasurements() {
        return measurements;
    }
    /**
     * @param measurements
     *        Date-value pairs of measurements for the sensor.
     */
    public void setMeasurements(Measurement[] measurements) {
        this.measurements = measurements;
    }
    @Override
    public String toString() {
        StringBuilder sensorData = new StringBuilder();
        sensorData.append("key    = ")
                  .append(key)
                  .append("\nvalues = ");
        if (measurements == null) {
            sensorData.append(measurements);
        } else {
            for (Measurement measurement : measurements) {
                sensorData.append(measurement);
            }
        }
        return sensorData.toString();
    }
}
