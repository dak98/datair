package data.source;

import java.io.Externalizable;

/**
 * Abstract class representing a measuring sensor.
 */
public abstract class Sensor implements Externalizable {
    public Sensor() {}
    /**
     * @return ID of sensor.
     */
    public abstract int getSensorID();
    /**
     * @return Code of sensor's parameter.
     */
    public abstract String getSensorParamCode();
    /**
     * @return String with key-value pairs of the sensor.
     */
    public abstract String toString();
}
