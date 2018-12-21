package data.collector.visitors;

import data.collector.AirDataCollector;
import data.collector.Visitor;
import data.source.Sensor;
import data.source.SensorData;
import data.source.Source;
import data.source.Station;

import java.util.List;

public class GetPerDateSensorData extends Visitor {
    /**
     * @param args
     *         String[3] - name of station, parameter name and date.
     * @return Specified SensorData's parameter value.
     *         null if specified parameter value doesn't exist.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, Source dataSource) {
        List<Sensor> sensorsList = airDataCollector.getSensorsOfStation(((Station) airDataCollector.accept(new GetStation(), args, dataSource)).getStationID(), dataSource);
        for (Sensor sensor : sensorsList) {
            if (sensor.getSensorParamCode().equals(args[1])) {
                List<SensorData.Values> valuesList = ((SensorData) airDataCollector.accept(new GetSensorDataByParamCode(), args, dataSource)).getListOfValues();
                for (SensorData.Values value : valuesList) {
                    if (value.getDate().equals(args[2])) {
                        return value.getValue();
                    }
                }
            }
        }
        return null;
    }
}
