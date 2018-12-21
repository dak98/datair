package data.collector.visitors;

import data.collector.AirDataCollector;
import data.collector.Visitor;
import data.source.Source;
import data.source.Station;

import java.util.Iterator;

public class GetStation extends Visitor {
    /**
     * @param args
     *         Name of station.
     * @param dataSource
     * @return Specified station object.
     *         null if specified station's name was not recognized.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, Source dataSource) {
        Iterator<Station> stationsListIterator = airDataCollector.getStationsList(dataSource).listIterator();
        while (stationsListIterator.hasNext()) {
            Station station = stationsListIterator.next();
            if (station.getStationName().equals(args[0])) {
                return station;
            }
        }
        return null;
    }
}
