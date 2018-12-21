package data.collector.visitors;

import data.collector.AirDataCollector;
import data.collector.Visitor;
import data.source.Sensor;
import data.source.SensorData;
import data.source.Source;
import data.source.Station;

import java.util.List;

public class GetSensorDataByParamCode extends Visitor {
    /**
     * @param args
     *         String[2] - station name and parameters code.
     * @return {@link data.source.SensorData} of specified station name and parameter code.
     *         null if station name was not recognized or doesn't contain parameter of specified code.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, Source dataSource) {
        List<Sensor> sensorsList = airDataCollector.getSensorsOfStation(((Station) airDataCollector.accept(new GetStation(), args, dataSource)).getStationID(), dataSource);
        for (Sensor sensor : sensorsList) {
            if (sensor.getSensorParamCode().equals(args[1])) {
                List<SensorData> sensorData = airDataCollector.getSensorData(sensor.getSensorID(), dataSource);
                return sensorData.get(sensorData.size() - 1);
            }
        }
        return null;
    }
}
