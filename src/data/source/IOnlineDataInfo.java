package data.source;

/**
 * Interface for classes providing urls for an air data source.
 */
public interface IOnlineDataInfo {
    /**
     * @return URL for getting data about a measuring stations.
     */
    String stationsURL();
    /**
     * @param stationID
     * @return URL for getting sensors of a measuring station specified by its ID.
     */
    String sensorsOfStationURL(int stationID);
    /**
     * @param sensorID
     * @return URL for getting air data for a sensor specified by its ID.
     */
    String dataOfSensorURL(int sensorID);
    /**
     * @param stationID
     * @return URL for getting an air index of a measuring station specified by its ID.
     */
    String airIndexURL(int stationID);
}
