package com.hiramexpress.Aspect;
import com.alibaba.druid.util.StringUtils;
import com.hiramexpress.domain.enums.ResultEnum;
import com.hiramexpress.service.RedisService;
import com.hiramexpress.utils.ClientIPUtils;
import com.hiramexpress.utils.ResultUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Autowired
    RedisService redisService;

    @Pointcut("execution(public * com.hiramexpress.controller.*.*(..))")
    public void log() {
    }

    @Pointcut("execution(public * com.hiramexpress.controller.ExpressController.analysisExpress(..))")
    public void analysis() {
    }

//    @Before("log()")
//    public void doBefore(JoinPoint joinPoint) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>");
//        //url
//        logger.info("  url={}", request.getRequestURL());
//        //method
//        logger.info("  methon={}", request.getMethod());
//        //ip
//        logger.info("  ip={}", request.getRemoteAddr());
//        //类方法
//        logger.info("  class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        //参数
//        logger.info("  args={}", joinPoint.getArgs());
//    }
//
//    @After("log()")
//    public void doAfter() {
//        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<");
//    }

//    @AfterReturning(returning = "object", pointcut = "log()")
//    public void doAfterReturning(Object object) {
//        logger.info("  response={}", object.toString());
//        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<");
//    }

    @Around("analysis()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String realIp = ClientIPUtils.getClientIp(request);
        logger.info("--->>> realIp: " + realIp);
        String redisKey = "record_" + realIp.replaceAll("\\.", "_");
        logger.info("--->>> redisKey: " + redisKey);
        if (!StringUtils.isEmpty(redisService.get(redisKey))) {
            return ResultUtil.error(ResultEnum.OPERATION_TOO_FREQUENTLY);
        }
        redisService.set(redisKey, realIp, 5L);
        return pjp.proceed();
    }
}