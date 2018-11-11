package com.hiramexpress.domain;

import java.util.Date;

public class Record {
    private int id;
    private Date recordDate;
    private int recordTimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getRecordTimes() {
        return recordTimes;
    }

    public void setRecordTimes(int recordTimes) {
        this.recordTimes = recordTimes;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", recordDate=" + recordDate +
                ", recordTimes=" + recordTimes +
                '}';
    }
}
