package data.source.powietrze.gov;

import data.source.AirIndex;

import java.lang.reflect.Field;

/**
 * Represents air index of a measuring station from powietrze.gios.gov.pl API.
 */
public class PowietrzeGovAirIndex extends AirIndex {
    private String id;
    private class Param {
        private String calcDate;
        private IndexLevel indexLevel;
        private String sourceDataDate;
        private class IndexLevel {
            private String id;
            private String indexLevelName;
            private IndexLevel(String indexID, String indexLevelName) {
                this.id = indexID;
                this.indexLevelName = indexLevelName;
            }
        }
        private Param(String calcDate, String indexID, String indexLevelName, String sourceDataDate) {
            this.calcDate = calcDate;
            this.indexLevel = new IndexLevel(indexID, indexLevelName);
            this.sourceDataDate = sourceDataDate;
        }
        @Override
        public String toString() {
            StringBuilder param = new StringBuilder();
            param.append("CalcDate       = " + calcDate);
            if (indexLevel.id == null) {
                param.append("\nIndexLevelID   = null" +
                             "\nIndexLevelName = null");
            } else {
                param.append("\nIndexLevelID   = " + indexLevel.id +
                             "\nIndexLevelName = " + indexLevel.indexLevelName);
            }
            param.append("\nsourceDataDate = " + sourceDataDate + "\n");
            return param.toString();
        }
    }
    private Param st;
    private Param so2;
    private Param no2;
    private Param co;
    private Param pm10;
    private Param pm25;
    private Param o3;
    private Param c6h6;
    final int paramNumber;
    final String[] setters = { "st", "so2", "no2", "co", "pm10", "pm25", "o3", "c6h6" };
    /**
     * Default constructor.
     * @param stationID
     *         ID of a measuring station of current air index.
     */
    PowietrzeGovAirIndex(String stationID, int paramNumber) {
        this.id = stationID;
        this.paramNumber = 8;
    }

    void setter(String paramName, String calcDate, String indexID, String indexLevelName, String sourceDataDate) {
        Field field = null;
        try {
            field = PowietrzeGovAirIndex.class.getDeclaredField(paramName);
            field.setAccessible(true);
            field.set(this, new Param(calcDate, indexID, indexLevelName, sourceDataDate));
        } catch (NoSuchFieldException e) {

        } catch(IllegalAccessException e) {

        }
    }
    @Override
    public String toString() {
        return "id = "     + this.id   +
               "\nst:\n"   + this.st   +
               "\nso2:\n"  + this.so2  +
               "\nno2:\n"  + this.no2  +
               "\nco:\n"   + this.co   +
               "\npm10:\n" + this.pm10 +
               "\npm25:\n" + this.pm25 +
               "\no3:\n"   + this.o3   +
               "\nc6h6:\n" + this.c6h6 + '\n';
    }
}
