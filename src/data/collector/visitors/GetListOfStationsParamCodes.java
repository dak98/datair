package data.collector.visitors;

import data.collector.AirDataCollector;
import data.collector.Visitor;
import data.source.Sensor;
import data.source.Source;
import data.source.Station;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GetListOfStationsParamCodes extends Visitor {
    /**
     * @param args
     *         String[1] - name of the station.
     * @return List of station's sensors parameters codes.
     *         null if station's name was not recognized.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, Source dataSource) {
        Station station = (Station) airDataCollector.accept(new GetStation(), args, dataSource);
        if (station != null) {
            Iterator<Sensor> sensorsListIterator = airDataCollector.getSensorsOfStation(station.getStationID(), dataSource).listIterator();
            List<String> sensorsParamNameList = new LinkedList<>();
            sensorsListIterator.forEachRemaining(sensor -> sensorsParamNameList.add(sensor.getSensorParamCode()));
            return sensorsParamNameList;
        }
        return null;
    }
}
