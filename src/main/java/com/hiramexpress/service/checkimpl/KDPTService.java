package com.hiramexpress.service.checkimpl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiramexpress.domain.enums.PlatformEnum;
import com.hiramexpress.domain.enums.ResultEnum;
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
        JSONObject result = new JSONObject();
        String url = "http://q.kdpt.net/api?id=XDB2g2sjbns211ow966aNo0I_1050588656&com=" + shipperCode + "&nu=" + logisticCode + "&show=json";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        JSONObject checkResult = JSONObject.parseObject(httpClient.client(url, method, params));
        String checkStatus = checkResult.getString("status");
        JSONArray checkTraces = checkResult.getJSONArray("data"); // 获得物流信息
        if (StringUtils.equals(checkStatus, "0") || checkTraces.size() <= 0) {    // 查询失败 或 没有信息
            logger.info("--->>> check failure.");
            result.put("success", false);
            result.put("reason", ResultEnum.NO_DATA.getMsg());
            return result;
        } else {    // 查询成功
            logger.info("--->>> check success.");
            JSONArray newCheckTraces = new JSONArray(); // 获得物流信息
            JSONObject eachStation;
            JSONObject newEachStation;
            for (Object checkTrace : checkTraces) {
                eachStation = (JSONObject) checkTrace;
                newEachStation = new JSONObject();
                newEachStation.put("AcceptStation", eachStation.getString("context"));
                newEachStation.put("AcceptTime", eachStation.getString("time"));
                newEachStation.put("Remark", null);
                newCheckTraces.add(newEachStation);
            }
            result.put("success", true);
            result.put("platform", PlatformEnum.KDPT);
            String[] states = {"在途，即货物处于运输过程中", "揽件，货物已由快递公司揽收并且产生了第一条跟踪信息",
                    "疑难，货物寄送过程出了问题", "签收，收件人已签收",
                    "退签或异常签收，即货物由于用户拒签、超区等原因退回，而且发件人已经签收",
                    "派件，即快递正在进行同城派件", "退回，货物正处于退回发件人的途中"};
            result.put("state", states[checkResult.getIntValue("state")]);
            result.put("logisticCode", checkResult.getString("nu"));
            result.put("traces", newCheckTraces);
            return result;
        }
    }
}
