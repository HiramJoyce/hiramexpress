package com.hiramexpress.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hiramexpress.domain.Result;
import com.hiramexpress.domain.enums.PlatformEnum;
import com.hiramexpress.domain.enums.ResultEnum;
import com.hiramexpress.service.impl.KDNIAOService;
import com.hiramexpress.service.impl.KDPTService;
import com.hiramexpress.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CheckExpress {

    private final Logger logger = LoggerFactory.getLogger(CheckExpress.class);

    private final RedisService redisService;      // 伙伴数据资源
    private final KDNIAOService kdniaoService;  // 快递鸟
    private final KDPTService kdptService;      //
    private final ConvertExpress convertExpress;      // 伙伴数据资源

    @Autowired
    public CheckExpress(RedisService redisService, KDNIAOService kdniaoService, KDPTService kdptService, ConvertExpress convertExpress) {
        this.redisService = redisService;
        this.kdniaoService = kdniaoService;
        this.kdptService = kdptService;
        this.convertExpress = convertExpress;
    }

    public Result checkExpress(String shipperCode, String logisticCode) throws Exception {
        logger.info("--->>> ShipperCode: " + shipperCode + " & LogisticCode: " + logisticCode);
        JSONObject result;
        String checkDate = new SimpleDateFormat("yyyyMMdd").format(new Date()); // eg: 20181107
        String redisKey = "checkNum_" + checkDate;
        int checkNum = Integer.parseInt(StringUtils.isEmpty(redisService.get("redisKey")) ? "0" : redisService.get("redisKey"));
        logger.info("--->>> redis data: key:" + redisKey + " value:" + checkNum);
        String finalShipperCode = convertExpress.convert(shipperCode, PlatformEnum.KDNIAO.name());
        result = kdniaoService.checkExpress(finalShipperCode, logisticCode);
        if (result.getBoolean("success")) {
            return ResultUtil.success(result);
        } else {
            finalShipperCode = convertExpress.convert(shipperCode, PlatformEnum.KDPT.name());
            result = kdptService.checkExpress(finalShipperCode, logisticCode);
            if (!result.getBoolean("success")) {
                result = null;
            }
        }
        redisService.set(redisKey, checkNum + 1 + "", 60 * 60 * 24L);
        if (result == null) {
            return ResultUtil.error(ResultEnum.NO_DATA);
        }
        return ResultUtil.success(result);
    }
}
