package data.collector.visitors;

import data.collector.AirDataCollector;
import data.collector.Visitor;
import data.source.SensorData;
import data.source.Source;
import data.source.Station;
import java.util.LinkedList;
import java.util.List;

public class GetAllSensorDataByParamCode extends Visitor {
    /**
     * @param args
     *         String[1] - code of the parameter.
     * @return List of all {@link data.source.SensorData} of given parameter.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, Source dataSource) {
        List<SensorData> sensorDataList = new LinkedList<>();
        List<Station> stationsList = airDataCollector.getStationsList(dataSource);
        for (Station station : stationsList) {
            String[] tmpArgs = { station.getStationName(), args[0] };
            SensorData sensorData = (SensorData) airDataCollector.accept(new GetSensorDataByParamCode(), tmpArgs, dataSource);
            if (sensorData != null) {
                sensorDataList.add(sensorData);
            }
        }
        return sensorDataList;
    }
}
