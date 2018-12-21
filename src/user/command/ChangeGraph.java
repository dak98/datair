package user.command;

import data.collector.AirDataCollector;
import data.collector.visitors.GetSensorDataByParamCode;
import data.collector.visitors.GetStationsByParamCode;
import data.source.SensorData;
import data.source.Station;
import data.source.powietrze.gov.PowietrzeGov;
import utility.DatesComparator;

import java.util.*;

/**
 * Graphs changes of given parameter.
 */
public class ChangeGraph extends Command {
    /**
     * @return String[3] - parameter code, start date, end date.
     */
    @Override
    public String[] parse(String args) {
        StringTokenizer arguments = new StringTokenizer(args, " ");
        if (arguments.countTokens() == 5) {
            String[] argsArray = {arguments.nextToken(), arguments.nextToken() + " " + arguments.nextToken(), arguments.nextToken() + " " + arguments.nextToken()};
            return argsArray;
        }
        return null;
    }
    /**
     * @return -4 - Unknown parameter code.
     *         -5 - No measurements between specified dates.
     */
    @Override
    public int outputData(String[] args) {
        if (!isCorrectParamCode(args[0])) {
            return -4;
        }
        AirDataCollector airDataCollector = new AirDataCollector();
        String[] tmpArgs1 = { args[0] };
        List<Station> stationsList = (List<Station>) airDataCollector.accept(new GetStationsByParamCode(), tmpArgs1, new PowietrzeGov());
        List<List<SensorData.Values>> valuesOfStation = new LinkedList<>();
        int longestName = 0;
        for (Station station : stationsList) {
            if (longestName < station.getStationName().length()) {
                longestName = station.getStationName().length();
            }
            String[] tmpArgs2 = { station.getStationName(), args[0] };
            SensorData sensorData = (SensorData) airDataCollector.accept(new GetSensorDataByParamCode(), tmpArgs2, new PowietrzeGov());
            if (sensorData != null) {
                valuesOfStation.add(sensorData.getListOfValues());
            }
        }
        List<ListIterator<SensorData.Values>> valuesIterators = new LinkedList<>();
        DatesComparator datesComparator = new DatesComparator();
        for (List<SensorData.Values> valuesList : valuesOfStation) {
            valuesIterators.add(valuesList.listIterator(valuesList.size()));
        }
        boolean stop = false;
        boolean iter = false;
        while (!stop) {
            int i = 0;
            for (ListIterator<SensorData.Values> valuesIterator : valuesIterators) {
                if (valuesIterator.hasPrevious()) {
                    SensorData.Values value = valuesIterator.previous();
                    if (datesComparator.isBetweenDates(value.getDate(), args[1], args[2])) {
                        graph(stationsList.get(i).getStationName(), longestName, value.getDate(), value.getValue(), 50.0f);
                        i++;
                        iter = true;
                    } else if (datesComparator.isLaterDate(value.getDate(), args[2])) {
                        stop = true;
                    }
                }
            }
        }
        if (!iter) {
            return -5;
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
     * Graphs given parameters.
     * @param stationName
     * @param longestName
     *         Value of the longest name of a measuring station.
     * @param date
     * @param value
     *         Value of measurement.
     * @param scalar
     *         Scales the number of output squares.
     *
     */
    private void graph(String stationName, int longestName, String date, String value, float scalar) {
        StringTokenizer dateParts = new StringTokenizer(date, " ");
        String day = dateParts.nextToken();
        String time = dateParts.nextToken();
        int nSquares = 0;
        if (value.equals("null")) {
            value = "0.0";
        } else {
            nSquares = Math.round(Float.parseFloat(value) / scalar * 10);
        }
        System.out.print(time + " " + day + " (" + stationName + ")");
        for (int i = 0; longestName > stationName.length() + i; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < nSquares; i++) {
            System.out.print('\u25A0');
        }
        System.out.print(" " + value + '\n');
    }
}
