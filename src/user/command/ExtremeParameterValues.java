package user.command;

import data.collector.AirDataCollector;
import data.collector.visitors.GetSensorDataByParamCode;
import data.source.SensorData;
import data.source.Station;
import data.source.powietrze.gov.PowietrzeGov;

import java.util.*;

/**
 * Returns dates and stations with largest and lowest values of given parameter.
 */
public class ExtremeParameterValues extends Command {
    /**
     * @return String[1] - code of parameter.
     */
    @Override
    public String[] parse(String args) {
        StringTokenizer arguments = new StringTokenizer(args, " ");
        if (arguments.countTokens() == 1) {
            String[] argsArr = { arguments.nextToken() };
            return argsArr;
        }
        return null;
    }
    /**
     * @return -4 - Unknown parameter code.
     *         -9 - Parameter is not checked by any station.
     */
    @Override
    public int outputData(String[] args) {
        if (!isCorrectParamCode(args[0])) {
            return -4;
        }
        TreeMap<SensorData.Values, Station> maxValuePerStation = new TreeMap<>();
        TreeMap<SensorData.Values, Station> minValuePerStation = new TreeMap<>();
        AirDataCollector airDataCollector = new AirDataCollector();
        List<Station> stationsList = airDataCollector.getStationsList(new PowietrzeGov());
        for (Station station : stationsList) {
            String[] tmpArgs = { station.getStationName(), args[0] };
            SensorData sensorData = (SensorData) airDataCollector.accept(new GetSensorDataByParamCode(), tmpArgs, new PowietrzeGov());
            if (sensorData != null && getMaxValue(sensorData) != null) {
                maxValuePerStation.put(getMaxValue(sensorData), station);
            }
            if (sensorData != null && getMinValue(sensorData) != null) {
                minValuePerStation.put(getMinValue(sensorData), station);
            }
        }
        if (maxValuePerStation.size() == 0 || minValuePerStation.size() == 0) {
            return -9;
        }
        /* Wzorzec iterator - iterowanie po Set */
        NavigableMap<SensorData.Values, Station> reversedMaxValuePerStation = maxValuePerStation.descendingMap();
        System.out.println("Max: " + reversedMaxValuePerStation.firstEntry().getValue().getStationName() + " - " +
                           reversedMaxValuePerStation.firstEntry().getKey().getValue() + " " +
                           reversedMaxValuePerStation.firstEntry().getKey().getDate());
        System.out.println("Min: " + minValuePerStation.firstEntry().getValue().getStationName() + " - " +
                           minValuePerStation.firstEntry().getKey().getValue() + " " +
                           minValuePerStation.firstEntry().getKey().getDate());
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
     * @return Value of measurement for sensor with maximum value.
     *         null if no measurements were made.
     */
    private SensorData.Values getMaxValue(SensorData sensorData) {
        List<SensorData.Values> valuesList = sensorData.getListOfValues();
        if (!valuesList.isEmpty()) {
            SensorData.Values maxValue = valuesList.get(0);
            for (SensorData.Values value : valuesList) {
                if (maxValue.compareTo(value) < 0) {
                    maxValue = value;
                }
            }
            if (maxValue.getValue().equals("null")) {
                return null;
            }
            return maxValue;
        }
        return null;
    }
    /**
     * @param sensorData
     * @return Value of measurement for sensor with minimum value.
     *         null if no measurements were made.
     */
    private SensorData.Values getMinValue(SensorData sensorData) {
        List<SensorData.Values> valuesList = sensorData.getListOfValues();
        if (!valuesList.isEmpty()) {
            SensorData.Values maxValue = valuesList.get(0);
            for (SensorData.Values value : valuesList) {
                if (maxValue.compareTo(value) > 0) {
                    maxValue = value;
                }
            }
            if (maxValue.getValue().equals("null")) {
                return null;
            }
            return maxValue;
        }
        return null;
    }
}
