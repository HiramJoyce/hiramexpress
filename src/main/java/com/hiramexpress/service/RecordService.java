package com.hiramexpress.service;

import com.hiramexpress.dao.RecordDao;
import com.hiramexpress.domain.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RecordService {

    private final Logger logger = LoggerFactory.getLogger(RecordService.class);

    @Resource
    private RecordDao recordDao;

    public Record getRecordByDate(Date date) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Record record = recordDao.selectRecordByDate(dateStr);
        return record;
    }

    public int addRecord(Record record) {
        return recordDao.insertRecords(record);
    }
}
