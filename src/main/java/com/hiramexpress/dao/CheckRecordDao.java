package com.hiramexpress.dao;

import com.hiramexpress.domain.CheckRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckRecordDao {
    int insertCheckRecords(CheckRecord checkRecord);
    List<CheckRecord> selectACheckRecords();
}