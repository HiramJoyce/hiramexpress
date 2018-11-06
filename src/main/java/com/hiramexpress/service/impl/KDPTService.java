package com.hiramexpress.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hiramexpress.service.HttpClient;
import com.hiramexpress.service.IExpressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 伙伴数据资源 http://www.kdpt.net/
 */
@Service
public class KDPTService implements IExpressService {

    private final Logger logger = LoggerFactory.getLogger(KDPTService.class);

    private final HttpClient httpClient;

    @Autowired
    public KDPTService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public JSONObject checkExpress(String shipperCode, String logisticCode) {
        logger.info("--->>> call 伙伴数据资源 checkExpress(" + shipperCode + ", " + logisticCode + ")");
        String url = "http://q.kdpt.net/api?id=XDB2g2sjbns211ow966aNo0I_1050588656&com=" + shipperCode + "&nu=" + logisticCode + "&show=json";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        return JSONObject.parseObject(httpClient.client(url, method, params));
    }
}
