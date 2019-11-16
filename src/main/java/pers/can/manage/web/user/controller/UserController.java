package pers.can.manage.web.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.can.manage.common.annotation.LogHandleAnnotation;
import pers.can.manage.common.constant.CommonConstant;
import pers.can.manage.common.enums.ResultEnum;
import pers.can.manage.common.enums.UserStatusEnum;
import pers.can.manage.model.SysUser;
import pers.can.manage.service.RedisCacheService;
import pers.can.manage.service.SysUserService;
import pers.can.manage.util.*;
import pers.can.manage.web.user.input.LoginReq;
import pers.can.manage.web.user.input.PageUserQueryReq;
import pers.can.manage.web.user.input.UserEditReq;
import pers.can.manage.web.user.output.ImgValidCodeResp;
import pers.can.manage.web.user.output.UserInfoResp;
import pers.can.manage.web.user.output.UserLoginResp;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
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
    @LogHandleAnnotation
    public Object login(@RequestBody @Valid LoginReq loginReq, HttpServletResponse response, HttpServletRequest request) {
        log.info("user login:{}", FastJsonUtil.toJson(loginReq));

        String code = this.redisCacheService.getValidCode(loginReq.getCodeKey());
        log.info("用户名:username={},获取的code:={}", loginReq.getUsername(), code);
        if (StringUtils.isBlank(code)) {
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

        //保存到session，由于是练习demo，暂不考虑session共享
        request.getSession().setAttribute(CommonConstant.SESSION_USER, sysUser);

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
    @LogHandleAnnotation
    public Object getUserPageList(@RequestBody @Valid PageUserQueryReq pageUserQueryReq) {

        log.info("请求数据pageUserQueryReq={}", FastJsonUtil.toJson(pageUserQueryReq));
        List<SysUser> sysUserList = this.sysUserService.listPageUser(pageUserQueryReq);

        return ResultUtil.getPageRespResult(sysUserList);
    }


    /**
     * 编辑用户信息
     *
     * @param userEditReq 用户编辑传输对象
     * @return object
     */
    @PostMapping("/editUser")
    @LogHandleAnnotation
    public Object editUser(@RequestBody @Valid UserEditReq userEditReq) {
        log.info("编辑用户:username={}", userEditReq.getUsername());

        // 更新
        if (userEditReq.getUserId() != null) {
            SysUser sysUser = this.sysUserService.getUserByPrimaryKey(userEditReq.getUserId());
            if (sysUser == null) {
                return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "用户id非法");
            }

            if (StringUtils.isNotBlank(userEditReq.getUsername())) {
                SysUser sysUser1 = this.sysUserService.selectByUserName(userEditReq.getUsername());
                //该用户名已其他用户使用
                if (sysUser1 != null && !Objects.equals(sysUser.getUserId(), sysUser1.getUserId())) {
                    return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "该用户名已有其他用户使用");
                }
            }

            sysUser.setUsername(userEditReq.getUsername());
            sysUser.setMobile(userEditReq.getMobile());
            sysUser.setEmail(userEditReq.getEmail());
            sysUser.setStatus(userEditReq.getStatus());
            this.sysUserService.updateUser(sysUser);

            return ResultUtil.getResult(ResultEnum.SUCCESS);
        }
        //插入
        SysUser sysUser2 = this.sysUserService.selectByUserName(userEditReq.getUsername());
        if (sysUser2 != null) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "该用户名已有其他用户使用");
        }
        sysUser2 = new SysUser();
        sysUser2.setCreateTime(new Date());
        sysUser2.setUsername(userEditReq.getUsername());
        sysUser2.setMobile(userEditReq.getMobile());
        sysUser2.setEmail(userEditReq.getEmail());
        sysUser2.setStatus(userEditReq.getStatus());
        sysUser2.setSalt(RandomStringUtils.randomAlphanumeric(8));
        sysUser2.setStatus(UserStatusEnum.ENABLE.getCode());

        String password2 = Md5Util.md5To16Encrypt(sysUser2.getSalt() + CommonConstant.INIT_PASSWORD);
        sysUser2.setPassword(password2);
        this.sysUserService.insertUser(sysUser2);

        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }


    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return object
     */
    @GetMapping("/getUserByUserId")
    @LogHandleAnnotation
    public Object getUserByUserId(Long userId) {

        if (userId == null || userId < 0) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "用户id非法");
        }

        SysUser sysUser = this.sysUserService.getUserByPrimaryKey(userId);
        if (sysUser == null) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "用户不存在");
        }
        UserInfoResp userInfoResp = new UserInfoResp();
        userInfoResp.setUserId(sysUser.getUserId());
        userInfoResp.setDeptId(sysUser.getDeptId());
        userInfoResp.setEmail(sysUser.getEmail());
        userInfoResp.setMobile(sysUser.getMobile());

        userInfoResp.setStatus(sysUser.getStatus());
        userInfoResp.setUsername(sysUser.getUsername());
        return ResultUtil.getSuccessResult(userInfoResp);
    }

    /**
     * 禁用或者启用用户
     *
     * @param userId 用户id
     * @return object
     */
    @GetMapping("/disableOrEnableUser")
    @LogHandleAnnotation
    public Object disableOrEnableUser(Long userId) {
        if (userId == null || userId < 0) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "用户id非法，禁用失败");
        }

        SysUser sysUser = this.sysUserService.getUserByPrimaryKey(userId);
        if (sysUser == null) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "用户不存在，禁用失败");
        }
        //如果用户状态为启用，则要禁用该用户
        if (Objects.equals(UserStatusEnum.ENABLE.getCode(), sysUser.getStatus())) {
            this.sysUserService.updateUserStatus(userId, UserStatusEnum.DISABLE.getCode());
        } else {
            this.sysUserService.updateUserStatus(userId, UserStatusEnum.ENABLE.getCode());
        }

        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return object
     */
    @GetMapping("/deleteUserByUserId")
    @LogHandleAnnotation
    public Object deleteUserByUserId(Long userId) {

        if (userId == null || userId < 0) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "用户id非法，删除失败");
        }

        SysUser sysUser = this.sysUserService.getUserByPrimaryKey(userId);
        if (sysUser == null) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR.getCode(), "用户不存在，删除失败");
        }

        this.sysUserService.deleteUserByUserId(userId);

        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }
}
