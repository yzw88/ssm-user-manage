package pers.can.manage.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.can.manage.common.constant.CommonConstant;
import pers.can.manage.common.constant.RedisConstant;
import pers.can.manage.common.dto.UserTokenDTO;
import pers.can.manage.common.enums.ResultEnum;
import pers.can.manage.common.exception.BusinessException;
import pers.can.manage.model.SysUser;
import pers.can.manage.service.SysUserService;
import pers.can.manage.util.FastJsonUtil;
import pers.can.manage.util.JwtUtil;
import pers.can.manage.util.RedisUtil;
import pers.can.manage.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author Waldron Ye
 * @date 2019/6/2 13:29
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SysUserService sysUserService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURL().toString();
        log.info("url:{}进入拦截器", requestUrl);
        this.checkToken(request, response);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //当前请求进行处理之后，也就是Controller 方法调用之后执行.对应的视图之前

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //当前请求进行处理之后，该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。对应的视图之后.
    }


    /**
     * token校验
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws BusinessException 业务异常
     */
    private void checkToken(HttpServletRequest request, HttpServletResponse response) throws BusinessException {

        //获取请求头的token
        String token = request.getHeader(CommonConstant.KEY_USER_TOKEN);
        log.info("请求头部token:{}", token);

        if (StringUtil.isBlank(token)) {
            BusinessException.err(ResultEnum.USER_STATUS_ERROR, "请求头token为空");
        }

        String tokenJson = JwtUtil.resolveToken(token);
        log.info("解析token为:{}", tokenJson);
        if (StringUtil.isBlank(tokenJson)) {
            BusinessException.err(ResultEnum.USER_STATUS_ERROR, "解析token异常");
        }

        UserTokenDTO userTokenDTO = FastJsonUtil.toBean(tokenJson, UserTokenDTO.class);

        Long tokenRenewalSecond = userTokenDTO.getExpireDate().getTime() - System.currentTimeMillis();
        log.info("token剩下的毫秒数:tokenRenewalSecond={}", tokenRenewalSecond);

        if (tokenRenewalSecond <= 0) {
            log.error("token已过期");
            BusinessException.err(ResultEnum.USER_STATUS_ERROR, "用户token已过期");
        }

        //token有效时间小于5分钟要更换token
        if (tokenRenewalSecond < 5 * 60 * 1000) {
            String key = RedisConstant.KEY_USER_LOGIN + userTokenDTO.getUserId();
            long userIncr = this.redisUtil.incr(key);
            //表示第一条线程
            if (userIncr == 1) {
                log.info("用户id:userId{}的token进行续命", userTokenDTO.getUserId());
                //设置key为60秒过期
                this.redisUtil.setExpire(key, 60);

                SysUser sysUser = this.sysUserService.getUserByPrimaryKey(userTokenDTO.getUserId());
                String tokenNew = this.sysUserService.getTokenByUser(sysUser);
                log.info("用户id:{},新的token:{}", sysUser.getUserId(), tokenNew);

                //响应头部配置，前端才可以从响应头部获取token
                response.setHeader("Access-Control-Expose-Headers", CommonConstant.KEY_USER_TOKEN);
                response.setHeader(CommonConstant.KEY_USER_TOKEN, tokenNew);
            }
        }

    }
}
