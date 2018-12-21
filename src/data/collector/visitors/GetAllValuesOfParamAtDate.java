package data.collector.visitors;

import data.collector.AirDataCollector;
import data.collector.Visitor;
import data.source.Source;

public class GetAllValuesOfParamAtDate extends Visitor {
    /**
     * @param args
     *         String[2] - Code of parameter, date.
     * @return List of {@link data.source.SensorData.Values} of given parameter at given date.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, Source dataSource) {
        String[] newArgs = { args[0], args[1], args[1] };
        return airDataCollector.accept(new GetAllValuesOfParamBetweenDates(), newArgs, dataSource);
    }
}
