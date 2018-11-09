package com.hiramexpress.controller;

import com.alibaba.druid.util.StringUtils;
import com.hiramexpress.domain.Result;
import com.hiramexpress.domain.enums.ResultEnum;
import com.hiramexpress.service.CheckExpress;
import com.hiramexpress.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExpressController {

    private final CheckExpress checkExpress;

    @Autowired
    public ExpressController(CheckExpress checkExpress) {
        this.checkExpress = checkExpress;
    }

    @PostMapping("/check")
    public Result<?> checkExpressWithCode(String shipperCode, String logisticCode, boolean useAnalysis) throws Exception {
        if (StringUtils.isEmpty(shipperCode) || StringUtils.isEmpty(logisticCode)) {
            return ResultUtil.error(ResultEnum.ERROR);
        }
        return checkExpress.checkExpress(shipperCode, logisticCode, useAnalysis);
    }

    @GetMapping("/count")
    public Result<?> getTodayCount() {
        return checkExpress.getTodayCount();
    }

    @GetMapping("/list")
    public Result<?> getExpressList() {
        return checkExpress.getExpressList();
    }

    @GetMapping("/analysis")
    public Result<?> analysisExpress(@RequestParam("logisticCode") String logisticCode) {
        return checkExpress.analysisExpress(logisticCode);
    }
}
