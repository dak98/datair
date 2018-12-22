package data;

/**
 * A single sensor of a measuring station.
 */
public class Sensor {
    private int id;
    private int stationID;
    private Param param;
    private class Param {
        private String paramName;
        private String paramFormula;
        private String paramCode;
        private int idParam;
    }

    public int getSensorID() {
        return id;
    }
    /**
     * @return Code of the parameter measured by the station.
     */
    public String getSensorParameterCode() {
        return param.paramCode;
    }
    @Override
    public String toString() {
        return "id           = " + this.id +
                "\nstationID    = " + this.stationID +
                "\nparamName    = " + this.param.paramName +
                "\nparamFormula = " + this.param.paramFormula +
                "\nparamCode    = " + this.param.paramCode +
                "\nidParam      = " + this.param.idParam + "\n";
    }
}
