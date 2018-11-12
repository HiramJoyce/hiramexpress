package com.hiramexpress.service;

import com.alibaba.fastjson.JSONObject;
import com.hiramexpress.domain.Result;
import com.hiramexpress.domain.enums.ResultEnum;
import com.hiramexpress.service.analysisimpl.KuaiDi100;
import com.hiramexpress.service.analysisimpl.TrackingMore;
import com.hiramexpress.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hiram 2018年11月09日 23:39
 */

@Service
public class AnalysisExpress {

    private final Logger logger = LoggerFactory.getLogger(AnalysisExpress.class);


    private final KuaiDi100 kuaiDi100;
    private final TrackingMore trackingMore;

    @Autowired
    public AnalysisExpress(KuaiDi100 kuaiDi100, TrackingMore trackingMore) {
        this.kuaiDi100 = kuaiDi100;
        this.trackingMore = trackingMore;
    }

    public Result<?> analysis(String logisticCode) {
        logger.info("--->>> analysis: logisticCode ->" + logisticCode);
        Map<String, IAnalysisService> map = new LinkedHashMap<>();
        map.put("KuaiDi100", kuaiDi100);
        map.put("TrackingMore", trackingMore);
        JSONObject result = null;
        for (String platform : map.keySet()) {
            result = map.get(platform).analysisExpress(logisticCode);
            if (result.getBoolean("success")) {
                break;
            } else {
                result = null;
            }
        }

        if (result == null) {
            return ResultUtil.error(ResultEnum.NO_DATA);
        }
        return ResultUtil.success(result);
    }
}
