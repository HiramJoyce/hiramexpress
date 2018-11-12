package com.hiramexpress.service.analysisimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiramexpress.service.IAnalysisService;
import com.hiramexpress.service.checkimpl.KdniaoTrackQueryAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TrackingMore implements IAnalysisService {

    private final Logger logger = LoggerFactory.getLogger(TrackingMore.class);

    private final KdniaoTrackQueryAPI api;

    @Autowired
    public TrackingMore(KdniaoTrackQueryAPI api) {
        this.api = api;
    }

    @Override
    public JSONObject analysisExpress(String logisticCode) {
        logger.info("--->>> TrackingMore analysis:");
        String url = "https://www.trackingmore.com/index_ajax.php";
        Map<String, String> params = new LinkedHashMap<>();
        params.put("tracknumber", logisticCode);
        params.put("lang", "cn");
        JSONArray resultArr = JSONObject.parseArray(api.sendPost(url, params));
        JSONObject result = new JSONObject();
        KuaiDi100.analysisResult(resultArr, result, "TrackingMore");
        return result;
    }
}
