package com.hiramexpress.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.hiramexpress.domain.enums.PlatformEnum;
import com.hiramexpress.service.IExpressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KDNIAOService implements IExpressService {

    private final Logger logger = LoggerFactory.getLogger(KDNIAOService.class);
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
        logger.info("--->>> call 快递鸟 checkExpress(" + shipperCode + ", " + logisticCode + ")");
        JSONObject result = new JSONObject();
        JSONObject checkResult = JSONObject.parseObject(api.getOrderTracesByJson(shipperCode, logisticCode));
        boolean checkSussess = checkResult.getBoolean("Success");
        JSONArray checkTraces = checkResult.getJSONArray("Traces"); // 获得物流信息
        if (!checkSussess || checkTraces.size() <= 0) {    // 查询失败 或 没有信息
            logger.info("--->>> check failure for " + checkResult.getString("Reason"));
//            if (checkResult.getString("Reason") != null && checkResult.getString("Reason").contains("没有可用套餐")) {
//                // TODO 当日查询次数到达3000
//            } else {
//                // TODO 没有物流轨迹的
//            }
            result.put("success", false);
            result.put("reason", checkResult.getString("Reason"));
            return result;
        } else {    // 查询成功
            logger.info("--->>> check success.");
            JSONArray newCheckTraces = new JSONArray(); // 获得物流信息
            JSONObject eachStation;
            JSONObject newEachStation;
            for (Object checkTrace : checkTraces) {
                eachStation = (JSONObject) checkTrace;
                newEachStation = new JSONObject();
                newEachStation.put("AcceptStation", eachStation.getString("AcceptStation"));
                newEachStation.put("AcceptTime", eachStation.getString("AcceptTime"));
                newEachStation.put("Remark", eachStation.get("Remark"));
                newCheckTraces.add(newEachStation);
            }
            result.put("success", true);
            result.put("platform", PlatformEnum.KDNIAO);
            String[] states = {"", "", "在途中", "签收", "问题件"};
            result.put("state", states[checkResult.getIntValue("State")]);
            result.put("logisticCode", checkResult.getString("LogisticCode"));
            result.put("traces", newCheckTraces);
            return result;
        }
    }
}
