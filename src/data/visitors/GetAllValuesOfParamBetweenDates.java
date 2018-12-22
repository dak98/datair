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

public class GetAllValuesOfParamBetweenDates extends Visitor {
    /**
     * @param args
     *        String[3] - Code of parameter, lower bound date, upper bound date.
     * @return List of {@link SensorData.Measurement} of given parameter between given dates.
     *         null if specified dates where could not be parsed.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException {
        List<SensorData.Measurement> finalValuesList = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lowerBoundDate;
        Date upperBoundDate;
        try {
            lowerBoundDate = sdf.parse(args[1]);
            upperBoundDate = sdf.parse(args[2]);
        } catch (ParseException e) {
            // Could not parse given date.
            return null;
        }
        String[] paramCode = { args[0] };
        List<SensorData> sensorDataList = (List<SensorData>) airDataCollector.accept(new GetAllSensorDataByParamCode(), paramCode, dataSource);
        for (SensorData sensorData : sensorDataList) {
            SensorData.Measurement[] measurements = sensorData.getMeasurements();
            if (measurements != null) {
                for (SensorData.Measurement measurement : measurements) {
                    if (measurement != null && !measurement.getDate().before(lowerBoundDate) && !measurement.getDate().after(upperBoundDate)) {
                        finalValuesList.add(measurement);
                    }
                }
            }
        }
        return finalValuesList;
    }
}
