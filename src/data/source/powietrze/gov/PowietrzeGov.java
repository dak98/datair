package data.source.powietrze.gov;

import data.source.*;
import utility.JsonRecord;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Returns specified air data from powietrze.gios.gov.pl API.
 */
public class PowietrzeGov extends Source {
    private SourceInfo powietrzeGovInfo = new PowietrzeGovInfo();

    @Override
    public List<Station> getStationsList() throws IOException {
        String stationsData = null;
        try {
            URL url1 = new URL(powietrzeGovInfo.getStationsListURL());
            stationsData = new BufferedReader(new InputStreamReader(url1.openStream())).readLine();
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Incorrect url for getting list of stations.");
        } catch (IOException e) {
            throw new IOException("An error with getting list of stations has occurred.");
        }
        List<Station> stationsList = new LinkedList<>();
        JsonRecord jsonRecordParser = new JsonRecord();
        while (stationsData.length() > 1) {
            stationsData = stationsData.substring(stationsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf(',') + 1));
            stationsData = stationsData.substring(stationsData.indexOf(',') + 1);
            String stationID = jsonRecordParser.getJsonRecordValue();

            stationsData = stationsData.substring(stationsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf("\",") + 2));
            stationsData = stationsData.substring(stationsData.indexOf("\",") + 2);
            String stationName = jsonRecordParser.getJsonRecordValue();

            stationsData = stationsData.substring(stationsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf("\",") + 2));
            stationsData = stationsData.substring(stationsData.indexOf("\",") + 2);
            String gegrLat = jsonRecordParser.getJsonRecordValue();

            stationsData = stationsData.substring(stationsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf("\",") + 2));
            stationsData = stationsData.substring(stationsData.indexOf("\",") + 2);
            String gegrLon = jsonRecordParser.getJsonRecordValue();

            stationsData = stationsData.substring(stationsData.indexOf('{') + 1);
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf(",") + 1));
            stationsData = stationsData.substring(stationsData.indexOf(",") + 1);
            String cityID = jsonRecordParser.getJsonRecordValue();

            stationsData = stationsData.substring(stationsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf("\",") + 2));
            stationsData = stationsData.substring(stationsData.indexOf("\",") + 2);
            String cityName = jsonRecordParser.getJsonRecordValue();

            stationsData = stationsData.substring(stationsData.indexOf('{') + 1);
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf("\",") + 2));
            stationsData = stationsData.substring(stationsData.indexOf("\",") + 2);
            String communeName = jsonRecordParser.getJsonRecordValue();

            stationsData = stationsData.substring(stationsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf("\",") + 2));
            stationsData = stationsData.substring(stationsData.indexOf("\",") + 2);
            String districtName = jsonRecordParser.getJsonRecordValue();

            stationsData = stationsData.substring(stationsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf('}')) + ',');
            stationsData = stationsData.substring(stationsData.indexOf('}') + 1);
            String provinceName = jsonRecordParser.getJsonRecordValue();

            stationsData = stationsData.substring(stationsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(stationsData.substring(0, stationsData.indexOf('}')) + ',');
            stationsData = stationsData.substring(stationsData.indexOf('}') + 1);
            String addressStreet = jsonRecordParser.getJsonRecordValue();

            stationsList.add(new PowietrzeGovStation(stationID, stationName, gegrLat, gegrLon, cityID, cityName, communeName, districtName, provinceName, addressStreet));
        }
        return stationsList;
    }
    @Override
    public List<Sensor> getSensorsList(int stationID) throws IOException {
        String sensorsData = null;
        try {
            URL url1 = new URL(powietrzeGovInfo.getSensorsListURL(stationID));
            sensorsData = new BufferedReader(new InputStreamReader(url1.openStream())).readLine();
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Incorrect url for getting list of sensors of station " + stationID + ".");
        } catch (IOException e) {
            throw new IOException("An error with getting list of sensors of station " + stationID + " has occurred.");
        }

        List<Sensor> sensorsList = new LinkedList<>();
        JsonRecord jsonRecordParser = new JsonRecord();
        while (sensorsData.length() > 1) {
            sensorsData = sensorsData.substring(sensorsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(sensorsData.substring(0, sensorsData.indexOf(',') + 1));
            sensorsData = sensorsData.substring(sensorsData.indexOf(',') + 1);
            String sensorID = jsonRecordParser.getJsonRecordValue();

            /* Known stationID */
            sensorsData = sensorsData.substring(sensorsData.indexOf(',') + 1);

            sensorsData = sensorsData.substring(sensorsData.indexOf('{') + 1);
            jsonRecordParser = jsonRecordParser.getJsonRecord(sensorsData.substring(0, sensorsData.indexOf("\",") + 2));
            sensorsData = sensorsData.substring(sensorsData.indexOf("\",") + 2);
            String paramName = jsonRecordParser.getJsonRecordValue();

            sensorsData = sensorsData.substring(sensorsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(sensorsData.substring(0, sensorsData.indexOf("\",") + 2));
            sensorsData = sensorsData.substring(sensorsData.indexOf("\",") + 2);
            String paramForumla = jsonRecordParser.getJsonRecordValue();

            sensorsData = sensorsData.substring(sensorsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(sensorsData.substring(0, sensorsData.indexOf("\",") + 2));
            sensorsData = sensorsData.substring(sensorsData.indexOf("\",") + 2);
            String paramCode = jsonRecordParser.getJsonRecordValue();

            sensorsData = sensorsData.substring(sensorsData.indexOf('\"'));
            jsonRecordParser = jsonRecordParser.getJsonRecord(sensorsData.substring(0, sensorsData.indexOf("}")) + ',');
            sensorsData = sensorsData.substring(sensorsData.indexOf('}') + 2);
            String idParam = jsonRecordParser.getJsonRecordValue();

            sensorsList.add(new PowietrzeGovSensor(sensorID, Integer.toString(stationID), paramName, paramForumla, paramCode, idParam));
        }
        return sensorsList;
    }
    @Override
    public SensorData getSensorData(int sensorID) throws IOException {
        String sensorData = null;
        try {
            URL url1 = new URL(powietrzeGovInfo.getSensorDataURL(sensorID));
            sensorData = new BufferedReader(new InputStreamReader(url1.openStream())).readLine();
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Incorrect url for getting data of the sensor " + sensorID + ".");
        } catch (IOException e) {
            throw new IOException("An error with getting data of the sensor " + sensorID + " has occurred.");
        }

        JsonRecord jsonRecordParser = new JsonRecord();
        jsonRecordParser = jsonRecordParser.getJsonRecord(sensorData.substring(sensorData.indexOf('\"'), sensorData.indexOf("\",") + 2));
        sensorData = sensorData.substring(sensorData.indexOf("\",") + 2);
        String key = jsonRecordParser.getJsonRecordValue();

        PowietrzeGovSensorData powietrzeGovSensorData = new PowietrzeGovSensorData(sensorID, key);
        while (sensorData.length() != 12 && sensorData.length() > 2) {
            jsonRecordParser = jsonRecordParser.getJsonRecord(sensorData.substring(sensorData.indexOf('{') + 1, sensorData.indexOf("\",") + 2));
            sensorData = sensorData.substring(sensorData.indexOf("\",") + 2);
            String date = jsonRecordParser.getJsonRecordValue();

            jsonRecordParser = jsonRecordParser.getJsonRecord(sensorData.substring(0, sensorData.indexOf('}')) + ',');
            sensorData = sensorData.substring(sensorData.indexOf('}') + 1);
            String value = jsonRecordParser.getJsonRecordValue();

            powietrzeGovSensorData.addValue(date, value);
        }
        return powietrzeGovSensorData;
    }
    @Override
    public AirIndex getAirIndex(int stationID) throws IOException {
        String airIndex = null;
        try {
            URL url1 = new URL(powietrzeGovInfo.getAirIndexURL(stationID));
            airIndex = new BufferedReader(new InputStreamReader(url1.openStream())).readLine();
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Incorrect url for getting air index of station " + stationID + ".");
        } catch (IOException e) {
            throw new IOException("An error with getting air index of station " + stationID + " has occurred.");
        }

        JsonRecord jsonRecordParser = new JsonRecord();
        jsonRecordParser = jsonRecordParser.getJsonRecord(airIndex.substring(airIndex.indexOf('\"'), airIndex.indexOf(',') + 1));
        airIndex = airIndex.substring(airIndex.indexOf(',') + 1);
        String key = jsonRecordParser.getJsonRecordValue();

        PowietrzeGovAirIndex powietrzeGovAirIndex = new PowietrzeGovAirIndex(key, 8);
        for (int i = 0; i < powietrzeGovAirIndex.paramNumber; i++) {
            jsonRecordParser = jsonRecordParser.getJsonRecord(airIndex.substring(airIndex.indexOf('\"'), airIndex.indexOf(',') + 1));
            airIndex = airIndex.substring(airIndex.indexOf(',') + 1);
            String calcDate = jsonRecordParser.getJsonRecordValue();

            String indexID = null;
            String indexLevelName = null;
            /* If LevelIndex is not null */
            if (airIndex.charAt(airIndex.indexOf(':') + 1) == '{') {
                airIndex = airIndex.substring(airIndex.indexOf('{'));
                jsonRecordParser = jsonRecordParser.getJsonRecord(airIndex.substring(airIndex.indexOf('\"'), airIndex.indexOf(',') + 1));
                airIndex = airIndex.substring(airIndex.indexOf(',') + 1);
                indexID = jsonRecordParser.getJsonRecordValue();

                jsonRecordParser = jsonRecordParser.getJsonRecord(airIndex.substring(airIndex.indexOf('\"'), airIndex.indexOf('}')) + ',');
                airIndex = airIndex.substring(airIndex.indexOf(',') + 1);
                indexLevelName = jsonRecordParser.getJsonRecordValue();
            } else {
                airIndex = airIndex.substring(airIndex.indexOf(',') + 1);
            }

            jsonRecordParser = jsonRecordParser.getJsonRecord(airIndex.substring(airIndex.indexOf('\"'), airIndex.indexOf(',') + 1));
            airIndex = airIndex.substring(airIndex.indexOf(',') + 1);
            String sourceDataDate = jsonRecordParser.getJsonRecordValue();

            powietrzeGovAirIndex.setter(powietrzeGovAirIndex.setters[i], calcDate, indexID, indexLevelName, sourceDataDate);
        }
        return powietrzeGovAirIndex;
    }
}
