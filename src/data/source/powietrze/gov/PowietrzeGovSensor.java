package data.source.powietrze.gov;

import data.source.Sensor;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a measuring sensor from powietrze.gios.gov.pl API.
 */
public class PowietrzeGovSensor extends Sensor implements Externalizable {
    private int id;
    private String stationID;
    private Param param;
    private class Param implements Externalizable {
        private String paramName;
        private String paramFormula;
        private String paramCode;
        private int idParam;
        private Param(String paramName, String paramFormula, String paramCode, String idParam) {
            this.paramName = paramName;
            this.paramFormula = paramFormula;
            this.paramCode = paramCode;
            this.idParam = Integer.parseInt(idParam);
        }
        public Param() {}
        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeInt(idParam);
            objectOutput.writeUTF(paramName);
            objectOutput.writeUTF(paramFormula);
            objectOutput.writeUTF(paramCode);
        }
        @Override
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            this.paramName = objectInput.readUTF();
            this.paramFormula = objectInput.readUTF();
            this.paramCode = objectInput.readUTF();
            this.idParam = objectInput.readInt();
        }
    }
    /**
     * Default constructor.
     * @param sensorID
     * @param stationID
     * @param paramName
     * @param paramFormula
     * @param paramCode
     * @param idParam
     */
    PowietrzeGovSensor(String sensorID, String stationID, String paramName, String paramFormula, String paramCode, String idParam) {
        this.id = Integer.parseInt(sensorID);
        this.stationID = stationID;
        this.param = new Param(paramName, paramFormula, paramCode, idParam);
    }
    @Override
    public int getSensorID() {
        return this.id;
    }
    @Override
    public String getSensorParamCode() {
        return this.param.paramCode;
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
    public PowietrzeGovSensor() {}
    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeInt(id);
        objectOutput.writeUTF(stationID);
        objectOutput.writeObject(param);
    }
    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        this.id = objectInput.readInt();
        this.stationID = objectInput.readUTF();
        this.param = (Param) objectInput.readObject();
    }
}
