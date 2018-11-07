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
        JSONObject result = null;
        String redisKey = "checkNum_" + new SimpleDateFormat("yyyyMMdd").format(new Date());    // eg: checkNum_20181107
        int checkNum = Integer.parseInt(StringUtils.isEmpty(redisService.get(redisKey)) ? "0" : redisService.get(redisKey));
        logger.info("--->>> redis data: key:" + redisKey + " value:" + checkNum);

        Map<String, IExpressService> servicesMap = new LinkedHashMap<>();
        servicesMap.put(PlatformEnum.KDNIAO.name(), kdniaoService);
        servicesMap.put(PlatformEnum.KDPT.name(), kdptService);

        String finalShipperCode;
        Iterator<String> keysIterator = servicesMap.keySet().iterator();
        while (keysIterator.hasNext()) {
            String platform = keysIterator.next();
            finalShipperCode = convertExpress.convert(shipperCode, platform);
            result = servicesMap.get(platform).checkExpress(finalShipperCode, logisticCode);
            if (result.getBoolean("success") != null && result.getBoolean("success")) {
                break;
            } else {
                logger.info("--->>> " + platform + " deal the check but got a false. Continue try other platform...");
                result = null;
                continue;
            }
        }
        redisService.set(redisKey, checkNum + 1 + "", 60 * 60 * 24L);
        if (result == null) {
            return ResultUtil.error(ResultEnum.NO_DATA);
        }
        return ResultUtil.success(result);
    }
}
