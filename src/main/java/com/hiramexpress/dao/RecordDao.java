package com.hiramexpress.dao;

import com.hiramexpress.domain.Record;

import java.util.Date;

public interface RecordDao {
    Record selectRecordByDate(Date date);
    int insertRecords(Record record);
}