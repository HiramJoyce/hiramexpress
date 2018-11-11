package com.hiramexpress.domain;

import java.util.Date;

public class Record {
    private Date recordDate;
    private int recordTimes;

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
}
