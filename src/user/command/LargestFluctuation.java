package user.command;

import data.collector.AirDataCollector;
import data.collector.visitors.GetAllValuesOfParamAfterDate;
import data.source.SensorData;
import data.source.powietrze.gov.PowietrzeGov;

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
    public int outputData(String[] args) {
        String[] paramCodes = { "ST", "SO2", "NO2", "CO", "PM10", "PM25", "O3", "C6H6" };
        Map<String, Float> fluctuationPerParam = new HashMap<>();
        AirDataCollector airDataCollector = new AirDataCollector();
        for (String paramCode : paramCodes) {
            String[] tmpArgs = { paramCode, args[0] };
            List<SensorData.Values> valuesOfSensor = (List<SensorData.Values>) airDataCollector.accept(new GetAllValuesOfParamAfterDate(), tmpArgs, new PowietrzeGov());
            if (valuesOfSensor.size() >= 2) {
                fluctuationPerParam.put(paramCode, getFluctuation(valuesOfSensor));
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
     * @param valuesOfParam
     *         List of {@link data.source.SensorData.Values} for particular parameter.
     *         It must containt at least 2 elements.
     * @return Fluctuation of measurements values.
     */
    private Float getFluctuation(List<SensorData.Values> valuesOfParam) {
        Float fluctuation = 0.0f;
        Iterator<SensorData.Values> valuesIterator = valuesOfParam.listIterator();
        SensorData.Values value1 = valuesIterator.next(), value2 = valuesIterator.next();
        while (valuesIterator.hasNext()) {
            fluctuation += abs(Float.parseFloat(value2.getValue()) - Float.parseFloat(value1.getValue()));
            value1 = value2;
            value2 = valuesIterator.next();
        }
        return (fluctuation += abs(Float.parseFloat(value2.getValue()) - Float.parseFloat(value1.getValue())));
    }
}
