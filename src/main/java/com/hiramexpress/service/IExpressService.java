package com.hiramexpress.service;

import com.alibaba.fastjson.JSONObject;

public interface IExpressService {
    JSONObject checkExpress(String shipperCode, String logisticCode) throws Exception;
}
