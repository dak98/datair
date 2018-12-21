package data.collector.visitors;

import data.collector.AirDataCollector;
import data.collector.Visitor;
import data.source.Source;
import data.source.Station;

import java.util.LinkedList;
import java.util.List;

public class GetStationsByParamCode extends Visitor {
    /**
     * @param args
     *         String[1] - code of parameter.
     * @return List of station which monitor given parameter.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, Source dataSource) {
        List<Station> stationsList = airDataCollector.getStationsList(dataSource);
        List<Station> stationsOfParam = new LinkedList<>();
        for (Station station : stationsList) {
            String[] tmpArgs = { station.getStationName() };
            List<String> paramsOfStation = (List<String>) airDataCollector.accept(new GetListOfStationsParamCodes(), tmpArgs, dataSource);
            if (paramsOfStation.contains(args[0])) {
                stationsOfParam.add(station);
            }
        }
        return stationsOfParam;
    }
}
