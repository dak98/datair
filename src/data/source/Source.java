package data.source;

import java.io.IOException;
import java.util.List;

/**
 * Abstract class for getting air data from specified {@link data.source.SourceInfo}.
 */
public abstract class Source {
    /**
     * @return List of JSON objects for stations.
     * @throws IOException
     *          An error has occurred while getting data from specified URL.
     */
    public abstract List<Station> getStationsList() throws IOException;
    /**
     * @param stationID
     * @return List of sensors of specified station.
     * @throws IOException
     *          An error has occurred while getting data from specified URL.
     */
    public abstract List<Sensor> getSensorsList(int stationID) throws IOException;
    /**
     * @param sensorID
     * @return Air data for specified sensor.
     * @throws IOException
     *          An error has occurred while getting data from specified URL.
     */
    public abstract SensorData getSensorData(int sensorID) throws IOException;
    /**
     *
     * @param stationID
     * @return Air index for specified station.
     * @throws IOException
     *          An error has occurred while getting data from specified URL.
     */
    public abstract AirIndex getAirIndex(int stationID) throws IOException;
}
