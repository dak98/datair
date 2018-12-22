package user.command;

import data.AirDataCollector;
import data.visitors.GetSensorDataByParamCode;
import data.visitors.GetStationsByParamCode;
import data.SensorData;
import data.Station;
import data.source.powietrze.gov.PowietrzeGov;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public int outputData(String[] args) throws IOException {
       if (!isCorrectParamCode(args[0])) {
            return -4;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(args[1]);
            endDate = sdf.parse(args[2]);
        } catch (ParseException e) {
            // Could not parse given date.
            // TODO: Date could not be parsed.
        }
        AirDataCollector airDataCollector = new AirDataCollector();
        String[] tmpArgs1 = { args[0] };
        Station[] stations = (Station[]) airDataCollector.accept(new GetStationsByParamCode(), tmpArgs1, new PowietrzeGov());
        List<SensorData.Measurement[]> measurementsOfStation = new LinkedList<>();
        int longestName = 0;
        for (Station station : stations) {
            if (longestName < station.getStationName().length()) {
                longestName = station.getStationName().length();
            }
            String[] tmpArgs2 = { station.getStationName(), args[0] };
            SensorData sensorData = (SensorData) airDataCollector.accept(new GetSensorDataByParamCode(), tmpArgs2, new PowietrzeGov());
            if (sensorData != null) {
                measurementsOfStation.add(sensorData.getMeasurements());
            }
        }
        List<ListIterator<SensorData.Measurement>> measurementsIterators = new LinkedList<>();
        for (SensorData.Measurement[] measurements : measurementsOfStation) {
            measurementsIterators.add(Arrays.asList(measurements).listIterator(measurements.length));
        }
        boolean stop = false;
        boolean iter = false;
        while (!stop) {
            int i = 0;
            for (ListIterator<SensorData.Measurement> measurementIterator : measurementsIterators) {
                if (measurementIterator.hasPrevious()) {
                    SensorData.Measurement measurement = measurementIterator.previous();
                    if (!measurement.getDate().before(startDate) && !measurement.getDate().after(endDate)) {
                        graph(stations[i].getStationName(), longestName, sdf.format(measurement.getDate()), measurement.getValue(), 50.0f);
                        i++;
                        iter = true;
                    } else if (measurement.getDate().after(endDate)) {
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
    private void graph(String stationName, int longestName, String date, float value, float scalar) {
        int nSquares = nSquares = Math.round(value / scalar * 10);
        StringTokenizer dateParts = new StringTokenizer(date, " ");
        String data = dateParts.nextToken();
        String time = dateParts.nextToken();
        System.out.print(time + " " + data +  " (" + stationName + ")");
        for (int i = 0; longestName > stationName.length() + i; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < nSquares; i++) {
            System.out.print('\u25A0');
        }
        System.out.print(" " + value + '\n');
    }
}
