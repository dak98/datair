package data.visitors;

import data.AirDataCollector;
import data.source.IOnlineDataReader;
import data.SensorData;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GetAllValuesOfParamAfterDate extends Visitor {
    /**
     * @param args
     *        String[2] - Code of parameter, lower bound date.
     * @return List of {@link SensorData.Measurement} of given parameter after given dates.
     *         null if specified date could not be parsed.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException {
        List<SensorData.Measurement> finalValuesList = new LinkedList<>();
        String[] paramCode = { args[0] };
        List<SensorData> sensorDataList = (List<SensorData>) airDataCollector.accept(new GetAllSensorDataByParamCode(), paramCode, dataSource);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lowerBoundDate;
        try {
            lowerBoundDate = sdf.parse(args[1]);
        } catch (ParseException e) {
            // Could not parse given date.
            return null;
        }
        for (SensorData sensorData : sensorDataList) {
            SensorData.Measurement[] measurements = sensorData.getMeasurements();
            for (SensorData.Measurement measurement : measurements) {
                if (measurement != null && measurement.getDate().after(lowerBoundDate)) {
                    finalValuesList.add(measurement);
                }
            }
        }
        return finalValuesList;
    }
}
