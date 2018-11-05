package com.hiramexpress.service;

import com.alibaba.fastjson.JSONObject;
import com.hiramexpress.service.impl.KDNIAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckExpress {

    private final Logger logger = LoggerFactory.getLogger(CheckExpress.class);

    private final KDNIAOService kdniaoService;

    @Autowired
    public CheckExpress(KDNIAOService kdniaoService) {
        this.kdniaoService = kdniaoService;
    }

    public JSONObject checkExpress(String shipperCode, String logisticCode) throws Exception {
        logger.info("--->>> ShipperCode: " + shipperCode + " & LogisticCode: " + logisticCode);
        JSONObject result = kdniaoService.checkExpress(shipperCode, logisticCode);
        return result;
    }
}
