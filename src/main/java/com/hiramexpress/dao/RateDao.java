package com.hiramexpress.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface RateDao {
    int insertRate(@Param("message") String message, @Param("email") String email, @Param("stars") int stars, @Param("realIp") String realIp, @Param("date") Date date);
}