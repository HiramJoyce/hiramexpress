package com.hiramexpress.service;

import com.alibaba.druid.util.StringUtils;
import com.hiramexpress.dao.RateDao;
import com.hiramexpress.dao.RecordDao;
import com.hiramexpress.domain.Record;
import com.hiramexpress.utils.ClientIPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RateService {

    private final Logger logger = LoggerFactory.getLogger(RateService.class);

    @Resource
    private RateDao rateDao;

    public int saveRate(String message, String email, int stars) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String realIp = ClientIPUtils.getClientIp(request);
        return rateDao.insertRate(message, email, stars, realIp, new Date());
    }
}
