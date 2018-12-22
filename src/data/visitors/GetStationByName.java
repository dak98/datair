package data.visitors;

import data.AirDataCollector;
import data.source.IOnlineDataReader;
import data.Station;

import java.io.IOException;

public class GetStationByName extends Visitor {
    /**
     * @param args
     *        Name of station.
     * @param dataSource
     * @return {@link Station} object specified by its name.
     *         null if specified station's name was not recognized.
     */
    @Override
    public Object visit(AirDataCollector airDataCollector, String[] args, IOnlineDataReader dataSource) throws IOException {
        Station[] stations = airDataCollector.getStations(dataSource);
        for (Station station : stations) {
            if (station.getStationName().equals(args[0])) {
                return station;
            }
        }
        return null;
    }
}
