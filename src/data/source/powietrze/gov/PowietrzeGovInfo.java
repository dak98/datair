package data.source.powietrze.gov;

import data.source.SourceInfo;

/**
 * Returns URLs for getting specified air data from powietrze.gios.gov.pl API.
 */
public class PowietrzeGovInfo extends SourceInfo {
    private String siteURL = "http://api.gios.gov.pl/pjp-api/rest";

    @Override
    public String getStationsListURL() {
        return siteURL + "/station/findAll";
    }

    @Override
    public String getSensorsListURL(int stationID) {
        return siteURL + "/station/sensors/" + stationID;
    }

    @Override
    public String getSensorDataURL(int sensorID) {
        return siteURL + "/data/getData/" + sensorID;
    }

    @Override
    public String getAirIndexURL(int stationID) {
        return siteURL + "/aqindex/getIndex/" + stationID;
    }
}
