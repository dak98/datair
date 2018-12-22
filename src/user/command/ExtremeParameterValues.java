package user.command;

import data.AirDataCollector;
import data.visitors.GetSensorDataByParamCode;
import data.SensorData;
import data.Station;
import data.source.powietrze.gov.PowietrzeGov;

import java.io.IOException;
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
    public int outputData(String[] args) throws IOException {
        if (!isCorrectParamCode(args[0])) {
            return -4;
        }
        TreeMap<SensorData.Measurement, Station> maxValuePerStation = new TreeMap<>();
        TreeMap<SensorData.Measurement, Station> minValuePerStation = new TreeMap<>();
        AirDataCollector airDataCollector = new AirDataCollector();
        Station[] stations = airDataCollector.getStations(new PowietrzeGov());
        for (Station station : stations) {
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
        NavigableMap<SensorData.Measurement, Station> reversedMaxValuePerStation = maxValuePerStation.descendingMap();
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
    private SensorData.Measurement getMaxValue(SensorData sensorData) {
        SensorData.Measurement[] measurements = sensorData.getMeasurements();
        if (measurements.length > 0) {
            SensorData.Measurement maxValue = measurements[0];
            for (SensorData.Measurement measurement : measurements) {
                if (measurement != null && maxValue.compareTo(measurement) < 0) {
                    maxValue = measurement;
                }
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
    private SensorData.Measurement getMinValue(SensorData sensorData) {
        SensorData.Measurement[] measurements = sensorData.getMeasurements();
        if (measurements.length > 0) {
            SensorData.Measurement minValue = measurements[0];
            for (SensorData.Measurement measurement : measurements) {
                if (measurement != null && minValue.compareTo(measurement) > 0) {
                    minValue = measurement;
                }
            }
            return minValue;
        }
        return null;
    }
}
