package data.visitors;

import data.AirDataCollector;
import data.source.IOnlineDataReader;
import data.SensorData;
import data.Station;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GetAllSensorDataByParamCode extends Visitor {
    /**
     * @param args
     *        String[1] - code of the parameter.
     * @return List of all {@link SensorData} of given parameter.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException {
        List<SensorData> sensorDataList = new LinkedList<>();
        Station[] stations = airDataCollector.getStations(dataSource);
        for (Station station : stations) {
            String[] tmpArgs = { station.getStationName(), args[0] };
            SensorData sensorData = (SensorData) airDataCollector.accept(new GetSensorDataByParamCode(), tmpArgs, dataSource);
            if (sensorData != null) {
                sensorDataList.add(sensorData);
            }
        }
        return sensorDataList;
    }
}
