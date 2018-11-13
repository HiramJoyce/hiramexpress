package com.hiramexpress.dao;

import com.hiramexpress.domain.Record;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordDao {
    Record selectRecordByDate(String data);
    int insertRecords(Record record);
    int selectAllTimes();
    int updateRecord(Record record);
    List<Record> selectRecords();
}