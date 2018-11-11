package com.hiramexpress.utils;

import com.alibaba.druid.util.StringUtils;
import com.hiramexpress.domain.Record;
import com.hiramexpress.service.RecordService;
import com.hiramexpress.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class ScheduledTask {

    private final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    private final RecordService recordService;
    private final RedisService redisService;

    @Autowired
    public ScheduledTask(RecordService recordService, RedisService redisService) {
        this.recordService = recordService;
        this.redisService = redisService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void logTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date beforeDay = calendar.getTime();    // 获取前一天日期

        String redisKey = "checkNum_" + new SimpleDateFormat("yyyyMMdd").format(beforeDay);    // eg: checkNum_20181107
        int checkNum = Integer.parseInt(StringUtils.isEmpty(redisService.get(redisKey)) ? "0" : redisService.get(redisKey));
        logger.info("===>>> 昨日服务： " + checkNum + " 次。");
        Record record = recordService.getRecordByDate(beforeDay);
        if (record == null) {
            record = new Record();
            record.setRecordDate(beforeDay);
            record.setRecordTimes(checkNum);
            int result = recordService.addRecord(record);
            if (result > 0) {
                redisService.set("historyCount", recordService.allTimes() + "", 24 * 60 * 60L);
            }
        } else {    // 更新旧数据
            record.setRecordTimes(checkNum);
            recordService.updateRecord(record);
        }
    }
}
