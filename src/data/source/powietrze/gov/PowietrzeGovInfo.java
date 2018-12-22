package data.source.powietrze.gov;

import data.source.IOnlineDataInfo;

public class PowietrzeGovInfo implements IOnlineDataInfo {
    private String siteURL = "http://api.gios.gov.pl/pjp-api/rest";

    @Override
    public String stationsURL() {
        return siteURL + "/station/findAll";
    }
    @Override
    public String sensorsOfStationURL(int stationID) {
        return siteURL + "/station/sensors/" + stationID;
    }
    @Override
    public String dataOfSensorURL(int sensorID) {
        return siteURL + "/data/getData/" + sensorID;
    }
    @Override
    public String airIndexURL(int stationID) {
        return siteURL + "/aqindex/getIndex/" + stationID;
    }
}
