package data.storage;

import data.collector.AirDataCollector;
import data.source.Sensor;
import data.source.Source;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SensorsStorage implements Serializable {
    private Map<Integer, List<Sensor>> sensorsOfStation1;
    /**
     * Default constructor.
     */
    public SensorsStorage(Source dataSource) {
        AirDataCollector airDataCollector = new AirDataCollector();
        this.sensorsOfStation1 = airDataCollector.getSensorsOfStationMap();
    }
    public SensorsStorage() {}
    public Map<Integer, List<Sensor>> getSensorsOfStationMap() {
        return this.sensorsOfStation1;
    }
}
