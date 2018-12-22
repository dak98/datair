package data.visitors;

import data.AirDataCollector;
import data.SensorData;
import data.source.IOnlineDataReader;
import data.Sensor;
import data.Station;

import java.io.IOException;

public class GetSensorDataByParamCode extends Visitor {
    /**
     * @param args
     *        String[2] - station name and parameters code.
     * @return {@link SensorData} of specified station name and parameter code.
     *         null if station name was not recognized or doesn't contain parameter of specified code.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException {
        Sensor[] sensorsOfStation = airDataCollector.getSensorsOfStation(((Station) airDataCollector.accept(new GetStationByName(), args, dataSource)).getStationID(), dataSource);
        for (Sensor sensor : sensorsOfStation) {
            if (sensor.getSensorParameterCode().equals(args[1])) {
                return airDataCollector.getSensorData(sensor.getSensorID(), dataSource);
            }
        }
        return null;
    }
}
