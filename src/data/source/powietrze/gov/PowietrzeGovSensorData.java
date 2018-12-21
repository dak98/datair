package data.source.powietrze.gov;

import data.source.SensorData;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents air data from a sensor from powietrze.gios.gov.pl API.
 */
public class PowietrzeGovSensorData extends SensorData {
    private int sensorID;
    private String key;
    private List<Values> valuesList;
    private class PowietrzeGovValues extends SensorData.Values {
        private String date;
        private String value;
        /**
         * Default constructor
         * @param date
         * @param value
         */
        private PowietrzeGovValues(String date, String value) {
            this.date = date;
            this.value = value;
        }
        @Override
        public String getDate() {
            return this.date;
        }
        @Override
        public String getValue() {
            return this.value;
        }
        @Override
        public int compareTo(Values value) {
            if (this.getValue().equals("null")) { return -1; }
            if (value.getValue().equals("null")) { return 1; }
            if (this.value.equals(value)) {
                return 0;
            } else if (Float.parseFloat(this.getValue()) > Float.parseFloat(value.getValue())) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    /**
     * Default constructor.
     * @param sensorID
     * @param key
     */
    PowietrzeGovSensorData(int sensorID, String key) {
        this.sensorID = sensorID;
        this.key = key;
        this.valuesList = new LinkedList<>();
    }
    /**
     * Adds one data-value pair to {@link data.source.powietrze.gov.PowietrzeGovSensorData}.
     * @param date
     *         Date of the measurement. Format: yyyy-mm-dd hh:mm:ss
     * @param value
     *         Value of the measurement. Uses '.' as the decimal point.
     */
    void addValue(String date, String value) {
        this.valuesList.add(new PowietrzeGovValues(date, value));
    }
    @Override
    public String getKeyName() {
        return this.key;
    }
    @Override
    public List<Values> getListOfValues() {
        return this.valuesList;
    }

}
