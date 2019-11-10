package pers.can.manage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pers.can.manage.UserManageApplicationTest;
import pers.can.manage.model.SysUser;
import pers.can.manage.service.SysUserService;
import pers.can.manage.util.FastJsonUtil;

/**
 * 用户服务测试类
 *
 * @author Waldron Ye
 * @date 2019/11/9 11:01
 */
@Slf4j
public class SysUserServiceImplTest extends UserManageApplicationTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void getUserByPrimaryKey() {
        Long userId = 1L;
        SysUser sysUser = this.sysUserService.getUserByPrimaryKey(userId);
        log.info("查询用户信息:sysUser={}", FastJsonUtil.toJson(sysUser));
    }
}