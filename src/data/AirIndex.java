package data;

import java.lang.reflect.Field;

/**
 * Single air index of a measuring station.
 */
public class AirIndex {
    private int id;
    public class Param {
        // Does not have to be a in one of the possible date formats.
        private String calcDate;
        private IndexLevel indexLevel;
        private String sourceDataDate;
        public class IndexLevel {
            private int id;
            private String indexLevelName;
        }

        /**
         * Default constructor.
         * @param calcDate
         * @param indexLevel
         * @param sourceDataDate
         */
        private Param(String calcDate, IndexLevel indexLevel, String sourceDataDate) {
            this.calcDate = calcDate;
            this.indexLevel = indexLevel;
            this.sourceDataDate = sourceDataDate;
        }
        @Override
        public String toString() {
            StringBuilder param = new StringBuilder().append("calcDate       = ")
                    .append(calcDate)
                    .append("\nindexLevel     = ");
            if (indexLevel != null) {
                param.append("\nindexLevelID   = ")
                        .append(indexLevel.id)
                        .append("\nindexLevelName = ")
                        .append(indexLevel.indexLevelName);
            } else {
                param.append(indexLevel);
            }
            param.append("\nSourceDataDate = ")
                    .append(sourceDataDate);

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
    public final String[] paramsCodes = { "st", "so2", "no2", "co", "pm10", "pm25", "o3", "c6h6" };

    /**
     * @param stationID
     *        ID of the station of the air index.
     */
    public void setStationID(int stationID) {
        id = stationID;
    }
    /**
     * @param paramCode
     *        One of codes of the parameters from {@link AirIndex#paramsCodes}.
     * @param calcDate
     *        Date of the measurement of the parameter. Can have different formats. Can be null.
     * @param indexLevel
     *        Can be null.
     * @param sourceDataDate
     *        Can be null.
     */
    public void setParam(String paramCode, String calcDate, Param.IndexLevel indexLevel, String sourceDataDate) {
        Field field = null;
        try {
            field = AirIndex.class.getDeclaredField(paramCode);
            field.setAccessible(true);
            field.set(this, new Param(calcDate, indexLevel, sourceDataDate));
        } catch (NoSuchFieldException e) {

        } catch(IllegalAccessException e) {

        }
    }
    @Override
    public String toString() {
        return "id = "      + id   +
                "\nst:\n"   + st   +
                "\nso2:\n"  + so2  +
                "\nno2:\n"  + no2  +
                "\nco:\n"   + co   +
                "\npm10:\n" + pm10 +
                "\npm25:\n" + pm25 +
                "\no3:\n"   + o3   +
                "\nc6h6:\n" + c6h6 +
                '\n';
    }

}
