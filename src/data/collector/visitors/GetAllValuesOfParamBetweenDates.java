package data.collector.visitors;

import data.collector.AirDataCollector;
import data.collector.Visitor;
import data.source.SensorData;
import data.source.Source;
import utility.DatesComparator;

import java.util.LinkedList;
import java.util.List;

public class GetAllValuesOfParamBetweenDates extends Visitor {
    /**
     * @param args
     *         String[3] - Code of parameter, lower bound date, upper bound date.
     * @return List of {@link data.source.SensorData.Values} of given parameter between given dates.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, Source dataSource) {
        DatesComparator datesComparator = new DatesComparator();
        String[] paramCode = { args[0] };
        List<SensorData> sensorDataList = (List<SensorData>) airDataCollector.accept(new GetAllSensorDataByParamCode(), paramCode, dataSource);
        List<SensorData.Values> finalValuesList = new LinkedList<>();
        for (SensorData sensorData : sensorDataList) {
            List<SensorData.Values> valuesList = sensorData.getListOfValues();
            for (SensorData.Values value : valuesList) {
                if (!value.getValue().equals("null") && datesComparator.isBetweenDates(value.getDate(), args[1], args[2])) {
                    finalValuesList.add(value);
                }
            }
        }
        return finalValuesList;
    }
}
