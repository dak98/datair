package data.visitors;

import data.AirDataCollector;
import data.SensorData;
import data.source.IOnlineDataReader;

import java.io.IOException;

public class GetAllValuesOfParamAtDate extends Visitor {
    /**
     * @param args
     *        String[2] - Code of parameter, date.
     * @return List of {@link SensorData.Measurement} of given parameter at given date.
     *         null if specified date could not be parsed.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException {
        String[] newArgs = { args[0], args[1], args[1] };
        return airDataCollector.accept(new GetAllValuesOfParamBetweenDates(), newArgs, dataSource);
    }
}
