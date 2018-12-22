package data.visitors;

import data.AirDataCollector;
import data.source.IOnlineDataReader;
import data.Station;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GetStationsByParamCode extends Visitor {
    /**
     * @param args
     *        String[1] - code of parameter.
     * @return List of station which monitor given parameter.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException {
        List<Station> stationsOfParam = new LinkedList<>();
        Station[] stations = airDataCollector.getStations(dataSource);
        for (Station station : stations) {
            String[] tmpArgs = { station.getStationName() };
            List<String> paramsOfStation = (List<String>) airDataCollector.accept(new GetListOfStationsParamCodes(), tmpArgs, dataSource);
            if (paramsOfStation.contains(args[0])) {
                stationsOfParam.add(station);
            }
        }
        return stationsOfParam.toArray(new Station[0]);
    }
}
