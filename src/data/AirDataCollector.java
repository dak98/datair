package data;

import com.google.gson.Gson;
import data.visitors.Visitor;
import data.source.*;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

/**
 * Main storage of air data.
 */
public class AirDataCollector {
    private static Station[] stations = null;
    private static Map<Integer, Sensor[]> sensorsOfStation = new HashMap<>();
    private static Map<Integer, SensorData> dataOfSensor = new HashMap<>();
    private static Map<Integer, List<AirIndex>> airIndexesOfStation = new HashMap<>();

    private final String stationsStoragePath = "storage/stations";
    private final String sensorsStoragePath = "storage/sensors/sensor";
    private final String sensorDataStoragePath = "storage/sensorData/sensorData";
    /**
     * Collects data for measuring stations and stores them in {@link AirDataCollector#stations}.
     * @param dataSource
     *        Object responsible for getting air data from the internet.
     * @throws MalformedURLException
     *         Incorrect url for getting stations data.
     * @throws IOException
     *         An error has occurred while getting data from specified URL or file.
     */
    private void setStations(IOnlineDataReader dataSource) throws IOException {
        Gson gson = new Gson();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(stationsStoragePath))) {
            stations = gson.fromJson((String) inputStream.readObject(), Station[].class);
        } catch (FileNotFoundException | ClassNotFoundException e) {
            stations = null;
        } catch (IOException e) {
            throw new IOException("Could not load measuring stations' data.");
        }
        if (stations == null) {
            stations = dataSource.readStations();
            storeStations();
        }
    }
    /**
     * Returns data for measuring stations stored in the program or collected them different sources.
     * @param dataSource
     *        Object responsible for getting air data from the internet.
     * @return Array of {@link Station} stored in the program.
     *         null if there are no measuring stations.
     * @throws MalformedURLException
     *         Incorrect url for getting stations data.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    public Station[] getStations(IOnlineDataReader dataSource) throws IOException {
        if (stations == null) {
            setStations(dataSource);
        }
        return stations;
    }
    /**
     * Stores data for measuring stations currently in the program.
     * @throws FileNotFoundException
     *         Specified file for storing measuring stations' data not found.
     * @throws IOException
     *         Could not store measuring stations' data.
     */
    private void storeStations() throws IOException {
        Gson gson = new Gson();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(stationsStoragePath))) {
            outputStream.writeObject(gson.toJson(stations));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Specified file for storing measuring stations' data not found.");
        } catch (IOException e) {
            throw new IOException("Could not store measuring stations' data.");
        }
    }
    /**
     * Collects sensors of measuring station specified by its ID and stores them in {@link AirDataCollector#sensorsOfStation}.
     * @param stationID
     * @param dataSource
     *        Object responsible for getting air data from the internet.
     * @throws MalformedURLException
     *         Incorrect url for getting sensors of specified station.
     * @throws IOException
     *         An error has occurred while getting data from specified URL or file.
     */
    private void setSensorsOfStation(int stationID, IOnlineDataReader dataSource) throws IOException {
        Gson gson = new Gson();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(sensorsStoragePath + stationID))) {
            Sensor[] sensors = gson.fromJson((String) inputStream.readObject(), Sensor[].class);
            sensorsOfStation.put(stationID, sensors);
        } catch (FileNotFoundException | ClassNotFoundException e) {
            sensorsOfStation.put(stationID, null);
        } catch (IOException e) {
            throw new IOException("Could not load sensors of measuring stations " + stationID + ".");
        }
        if (sensorsOfStation.get(stationID) == null) {
            sensorsOfStation.put(stationID, dataSource.readSensorsOfStation(stationID));
            storeSensorsOfStations(stationID);
        }
    }
    /**
     * Returns data of a {@link Sensor} of a measuring station specified by its ID stored in the program or collected from different sources.
     * @param stationID
     * @param dataSource
     *        Object responsible for getting air data from the internet.
     * @return Array sensors of a measuring station specified by its ID.
     *         null if there are no sensors for the specified station.
     * @throws MalformedURLException
     *         Incorrect url for getting sensors of specified station.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    public Sensor[] getSensorsOfStation(int stationID, IOnlineDataReader dataSource) throws IOException {
        if (sensorsOfStation.get(stationID) == null) {
            setSensorsOfStation(stationID, dataSource);
        }
        return sensorsOfStation.get(stationID);
    }
    /**
     * Stores sensors of the specified measuring station currently in the program.
     * @param stationID
     * @throws FileNotFoundException
     *         Specified file for storing sensors of the measuring station not found.
     * @throws IOException
     *         Could not store sensors of the measuring station.
     */
    private void storeSensorsOfStations(int stationID) throws IOException {
        Gson gson = new Gson();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(sensorsStoragePath + stationID))) {
            outputStream.writeObject(gson.toJson(sensorsOfStation.get(stationID)));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Specified file for storing sensors of the measuring station " + stationID + " not found.");
        } catch (IOException e) {
            throw new IOException("Could not store sensors of the measuring station " + stationID + ".");
        }
    }
    /**
     * Collects data of a sensor specified by its ID and merges them into one {@link SensorData} object.
     * @param sensorID
     * @param dataSource
     *        Object responsible for getting air data from the internet.
     * @throws MalformedURLException
     *         Incorrect url for getting data of specified sensor.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    private void setDataOfSensor(int sensorID, IOnlineDataReader dataSource) throws IOException {
        // TODO: Add merging of different SensorData objects.
        Gson gson = new Gson();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(sensorDataStoragePath + sensorID))) {
            SensorData sensorData = gson.fromJson((String) inputStream.readObject(), SensorData.class);
            dataOfSensor.put(sensorID, sensorData);
        } catch (FileNotFoundException | ClassNotFoundException e) {
            sensorsOfStation.put(sensorID, null);
        } catch (IOException e) {
            throw new IOException("Could not load data for sensor " + sensorID + ".");
        }
        if (dataOfSensor.get(sensorID) == null) {
            dataOfSensor.put(sensorID, dataSource.readDataOfSensor(sensorID));
            storeSensorData(sensorID);
        }
    }
    /**
     * Returns {@link SensorData} of a sensor specified by its ID stored in the program or collected from different sources.
     * @param sensorID
     * @param dataSource
     *        Object responsible for getting air data from the internet.
     * @return {@link SensorData} object with all the measurements of the specified sensor.
     *         null if there is no data for the sensor.
     * @throws MalformedURLException
     *         Incorrect url for getting data of specified sensor.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    public SensorData getSensorData(int sensorID, IOnlineDataReader dataSource) throws IOException {
        if (dataOfSensor.get(sensorID) == null) {
            setDataOfSensor(sensorID, dataSource);
        }
        return dataOfSensor.get(sensorID);
    }
    /**
     * Stores data of specified sensors currently in the program.
     * @param sensorID
     * @throws FileNotFoundException
     *         Specified file for storing data of sensor not found.
     * @throws IOException
     *         Could not store data of sensor.
     */
    private void storeSensorData(int sensorID) throws IOException {
        Gson gson = new Gson();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(sensorDataStoragePath + sensorID))) {
            outputStream.writeObject(gson.toJson(dataOfSensor.get(sensorID)));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Specified file for storing data of sensor " + sensorID + " not found.");
        } catch (IOException e) {
            throw new IOException("Could not store data of sensor " + sensorID + ".");
        }
    }
    /**
     * Collects air indexes of a station specified by its ID and stores them in {@link AirDataCollector#airIndexesOfStation}.
     * @param stationID
     * @param dataSource
     *        Object responsible for getting air data from the internet.
     * @throws MalformedURLException
     *         Incorrect url for getting air index of specified station.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    private void setAirIndexes(int stationID, IOnlineDataReader dataSource) throws IOException {
        List<AirIndex> airIndexList = new LinkedList<>();
        airIndexList.add(dataSource.readAirIndex(stationID));
        airIndexesOfStation.put(stationID, airIndexList);
    }
    /**
     * Returns list of {@link AirIndex} of a station specified by its ID stored in the program or collected from different sources.
     * @param stationID
     * @param dataSource
     *        Object responsible for getting air data from the internet.
     * @return List of air indexes of the specified station.
     *         null if there are no air indexes of specified station.
     * @throws MalformedURLException
     *         Incorrect url for getting air index of specified station.
     * @throws IOException
     *         An error has occurred while getting data from specified URL.
     */
    public List<AirIndex> getAirIndexOfStation(int stationID, IOnlineDataReader dataSource) throws IOException {
        if (airIndexesOfStation.get(stationID) == null) {
            setAirIndexes(stationID, dataSource);
        }
        return airIndexesOfStation.get(stationID);
    }
    /**
     * Invokes one of the visitors from {@link data.visitors}.
     * @param visitor
     *        Instance of one of the visitors.
     * @param args
     *        Parameters of the visitor.
     * @param dataSource
     *        Source for getting new data from.
     * @return Return value of the visitor.
     */
    public Object accept(Visitor visitor, String[] args, IOnlineDataReader dataSource) throws IOException {
        return visitor.visit(this, args, dataSource);
    }
}
