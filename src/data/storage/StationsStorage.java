package data.storage;

import data.collector.AirDataCollector;
import data.source.Source;
import data.source.Station;

import java.io.Serializable;
import java.util.List;

public class StationsStorage implements Serializable {
    private List<Station> stationList;
    /**
     * Default constructor.
     * @param dataSource
     *         Internet data source.
     */
    StationsStorage(Source dataSource) {
        AirDataCollector airDataCollector = new AirDataCollector();
        stationList = airDataCollector.getStationsList(dataSource);
    }
    public StationsStorage() {}

    public List<Station> getStationList() {
        return this.stationList;
    }
}
