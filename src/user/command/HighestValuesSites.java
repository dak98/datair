package user.command;

import data.collector.AirDataCollector;
import data.collector.visitors.GetSensorDataByParamCode;
import data.source.SensorData;
import data.source.Station;
import data.source.powietrze.gov.PowietrzeGov;

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
    public int outputData(String[] args) {
        if (!isCorrectParamCode(args[0])) {
            return -4;
        }
        TreeMap<SensorData.Values, Station> maxValuePerStation = new TreeMap<>();
        AirDataCollector airDataCollector = new AirDataCollector();
        List<Station> stationsList = airDataCollector.getStationsList(new PowietrzeGov());
        for (Station station : stationsList) {
            String[] tmpArgs = { station.getStationName(), args[0] };
            SensorData sensorData = (SensorData) airDataCollector.accept(new GetSensorDataByParamCode(), tmpArgs, new PowietrzeGov());
            if (sensorData != null && getMaxValue(sensorData, args[2]) != null) {
                maxValuePerStation.put(getMaxValue(sensorData, args[2]), station);
            }
        }
        if (maxValuePerStation.size() < Integer.parseInt(args[1])) {
            return -8;
        }
        /* Wzorzec iterator - iterowanie po Set */
        NavigableMap<SensorData.Values, Station> reversedMaxValuePerStation = maxValuePerStation.descendingMap();
        Iterator<Map.Entry<SensorData.Values, Station>> valuesAndStationsSetIterator = reversedMaxValuePerStation.entrySet().iterator();
        for (int i = Integer.parseInt(args[1]); i > 0; i--) {
            Map.Entry<SensorData.Values, Station> valueAndStation = valuesAndStationsSetIterator.next();
            System.out.println(valueAndStation.getValue().getStationName() + ": " + valueAndStation.getKey().getValue());
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
    private SensorData.Values getMaxValue(SensorData sensorData, String date) {
        List<SensorData.Values> valuesList = sensorData.getListOfValues();
        for (SensorData.Values value : valuesList) {
            if (value.getDate().equals(date)) {
                return value;
            }
        }
        return null;
    }
}
