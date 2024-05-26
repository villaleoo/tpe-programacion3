package tpe.structures;

import java.util.Date;

public class Processor {
    String idProc;
    String codProc;
    Boolean isCooled;
    Integer funcYear;

    public Processor(String idProc, String codProc, Boolean isCooled, Integer funcYear) {
        this.idProc = idProc;
        this.codProc = codProc;
        this.isCooled = isCooled;
        this.funcYear = funcYear;
    }


    public String getIdProc() {
        return idProc;
    }

    public void setIdProc(String idProc) {
        this.idProc = idProc;
    }

    public String getCode() {
        return codProc;
    }

    public void setCode(String codProc) {
        this.codProc = codProc;
    }

    public Boolean isCooled() {
        return isCooled;
    }

    public void setCooled(Boolean cooled) {
        isCooled = cooled;
    }

    public Integer getFuncYear() {
        return funcYear;
    }

    public void setFuncYear(Integer funcYear) {
        this.funcYear = funcYear;
    }
}
