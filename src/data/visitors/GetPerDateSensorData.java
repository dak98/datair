package data.visitors;

import data.AirDataCollector;
import data.source.IOnlineDataReader;
import data.Sensor;
import data.SensorData;
import data.Station;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetPerDateSensorData extends Visitor {
    /**
     * @param args
     *        String[3] - name of station, parameter name and date.
     * @return {@link SensorData.Measurement} object with specified data.
     *         null if specified {@link SensorData.Measurement} doesn't exist.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(args[2]);
        } catch (ParseException e) {
            // Could not parse given date.
            return null;
        }
        Sensor[] sensorsOfStation = airDataCollector.getSensorsOfStation(((Station) airDataCollector.accept(new GetStationByName(), args, dataSource)).getStationID(), dataSource);
        for (Sensor sensor : sensorsOfStation) {
            if (sensor.getSensorParameterCode().equals(args[1])) {
                SensorData.Measurement[] measurements = ((SensorData) airDataCollector.accept(new GetSensorDataByParamCode(), args, dataSource)).getMeasurements();
                for (SensorData.Measurement value : measurements) {
                    if (value.getDate().equals(date)) {
                        return value.getValue();
                    }
                }
            }
        }
        return null;
    }
}
