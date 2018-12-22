package data.source;

import data.AirIndex;
import data.Sensor;
import data.SensorData;
import data.Station;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Interface for classes responsible for getting online air data from specified {@link data.source.IOnlineDataInfo}.
 */
public interface IOnlineDataReader {
    /**
     * @return Array of all measuring stations from an online data source.
     *         null if data could not be read.
     * @throws MalformedURLException
     *         Incorrect url for getting stations data.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    Station[] readStations() throws IOException;
    /**
     * @param stationID
     * @return Array of sensors of a measuring station specified by its ID.
     * @throws MalformedURLException
     *         Incorrect url for getting sensors of specified station.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    Sensor[] readSensorsOfStation(int stationID) throws IOException;
    /**
     * @param sensorID
     * @return Air data for a sensor specified by its ID.
     * @throws MalformedURLException
     *         Incorrect url for getting data of specified sensor.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    SensorData readDataOfSensor(int sensorID) throws IOException;
    /**
     *
     * @param stationID
     * @return Air index for a measuring station specified by its ID.
     * @throws MalformedURLException
     *         Incorrect url for getting air index of specified station.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    AirIndex readAirIndex(int stationID) throws IOException;
}
