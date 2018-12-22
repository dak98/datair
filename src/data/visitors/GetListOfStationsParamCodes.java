package data.visitors;

import data.AirDataCollector;
import data.source.IOnlineDataReader;
import data.Sensor;
import data.Station;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GetListOfStationsParamCodes extends Visitor {
    /**
     * @param args
     *        String[1] - name of the station.
     * @return List of station's sensors parameters codes.
     *         null if station's name was not recognized.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException {
        Station station = (Station) airDataCollector.accept(new GetStationByName(), args, dataSource);
        if (station != null) {
            Sensor[] sensorsOfStation = airDataCollector.getSensorsOfStation(station.getStationID(), dataSource);
            List<String> sensorsParamNameList = new LinkedList<>();
            for (Sensor sensor : sensorsOfStation) {
                sensorsParamNameList.add(sensor.getSensorParameterCode());
            }
            return sensorsParamNameList;
        }
        return null;
    }
}
