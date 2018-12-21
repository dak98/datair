package data.source;

import java.io.Externalizable;

/**
 * Abstract class representing a measuring station.
 */
public abstract class Station implements Externalizable {
    public Station() {}
    /**
     * @return Name of the station.
     */
    public abstract String getStationName();
    /**
     * @return ID of the station.
     */
    public abstract int getStationID();
    /**
     * @return String with key-value pairs of the station.
     */
    public abstract String toString();
}
