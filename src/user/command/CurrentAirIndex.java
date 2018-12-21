package user.command;

import data.collector.AirDataCollector;
import data.collector.visitors.GetStation;
import data.source.AirIndex;
import data.source.Station;
import data.source.powietrze.gov.PowietrzeGov;

import java.util.List;

/**
 * Informs about current air quality index
 * for given station.
 */
public class CurrentAirIndex extends Command {
    /**
     * @return String[1] - station name.
     */
    @Override
    public String[] parse(String args) {
        if (args == null || args.isEmpty()) {
            return null;
        }
        String[] argsArray = { args };
        return argsArray;
    }
    /**
     * @return -1 - Unknown station name.
     */
    @Override
    public int outputData(String[] args) {
        AirDataCollector airDataCollector = new AirDataCollector();
        Station station = (Station) airDataCollector.accept(new GetStation(), args, new PowietrzeGov());
        if (station != null) {
            System.out.println(station.getStationID());
            List<AirIndex> airIndexesOfStation = airDataCollector.getAirIndexOfStation(station.getStationID(), new PowietrzeGov());
            System.out.println(airIndexesOfStation.get(airIndexesOfStation.size() - 1).toString());
            return 0;
        }
        return -1;
    }
}
