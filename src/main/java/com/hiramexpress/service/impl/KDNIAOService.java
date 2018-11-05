package com.hiramexpress.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KDNIAOService {

    private final KdniaoTrackQueryAPI api;

    @Autowired
    public KDNIAOService(KdniaoTrackQueryAPI api) {
        this.api = api;
    }

    /**
     * 调用快递鸟API
     * @param shipperCode 快递公司代号
     * @param logisticCode 运单编号
     * @return 结果
     * @throws Exception 抛出api类中查询可能引发的异常
     */
    public JSONObject checkExpress(String shipperCode, String logisticCode) throws Exception {
        JSONObject result = JSONObject.parseObject(api.getOrderTracesByJson(shipperCode, logisticCode));
        return result;
    }
}
