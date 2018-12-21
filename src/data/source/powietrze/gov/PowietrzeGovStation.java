package data.source.powietrze.gov;

import data.source.Station;
import data.storage.Serialization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Represents a measuring station from powietrze.gios.gov.pl API.
 */
public class PowietrzeGovStation extends Station implements Externalizable {
    private int id;
    private String stationName;
    private String gegrLat;
    private String gegrLon;
    transient private City city;
    private class City extends Serialization {
        private int id;
        private String name;
        private Commune commune;
        private City(String id, String name, String communeName, String districtName, String provinceName) {
            this.id = Integer.parseInt(id);
            this.name = name;
            this.commune = new Commune(communeName, districtName, provinceName);
        }
        private class Commune {
            private String communeName;
            private String districtName;
            private String provinceName;
            private Commune(String communeName, String districtName, String provinceName) {
                this.communeName = communeName;
                this.districtName = districtName;
                this.provinceName = provinceName;
            }
        }
    }
    private String addressStreet;
    /**
     * Default constructor.
     * @param stationID
     * @param stationName
     * @param gegrLat
     * @param gegrLon
     * @param cityID
     * @param cityName
     * @param communeName
     * @param districtName
     * @param provinceName
     * @param addressStreet
     */
    PowietrzeGovStation(String stationID, String stationName, String gegrLat, String gegrLon, String cityID, String cityName, String communeName, String districtName, String provinceName, String addressStreet) {
        this.id = Integer.parseInt(stationID);
        this.stationName = stationName;
        this.gegrLat = gegrLat;
        this.gegrLon = gegrLon;
        this.city = new City(cityID, cityName, communeName, districtName, provinceName);
        this.addressStreet = addressStreet;
    }
    public PowietrzeGovStation() {

    }
    @Override
    public String getStationName() {
        return this.stationName;
    }
    @Override
    public int getStationID() {
        return this.id;
    }
    @Override
    public String toString() {
        StringBuilder powietrzeGovStationString = new StringBuilder();
        powietrzeGovStationString.append("id            = " + this.id +
                                         "\nstationName   = " + this.stationName +
                                         "\ngegrLat       = " + this.gegrLat +
                                         "\ngegrLon       = " + this.gegrLon +
                                         "\ncityID        = " + this.city.id +
                                         "\ncityName      = " + this.city.name +
                                         "\ncommuneName   = " + this.city.commune.communeName +
                                         "\ndistrictName  = " + this.city.commune.districtName +
                                         "\nprovinceName  = " + this.city.commune.provinceName +
                                         "\naddressStreet = " + this.addressStreet + "\n");
        return powietrzeGovStationString.toString();
    }
    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeInt(this.id);
        objectOutput.writeUTF(stationName);
        objectOutput.writeUTF(gegrLat);
        objectOutput.writeUTF(gegrLon);
        objectOutput.writeUTF(addressStreet);
    }
    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        this.id = objectInput.readInt();
        this.stationName = objectInput.readUTF();
        this.gegrLat = objectInput.readUTF();
        this.gegrLon = objectInput.readUTF();
        this.addressStreet = objectInput.readUTF();
    }

}
