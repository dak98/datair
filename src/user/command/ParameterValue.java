package user.command;

import data.AirDataCollector;
import data.visitors.GetListOfStationsParamCodes;
import data.visitors.GetPerDateSensorData;
import data.visitors.GetStationByName;
import data.source.powietrze.gov.PowietrzeGov;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Returns value of parameter for given station name
 * and date.
 */
public class ParameterValue extends Command {
    /**
     * @return String[3] with arguments.
     */
    @Override
    public String[] parse(String args) {
        StringTokenizer arguments = new StringTokenizer(args, " ");
        if (arguments.countTokens() >= 4) {
            String[] argsArray = new String[3];
            StringBuilder stationName = new StringBuilder();
            for (int i = 0; arguments.countTokens() > 3; i++) {
                stationName.append(arguments.nextToken() + " ");
            }
            argsArray[0] = stationName.toString().trim();
            argsArray[1] = arguments.nextToken();
            argsArray[2] = arguments.nextToken() + " " + arguments.nextToken();
            return argsArray;
        }
        return null;
    }
    /**
     * @return -1 - Unknown station name.
     *         -2 - Specified parameter is not checked by station.
     *         -3 - No measurement was made on specified date.
     */
    @Override
    public int outputData(String[] args) throws IOException {
        AirDataCollector airDataCollector = new AirDataCollector();
        if (!isStation(args)) {
            return -1;
        }
        if (!isParamOfStation(args)) {
            return -2;
        }
        String value = (String) airDataCollector.accept(new GetPerDateSensorData(), args, new PowietrzeGov());
        if (value == null) {
            return -3;
        }
        System.out.println(value);
        return 0;
    }
    /**
     * @param stationName
     * @return True if station with given name exists.
     *         False otherwise.
     */
    private boolean isStation(String[] stationName) throws IOException{
        AirDataCollector airDataCollector = new AirDataCollector();
        if (airDataCollector.accept(new GetStationByName(), stationName, new PowietrzeGov()) == null) {
            return false;
        }
        return true;
    }
    /**
     * @param args
     * @return True if given parameter is checked by station.
     *         False otherwise.
     */
    private boolean isParamOfStation(String[] args) throws IOException {
        AirDataCollector airDataCollector = new AirDataCollector();
        List<String> paramCodes = (List<String>) airDataCollector.accept(new GetListOfStationsParamCodes(), args, new PowietrzeGov());
        for (String paramCode : paramCodes) {
            if (paramCode.equals(args[1])) {
                return true;
            }
        }
        return false;
    }
}
