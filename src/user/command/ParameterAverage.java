package user.command;

import data.AirDataCollector;
import data.visitors.GetAllValuesOfParamBetweenDates;
import data.SensorData;
import data.source.powietrze.gov.PowietrzeGov;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Returns average value for parameter in given time interval.
 */
public class ParameterAverage extends Command {
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
        AirDataCollector airDataCollector = new AirDataCollector();
        List<SensorData.Measurement> measurements = (List<SensorData.Measurement>) airDataCollector.accept (new GetAllValuesOfParamBetweenDates(), args, new PowietrzeGov());
        if (measurements.isEmpty()) {
            return -5;
        }
        float avg = 0.0f;
        int count = 0;
        for (SensorData.Measurement measurement : measurements) {
            avg += measurement.getValue();
            count++;
        }
        System.out.println(avg / count);
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
}
