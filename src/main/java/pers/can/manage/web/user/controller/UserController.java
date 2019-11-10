package pers.can.manage.web.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.can.manage.common.annotation.ValidateAnnotation;
import pers.can.manage.common.constant.CommonConstant;
import pers.can.manage.common.enums.ResultEnum;
import pers.can.manage.common.enums.UserStatusEnum;
import pers.can.manage.model.SysUser;
import pers.can.manage.service.RedisCacheService;
import pers.can.manage.service.SysUserService;
import pers.can.manage.util.*;
import pers.can.manage.web.user.input.LoginReq;
import pers.can.manage.web.user.input.PageUserQueryReq;
import pers.can.manage.web.user.output.ImgValidCodeResp;
import pers.can.manage.web.user.output.UserLoginResp;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

/**
 * 用户控制器
 *
 * @author Waldron Ye
 * @date 2019/11/9 20:27
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private RedisCacheService redisCacheService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户验证码
     *
     * @return object
     */
    @GetMapping("/getUserValidCode")
    public Object getUserValidCode() {
        int width = 130;
        int height = 40;
        try {
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            String validCode = ValidCodeUtil.drawRandomText(width, height, bufferedImage);
            log.info("生成的验证码为:randomText={}", validCode);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            String base64Code = "data:image/png;base64," + Base64Util.encode(outputStream.toByteArray());

            String key = Md5Util.md5To16Encrypt(validCode);
            //添加缓存
            redisCacheService.addImgCode(key, validCode);

            ImgValidCodeResp imgValidCodeResp = new ImgValidCodeResp();
            imgValidCodeResp.setBase64Code(base64Code);
            imgValidCodeResp.setCodeKey(key);

            return ResultUtil.getSuccessResult(imgValidCodeResp);
        } catch (Exception e) {
            log.error("生成验证码异常", e);
            return ResultUtil.getResult(ResultEnum.ERROR_SYS);
        }

    }

    /**
     * 用户登录
     *
     * @param loginReq 用户登录请求对象
     * @return object
     */
    @PostMapping("/login")
    @ValidateAnnotation
    public Object login(@RequestBody @Valid LoginReq loginReq, HttpServletResponse response) {
        log.info("user login:{}", FastJsonUtil.toJson(loginReq));

        String code = this.redisCacheService.getValidCode(loginReq.getCodeKey());
        log.info("用户名:username={},获取的code:={}", loginReq.getUsername(), code);
        if (StringUtil.isEmpty(code)) {
            return ResultUtil.getResult(ResultEnum.BUSINESS_ERROR.getCode(), "验证码已过期，请重新获取");
        }
        if (!code.equalsIgnoreCase(loginReq.getImgCode())) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "验证码错误");
        }

        SysUser sysUser = this.sysUserService.selectByUserName(loginReq.getUsername());
        if (sysUser == null) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "用户名或密码错误");
        }

        String password = Md5Util.md5To16Encrypt(sysUser.getSalt() + loginReq.getPassword());
        if (Objects.equals(password, sysUser.getPassword())) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "用户名或密码错误");
        }

        if (Objects.equals(UserStatusEnum.DISABLE.getCode(), sysUser.getStatus())) {
            return ResultUtil.getResult(ResultEnum.BUSINESS_ERROR.getCode(), "当前用户已被禁用");
        }

        String token = this.sysUserService.getTokenByUser(sysUser);

        //登录成功，把token放到请求头
        response.setHeader("Access-Control-Expose-Headers", CommonConstant.KEY_USER_TOKEN);
        response.setHeader(CommonConstant.KEY_USER_TOKEN, token);

        UserLoginResp userLoginResp = new UserLoginResp();
        userLoginResp.setUserId(sysUser.getUserId());
        userLoginResp.setUsername(sysUser.getUsername());
        userLoginResp.setEmail(sysUser.getEmail());
        userLoginResp.setMobile(sysUser.getMobile());

        return ResultUtil.getSuccessResult(userLoginResp);
    }


    /**
     * 获取用户分页数据
     *
     * @param pageUserQueryReq 用户分页查询对象
     * @return object
     */
    @PostMapping("/getUserPageList")
    @ValidateAnnotation
    public Object getUserPageList(@RequestBody @Valid PageUserQueryReq pageUserQueryReq) {

        List<SysUser> sysUserList = this.sysUserService.listPageUser(pageUserQueryReq);

        return ResultUtil.getPageRespResult(sysUserList);
    }
}
