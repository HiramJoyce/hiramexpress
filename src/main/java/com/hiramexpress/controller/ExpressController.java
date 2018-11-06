package com.hiramexpress.controller;

import com.hiramexpress.domain.Result;
import com.hiramexpress.service.CheckExpress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExpressController {

    private final CheckExpress checkExpress;

    @Autowired
    public ExpressController(CheckExpress checkExpress) {
        this.checkExpress = checkExpress;
    }

    @PostMapping("/check")
    public Result<?> CheckExpressWithCode(String shipperCode, String logisticCode) throws Exception {
        return checkExpress.checkExpress(shipperCode, logisticCode);
    }
}
