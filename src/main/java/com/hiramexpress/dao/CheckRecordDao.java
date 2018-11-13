package com.hiramexpress.dao;

import com.hiramexpress.domain.CheckRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckRecordDao {
    int insertCheckRecords(CheckRecord checkRecord);
}