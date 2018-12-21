package data.source;

/**
 * Abstract class returning urls for air data source.
 */
public abstract class SourceInfo {
    /**
     *
     * @return URL for getting list of stations.
     */
    public abstract String getStationsListURL();
    /**
     *
     * @param stationID
     * @return URL for getting list of sensors of specified station.
     */
    public abstract String getSensorsListURL(int stationID);
    /**
     *
     * @param sensorID
     * @return URL for getting data from specified sensor.
     */
    public abstract String getSensorDataURL(int sensorID);
    /**
     *
     * @param stationID
     * @return URL for getting air index from specified station.
     */
    public abstract String getAirIndexURL(int stationID);
}
