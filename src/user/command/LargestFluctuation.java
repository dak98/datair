package user.command;

import data.AirDataCollector;
import data.visitors.GetAllValuesOfParamAfterDate;
import data.SensorData;
import data.source.powietrze.gov.PowietrzeGov;

import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

/**
 * Returns parameter with largest fluctuation since given date.
 */
public class LargestFluctuation extends Command {
    /**
     * @return String[1] - date.
     */
    @Override
    public String[] parse(String args) {
        StringTokenizer arguments = new StringTokenizer(args, " ");
        if (arguments.countTokens() == 2) {
            String[] argsArr = {arguments.nextToken() + " " + arguments.nextToken()};
            return argsArr;
        }
        return null;
    }
    /**
     * @return -6 - No measurements recorded after specified date.
     */
    @Override
    public int outputData(String[] args) throws IOException {
        String[] paramCodes = { "ST", "SO2", "NO2", "CO", "PM10", "PM25", "O3", "C6H6" };
        Map<String, Float> fluctuationPerParam = new HashMap<>();
        AirDataCollector airDataCollector = new AirDataCollector();
        for (String paramCode : paramCodes) {
            String[] tmpArgs = { paramCode, args[0] };
            List<SensorData.Measurement> measurements = (List<SensorData.Measurement>) airDataCollector.accept(new GetAllValuesOfParamAfterDate(), tmpArgs, new PowietrzeGov());
            if (measurements.size() >= 2) {
                fluctuationPerParam.put(paramCode, getFluctuation(measurements));
            } else {
                fluctuationPerParam.put(paramCode, 0.0f);
            }
        }
        String maxFluctuationParam = null;
        float maxFluctuation = 0.0f;
        for (String paramCode : paramCodes) {
            System.out.println(paramCode + ": " + fluctuationPerParam.get(paramCode));
            if (fluctuationPerParam.get(paramCode) > maxFluctuation) {
                maxFluctuation = fluctuationPerParam.get(paramCode);
                maxFluctuationParam = paramCode;
            }
        }
        if (maxFluctuation == 0.0) {
            return -6;
        }
        System.out.println("Largest fluctuation: " + maxFluctuationParam + " - " + maxFluctuation);
        return 0;
    }
    /**
     * @param measurements
     *         List of {@link SensorData.Measurement} for particular parameter.
     *         It must containt at least 2 elements.
     * @return Fluctuation of measurements values.
     */
    private Float getFluctuation(List<SensorData.Measurement> measurements) {
        float fluctuation = 0.0f;
        SensorData.Measurement value1 = measurements.get(0), value2 = measurements.get(1);
        for (int i = 2; i < measurements.size(); i++) {
            fluctuation += abs(value1.getValue() - value2.getValue());
            value1 = value2;
            value2 = measurements.get(i);
        }
        return fluctuation;
    }
}
