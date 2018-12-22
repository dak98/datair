package data;

/**
 * A single measuring station.
 */
public class Station {
    private int id;
    private String stationName;
    private Float gegrLat;
    private Float gegrLon;
    private City city;
    private class City {
        private Integer id;
        private String name;
        private Commune commune;
        private class Commune {
            private String communeName;
            private String districtName;
            private String provinceName;
        }
    }
    private String addressStreet;

    public int getStationID() {
        return id;
    }
    public String getStationName() {
        return stationName;
    }
    @Override
    public String toString() {
        return  "id            = "   + id                        +
                "\nstationName   = " + stationName               +
                "\ngegrLat       = " + gegrLat                   +
                "\ngegrLon       = " + gegrLon                   +
                "\ncityID        = " + city.id                   +
                "\ncityName      = " + city.name                 +
                "\ncommuneName   = " + city.commune.communeName  +
                "\ndistrictName  = " + city.commune.districtName +
                "\nprovinceName  = " + city.commune.provinceName +
                "\naddressStreet = " + addressStreet             +
                "\n";
    }
}
