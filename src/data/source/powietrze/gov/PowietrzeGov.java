package data.source.powietrze.gov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.AirIndex;
import data.Sensor;
import data.SensorData;
import data.Station;
import data.source.*;
import data.storage.AirIndexDeserializer;
import data.storage.SensorDataDeserializer;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Returns specified air data from powietrze.gios.gov.pl API.
 */
public class PowietrzeGov implements IOnlineDataReader {
    private IOnlineDataInfo powietrzeGovInfo = new PowietrzeGovInfo();

    @Override
    public Station[] readStations() throws IOException {
        String stationsData = null;
        try {
            stationsData = readData(powietrzeGovInfo.stationsURL());
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Incorrect url for getting stations data.");
        } catch (IOException e) {
            String message;
            if (e.getMessage() == null) {
                message = "An error with getting stations data has occurred.";
            } else {
                message = "Could not close input stream.";
            }
            throw new IOException(message);
        }
        if (stationsData == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(stationsData, Station[].class);
    }
    @Override
    public Sensor[] readSensorsOfStation(int stationID) throws IOException {
        String sensorsData = null;
        try {
            sensorsData = readData(powietrzeGovInfo.sensorsOfStationURL(stationID));
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Incorrect url for getting sensors of station " + stationID + ".");
        } catch (IOException e) {
            String message;
            if (e.getMessage() == null) {
                message = "An error with getting sensors if station " + stationID + " has occurred.";
            } else {
                message = "Could not close input stream.";
            }
            throw new IOException(message);
        }
        if (sensorsData == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(sensorsData, Sensor[].class);
    }
    @Override
    public SensorData readDataOfSensor(int sensorID) throws IOException {
        String dataOfSensor = null;
        try {
            dataOfSensor = readData(powietrzeGovInfo.dataOfSensorURL(sensorID));
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Incorrect url for getting data of sensor " + sensorID + ".");
        } catch (IOException e) {
            String message;
            if (e.getMessage() == null) {
                message = "An error with getting data of sensor " + sensorID + " has occurred.";
            } else {
                message = "Could not close input stream.";
            }
            throw new IOException(message);
        }
        if (dataOfSensor == null) {
            return null;
        }
        Gson gson = new GsonBuilder().registerTypeAdapter(SensorData.class, new SensorDataDeserializer())
                                     .setDateFormat("yyyy-MM-dd HH:mm:ss")
                                     .create();
        return gson.fromJson(dataOfSensor, SensorData.class);
    }
    @Override
    public AirIndex readAirIndex(int stationID) throws IOException {
        String airIndex = null;
        try {
            airIndex = readData(powietrzeGovInfo.airIndexURL(stationID));
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Incorrect url for getting air index of station " + stationID + ".");
        } catch (IOException e) {
            String message;
            if (e.getMessage() == null) {
                message = "An error with getting air index of station " + stationID + " has occurred.";
            } else {
                message = "Could not close input stream.";
            }
            throw new IOException(message);
        }
        if (airIndex == null) {
            return null;
        }
        Gson gson = new GsonBuilder().registerTypeAdapter(AirIndex.class, new AirIndexDeserializer())
                                     .create();
        return gson.fromJson(airIndex, AirIndex.class);
    }
    /**
     * @param urlString
     * @return String with data read from specified url.
     *         null if no data was read.
     * @throws IOException
     *         An error occurred while reading data.
     */
    private String readData(String urlString) throws IOException {
        String data = null;
        InputStreamReader isr = null;
        try {
            URL url = new URL(urlString);
            isr = new InputStreamReader(url.openStream());
            data = new BufferedReader(isr).readLine();
        } catch (MalformedURLException e) {
            throw new MalformedURLException();
        } catch (IOException e) {
            throw new IOException();
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    throw new IOException("Could not close input stream.");
                }
            }
        }
        return data;
    }
}
