package com.hiramexpress.service;

import com.hiramexpress.dao.CheckRecordDao;
import com.hiramexpress.domain.CheckRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CheckRecordService {

    @Resource
    CheckRecordDao checkRecordDao;

    public int addCheckRecord(CheckRecord checkRecord) {
        return checkRecordDao.insertCheckRecords(checkRecord);
    }
}
