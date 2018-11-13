package com.hiramexpress.controller;

import com.alibaba.druid.util.StringUtils;
import com.hiramexpress.domain.Result;
import com.hiramexpress.domain.enums.ResultEnum;
import com.hiramexpress.service.AnalysisExpress;
import com.hiramexpress.service.CheckExpress;
import com.hiramexpress.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExpressController {

    private final CheckExpress checkExpress;
    private final AnalysisExpress analysisExpress;

    @Autowired
    public ExpressController(CheckExpress checkExpress, AnalysisExpress analysisExpress) {
        this.checkExpress = checkExpress;
        this.analysisExpress = analysisExpress;
    }

    @PostMapping("/check")
    public Result<?> checkExpressWithCode(String shipperCode, String logisticCode, boolean useAnalysis, String analysisPlatform) throws Exception {
        if (StringUtils.isEmpty(shipperCode) || StringUtils.isEmpty(logisticCode)) {
            return ResultUtil.error(ResultEnum.ERROR);
        }
        return checkExpress.checkExpress(shipperCode, logisticCode, useAnalysis, analysisPlatform);
    }

    @GetMapping("/count")
    public Result<?> getCount() {
        return checkExpress.getCount();
    }

    @GetMapping("/list")
    public Result<?> getExpressList() {
        return checkExpress.getExpressList();
    }

    @GetMapping("/analysis")
    public Result<?> analysisExpress(@RequestParam("logisticCode") String logisticCode) {
        return analysisExpress.analysis(logisticCode);
    }

    @PostMapping("/rate")
    public Result<?> rate(String message, String email, int stars) {
        return checkExpress.rate(message, email, stars);
    }

    @GetMapping("/statistics")
    public Result<?> statistics() {
        return checkExpress.statistics();
    }

}
