package com.hiramexpress.service.analysisimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiramexpress.service.HttpClient;
import com.hiramexpress.service.IAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class KuaiDi100 implements IAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(KuaiDi100.class);

    private final HttpClient client;

    @Autowired
    public KuaiDi100(HttpClient client) {
        this.client = client;
    }

    @Override
    public JSONObject analysisExpress(String logisticCode) {
        logger.info("--->>> KuaiDi100 analysis:");
        String url = "https://www.kuaidi100.com/autonumber/autoComNum?resultv2=1&text=" + logisticCode;
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String resultStr = client.client(url, method, params);
        JSONObject result = JSONObject.parseObject(resultStr);
        JSONArray resultArr = result.getJSONArray("auto");
        JSONObject resultJ = new JSONObject();
        analysisResult(resultArr, resultJ, "KuaiDi100");
        return resultJ;
    }

    static void analysisResult(JSONArray resultArr, JSONObject resultJ, String platform) {
        logger.info("--->>> platform: " + platform + " analysis result: " + resultArr.toJSONString());
        if (resultArr.size() <= 0) {
            resultJ.put("success", false);
            resultJ.put("msg", "");
        } else {
            resultJ.put("success", true);
            resultJ.put("list", resultArr);
            resultJ.put("platform", platform);
        }
    }
}
