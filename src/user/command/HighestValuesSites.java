package user.command;

import data.AirDataCollector;
import data.visitors.GetSensorDataByParamCode;
import data.SensorData;
import data.Station;
import data.source.powietrze.gov.PowietrzeGov;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Stations with larges value of given parameter on specified date.
 */
public class HighestValuesSites extends Command {
    /**
     * @return String[3] - Code of the parameter, number of stations and date of measurement.
     */
    @Override
    public String[] parse(String args) {
        StringTokenizer arguments = new StringTokenizer(args, " ");
        if (arguments.countTokens() == 4) {
            String[] argsArr = { arguments.nextToken(), arguments.nextToken(), arguments.nextToken() + " " + arguments.nextToken() };
            return argsArr;
        }
        return null;
    }
    /**
     * @return -4 - Unknown parameter code.
     *         -8 - Parameter is not checked by specified number of stations.
     */
    @Override
    public int outputData(String[] args) throws IOException {
        if (!isCorrectParamCode(args[0])) {
            return -4;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(args[2]);
        } catch (ParseException e) {
            // Could not parse given date.
            // TODO: Date could not be parsed.
        }
        TreeMap<SensorData.Measurement, Station> maxValuePerStation = new TreeMap<>();
        AirDataCollector airDataCollector = new AirDataCollector();
        Station[] stations = airDataCollector.getStations(new PowietrzeGov());
        for (Station station : stations) {
            String[] tmpArgs = { station.getStationName(), args[0] };
            SensorData sensorData = (SensorData) airDataCollector.accept(new GetSensorDataByParamCode(), tmpArgs, new PowietrzeGov());
            if (sensorData != null && getMaxValue(sensorData, date) != null) {
                maxValuePerStation.put(getMaxValue(sensorData, date), station);
            }
        }
        if (maxValuePerStation.size() < Integer.parseInt(args[1])) {
            return -8;
        }
        /* Wzorzec iterator - iterowanie po Set */
        NavigableMap<SensorData.Measurement, Station> reversedMaxValuePerStation = maxValuePerStation.descendingMap();
        Iterator<Map.Entry<SensorData.Measurement, Station>> valuesAndStationsSetIterator = reversedMaxValuePerStation.entrySet().iterator();
        for (int i = Integer.parseInt(args[1]); i > 0; i--) {
            Map.Entry<SensorData.Measurement, Station> measurementStationEntry = valuesAndStationsSetIterator.next();
            System.out.println(measurementStationEntry.getValue().getStationName() + ": " + measurementStationEntry.getKey().getValue());
        }
        return 0;
    }
    /**
     * @param paramCode
     * @return True if paramCode is checked by the stations.
     *         False otherwise.
     */
    private boolean isCorrectParamCode(String paramCode) {
        String[] paramCodes = { "ST", "SO2", "NO2", "CO", "PM10", "PM25", "O3", "C6H6" };
        for (String singleParamCode : paramCodes) {
            if (singleParamCode.equals(paramCode)) {
                return true;
            }
        }
        return false;
    }
    /**
     * @param sensorData
     * @param date
     * @return Value of measurement for sensor on specified date.
     *         null if no measurement was made on specified date.
     */
    private SensorData.Measurement getMaxValue(SensorData sensorData, Date date) {
        SensorData.Measurement[] measurements = sensorData.getMeasurements();
        for (SensorData.Measurement measurement : measurements) {
            if (measurement.getDate().equals(date)) {
                return measurement;
            }
        }
        return null;
    }
}
