package pers.can.manage.web.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.can.manage.common.constant.CommonConstant;

import java.util.Objects;

/**
 * 视图控制器
 *
 * @author Waldron Ye
 * @date 2019/11/9 19:28
 */
@Controller
@RequestMapping("/view")
@Slf4j
public class ViewController {

    @RequestMapping("/{module}/{url}")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url) {

        String urlNew = module + "/" + url;
        log.info("待跳转页面:urlNew={}", urlNew);

        //登录页面放过,其它页面则判断用户是否登录，如果没登录则跳转登录页面
        if (!Objects.equals(CommonConstant.USER_LOGIN_URL, urlNew)) {
            //判断是否登录，如未登录则跳转登录页面
            log.info("用户未登录");

        }

        return urlNew;
    }
}
