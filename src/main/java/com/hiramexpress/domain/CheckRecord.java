package com.hiramexpress.domain;

import java.util.Date;

public class CheckRecord {
    private int id;
    private String checkShipperCode;
    private String checkLogisticCode;
    private int checkResult;
    private String checkReason;
    private String checkRealIp;
    private Date checkDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCheckShipperCode() {
        return checkShipperCode;
    }

    public void setCheckShipperCode(String checkShipperCode) {
        this.checkShipperCode = checkShipperCode;
    }

    public String getCheckLogisticCode() {
        return checkLogisticCode;
    }

    public void setCheckLogisticCode(String checkLogisticCode) {
        this.checkLogisticCode = checkLogisticCode;
    }

    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String reason) {
        this.checkReason = reason;
    }

    public String getCheckRealIp() {
        return checkRealIp;
    }

    public void setCheckRealIp(String checkRealIp) {
        this.checkRealIp = checkRealIp;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    @Override
    public String toString() {
        return "CheckRecord{" +
                "id=" + id +
                ", checkShipperCode='" + checkShipperCode + '\'' +
                ", checkLogisticCode='" + checkLogisticCode + '\'' +
                ", checkResult=" + checkResult +
                ", checkReason='" + checkReason + '\'' +
                ", checkRealIp='" + checkRealIp + '\'' +
                ", checkDate=" + checkDate +
                '}';
    }
}
