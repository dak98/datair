package user.command;

import data.collector.AirDataCollector;
import data.collector.visitors.GetAllValuesOfParamAtDate;
import data.collector.visitors.GetAllValuesOfParamBetweenDates;
import data.source.SensorData;
import data.source.powietrze.gov.PowietrzeGov;

import java.util.*;

/**
 * Returns parameter with lowest value on given date.
 */
public class LowestValue extends Command {
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
     * @return -7 - No measurements recorded at specified date.
     */
    @Override
    public int outputData(String[] args) {
        String[] paramCodes = { "ST", "SO2", "NO2", "CO", "PM10", "PM25", "O3", "C6H6" };
        Map<String, Float> lowestPerParam = new HashMap<>();
        AirDataCollector airDataCollector = new AirDataCollector();
        for (String paramCode : paramCodes) {
            String[] tmpArgs = { paramCode, args[0] };
            List<SensorData.Values> valuesOfSensor = (List<SensorData.Values>) airDataCollector.accept(new GetAllValuesOfParamAtDate(), tmpArgs, new PowietrzeGov());
            if (valuesOfSensor.isEmpty()) {
                lowestPerParam.put(paramCode, Float.MAX_VALUE);
            } else {
                lowestPerParam.put(paramCode, getLowest(valuesOfSensor));
            }
        }
        Float lowest = Float.MAX_VALUE;
        String lowestParamCode = null;
        for (String paramCode : paramCodes) {
            System.out.println(paramCode + ": " + lowestPerParam.get(paramCode));
            if (lowestPerParam.get(paramCode) < lowest) {
                lowest = lowestPerParam.get(paramCode);
                lowestParamCode = paramCode;
            }
        }
        if (lowest == Float.MAX_VALUE) {
            return -7;
        }
        System.out.println("Lowest: " + lowestParamCode + " - " + lowest);
        return 0;
    }
    /**
     * @param valuesOfParam
     *         List of {@link data.source.SensorData.Values} for particular parameter.
     * @return Lowest measurement value.
     */
    private Float getLowest(List<SensorData.Values> valuesOfParam) {
        Float lowest = Float.MAX_VALUE;
        Iterator<SensorData.Values> valuesIterator = valuesOfParam.listIterator();
        SensorData.Values value = null;
        while (valuesIterator.hasNext()) {
            value = valuesIterator.next();
            if (Float.parseFloat(value.getValue()) < lowest) {
                lowest = Float.parseFloat(value.getValue());
            }
        }
        if (Float.parseFloat(value.getValue()) < lowest) {
            lowest = Float.parseFloat(value.getValue());
        }
        return lowest;
    }
}
