package pers.can.manage.web.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.can.manage.common.annotation.LogHandleAnnotation;
import pers.can.manage.common.enums.ResultEnum;
import pers.can.manage.util.RedisUtil;
import pers.can.manage.util.ResultUtil;
import pers.can.manage.web.common.input.PayReq;

import javax.validation.Valid;

/**
 * 检查控制器
 *
 * @author Waldron Ye
 * @date 2019/11/9 10:38
 */
@RestController
@Slf4j
public class CheckController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/check")
    public Object check() {
        log.info("check==");
        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }

    @GetMapping("/checkRedis")
    public Object checkRedis() {
        log.info("checkRedis==");
        try {
            String key = "yzw";
            String value = "123456";
            redisUtil.setString(key, value);
            String value2 = redisUtil.getString(key);
            log.info("从redis取出:value2={}", value2);
        } catch (Exception e) {
            log.error("连接redis异常:msg=", e);
            return ResultUtil.getResult(ResultEnum.ERROR_SYS);
        }
        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }

    @PostMapping("/checkPost")
    public Object checkPost() {
        log.info("checkPost==");
        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }

    @PostMapping("/validCheck")
    @LogHandleAnnotation
    public Object validCheck(@RequestBody @Valid PayReq payReq) {
        log.info("validCheck");

        return ResultUtil.getSuccessResult(payReq);
    }
}
