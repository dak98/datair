package data.collector;

import data.source.*;
import data.source.powietrze.gov.PowietrzeGov;
import data.storage.Serialization;

import java.io.IOException;
import java.util.*;

/**
 * Main storage of air data.
 */
public class AirDataCollector {
    private static List<Station> stationsList = new LinkedList<>();
    private static Map<Integer, List<Sensor>> sensorsOfStation = new HashMap<>();
    private static Map<Integer, List<SensorData>> dataOfSensor = new HashMap<>();
    private static Map<Integer, List<AirIndex>> airIndexesOfStation = new HashMap<>();
    /**
     * Adds list of {@link data.source.Station} to {@link AirDataCollector#stationsList}.
     * @param dataSource
     *         Object responsible for getting air data from the internet.
     */
    private void setStationsList(Source dataSource) {
        try {
            stationsList = dataSource.getStationsList();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * @param dataSource
     *         Object responsible for getting air data from the internet.
     * @return List of {@link data.source.Station}.
     */
    public List<Station> getStationsList(Source dataSource) {
        if (stationsList == null || stationsList.isEmpty()) {
            setStationsList(dataSource);
        }
        return stationsList;
    }
    /**
     * Adds list of {@link data.source.Sensor} of given station to {@link AirDataCollector#sensorsOfStation}.
     * @param stationID
     * @param dataSource
     *         Object responsible for getting air data from the internet.
     */
    private void setSensorsOfStation(int stationID, Source dataSource) {
        try {
            sensorsOfStation.put(stationID, dataSource.getSensorsList(stationID));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * @param stationID
     * @param dataSource
     *         Object responsible for getting air data from the internet.
     * @return List of sensors of specified station.
     */
    public List<Sensor> getSensorsOfStation(int stationID, Source dataSource) {
        if (sensorsOfStation.get(stationID) == null) {
            setSensorsOfStation(stationID, dataSource);
        }
        return sensorsOfStation.get(stationID);
    }
    /**
     * Adds single {@link data.source.SensorData} to the list of sensor data of given sensor from {@link AirDataCollector#dataOfSensor}.
     * @param sensorID
     * @param dataSource
     *         Object responsible for getting air data from the internet.
     */
    private void setSensorData(int sensorID, Source dataSource) {
        try {
            List<SensorData> sensorDataList = new LinkedList<>();
            sensorDataList.add(dataSource.getSensorData(sensorID));
            dataOfSensor.put(sensorID, sensorDataList);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * @param sensorID
     * @param dataSource
     * @return List of sensor data of specified sensor.
     */
    public List<SensorData> getSensorData(int sensorID, Source dataSource) {
        if (dataOfSensor.get(sensorID) == null) {
            setSensorData(sensorID, dataSource);
        }
        return dataOfSensor.get(sensorID);
    }
    /**
     * Adds single {@link data.source.AirIndex} to the list of air indexes of given station from {@link AirDataCollector#airIndexesOfStation}.
     * @param stationID
     * @param dataSource
     *         Object responsible for getting air data from the internet.
     */
    private void setAirIndexOfStation(int stationID, Source dataSource) {
        try {
            List<AirIndex> airIndexList = new LinkedList<>();
            airIndexList.add(dataSource.getAirIndex(stationID));
            airIndexesOfStation.put(stationID, airIndexList);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * @param stationID
     * @param dataSource
     *         Object responsible for getting air data from the internet.
     * @return List of air indexes of specified station.
     */
    public List<AirIndex> getAirIndexOfStation(int stationID, Source dataSource) {
        if (airIndexesOfStation.get(stationID) == null) {
            setAirIndexOfStation(stationID, dataSource);
        }
        return airIndexesOfStation.get(stationID);
    }
    public Map<Integer, List<Sensor>> getSensorsOfStationMap() {
        return sensorsOfStation;
    }
    /**
     * Serialises current stations and sensors.
     */
    public void save() {
        Serialization serialization = new Serialization();
        serialization.serialize("stations", "sensors");
    }
    /**
     * Loads serialized data.
     */
    public void load() {
        Serialization serialization = new Serialization();
        System.out.println("Loading stations.");
        List<Station> stationsTmpList = serialization.getStationLists("stations");
        System.out.println("Stations loaded successfully.");
        System.out.println("Loading data.");
        if (stationsTmpList != null) {
            stationsList = stationsTmpList;
        }
        for (Station station : stationsList) {
            setSensorsOfStation(station.getStationID(), new PowietrzeGov());
            for (Sensor sensor : sensorsOfStation.get(station.getStationID())) {
                setSensorData(sensor.getSensorID(), new PowietrzeGov());
            }
            setAirIndexOfStation(station.getStationID(), new PowietrzeGov());
        }
        System.out.println("Data loaded successfully.");
    }
    /**
     * Invokes one of the visitors from {@link data.collector.visitors}.
     * @param visitor
     *         Instance of one of the visitors.
     * @param args
     *         Parameters of the visitor.
     * @param dataSource
     *         Source for getting new data from.
     * @return Return of the visitor.
     */
    public Object accept(Visitor visitor, String[] args, Source dataSource) {
        return visitor.visit(this, args, dataSource);
    }
}
