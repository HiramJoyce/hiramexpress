package com.hiramexpress.utils;


import com.hiramexpress.domain.Result;
import com.hiramexpress.domain.enums.ResultEnum;

/**
 * @author caohailiang
 * 返回结果工具类
 */
public class ResultUtil {

    public static Result<?> success(Object object) {
        Result<Object> result = new Result<Object>();
        result.setCode(1);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result<?> success() {
        return success(null);
    }

    public static Result<?> error(ResultEnum resultEnum) {
        Result<?> result = new Result<Object>();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return result;
    }

    public static Result<?> error(Integer code, String msg) {
        Result<?> result = new Result<Object>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
