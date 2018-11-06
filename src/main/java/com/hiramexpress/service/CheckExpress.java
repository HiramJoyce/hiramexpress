package com.hiramexpress.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hiramexpress.domain.Result;
import com.hiramexpress.service.impl.KDNIAOService;
import com.hiramexpress.service.impl.KDPTService;
import com.hiramexpress.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        String checkNum = StringUtils.isEmpty(redisService.get("checkNum")) ? "0" : redisService.get("checkNum");
        int checkNumInt = (Integer.parseInt(checkNum));
        Map<String, IExpressService> platforms = new LinkedHashMap<>();
        List<String> platformsList = new ArrayList<>();
        platformsList.add("KDNIAO");
        platformsList.add("KDPT");
        platforms.put("KDNIAO", kdniaoService);
        platforms.put("KDPT", kdptService);
        String platform = convertExpress.convert(shipperCode, platformsList.get(checkNumInt % platforms.size()));
        logger.info("--->>> platform: " + platform);
        for (String exp : platformsList) {
            if (exp.equals(platformsList.get(checkNumInt % platforms.size()))) {
                result = platforms.get(exp).checkExpress(platform, logisticCode);
                break;
            }
        }
        redisService.set("checkNum", checkNumInt + 1 + "", 60 * 10L);
        return ResultUtil.success(result);
    }
}
