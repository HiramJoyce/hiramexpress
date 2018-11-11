package com.hiramexpress.service;

import com.hiramexpress.dao.RecordDao;
import com.hiramexpress.domain.Record;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class RecordService {

    @Resource
    private RecordDao recordDao;

    public Record getRecordByDate(Date date) {
        return recordDao.selectRecordByDate(date);
    }

    public int addRecord(Record record) {
        return recordDao.insertRecords(record);
    }
}
