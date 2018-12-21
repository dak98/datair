package data.storage;

import data.source.Sensor;
import data.source.Station;
import data.source.powietrze.gov.PowietrzeGov;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Responsible for storing programs data.
 */
public class Serialization {
    /**
     * @param file
     *         Stations list save destination.
     */
    private void serializeStations(String file) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(new StationsStorage(new PowietrzeGov()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Could not find specified file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not write object.");
        }
        System.out.println("Saving stations data");
    }
    /**
     * @param file
     *         Sensors of station list save destination.
     */
    private void serializeSensorsOfStation(String file) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(new SensorsStorage(new PowietrzeGov()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Could not find specified file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not write object.");
        }
        System.out.println("Saving sensors data");
    }
    /**
     * Class for serializing program's data.
     * @param file1
     *         Stations save destination file.
     * @param file2
     *         Sensors save destination file.
     */
    public void serialize(String file1, String file2) {
        serializeStations(file1);
        serializeSensorsOfStation(file2);
    }
    /**
     * @param file
     *         File to get stations list from.
     * @return List of stations.
     *         null if error occurred.
     */
    public List<Station> getStationLists(String file) {
        StationsStorage stationsStorage = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            stationsStorage = (StationsStorage) inputStream.readObject();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
        System.out.println("Stations read.");
        return stationsStorage.getStationList();
    }
    /**
     * @param file
     *         File to get sensors list from.
     * @return List of sensors.
     *         null if error occurred.
     */
    public Map<Integer, List<Sensor>> getSensorsOfStation(String file) {
        SensorsStorage sensorsStorage = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            sensorsStorage = (SensorsStorage) inputStream.readObject();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
        System.out.println("Sensors read.");
        return sensorsStorage.getSensorsOfStationMap();
    }
}
