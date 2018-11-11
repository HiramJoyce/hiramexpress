package com.hiramexpress.service;

import com.alibaba.druid.util.StringUtils;
import com.hiramexpress.dao.RecordDao;
import com.hiramexpress.domain.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RecordService {

    private final Logger logger = LoggerFactory.getLogger(RecordService.class);

    private final RedisService redisService;

    @Resource
    private RecordDao recordDao;

    @Autowired
    public RecordService(RedisService redisService) {
        this.redisService = redisService;
    }

    public Record getRecordByDate(Date date) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return recordDao.selectRecordByDate(dateStr);
    }

    public int addRecord(Record record) {
        return recordDao.insertRecords(record);
    }

    public int allTimes() {
        String redisKey = "historyCount";
        int checkNum = Integer.parseInt(StringUtils.isEmpty(redisService.get(redisKey)) ? "0" : redisService.get(redisKey));
        if (checkNum > 0) {
            return checkNum;
        }
        return recordDao.selectAllTimes();
    }

    public int updateRecord(Record record) {
        return recordDao.updateRecord(record);
    }
}
