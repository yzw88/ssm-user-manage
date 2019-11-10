package pers.can.manage.service;

import pers.can.manage.model.SysUser;
import pers.can.manage.web.user.input.PageUserQueryReq;

import java.util.List;

/**
 * 用户service接口
 *
 * @author Waldron Ye
 * @date 2019/11/9 10:54
 */
public interface SysUserService {

    /**
     * 查询用户model
     *
     * @param userId 用户id
     * @return 用户model
     */
    SysUser getUserByPrimaryKey(Long userId);

    /**
     * 查询用户model
     *
     * @param username 用户名
     * @return 用户model
     */
    SysUser selectByUserName(String username);

    /**
     * 生成token
     *
     * @param sysUser 用户model
     * @return string
     */
    String getTokenByUser(SysUser sysUser);

    /**
     * 获取用户分页列表
     *
     * @param pageUserQueryReq 用户分页对象
     * @return list
     */
    List<SysUser> listPageUser(PageUserQueryReq pageUserQueryReq);

    /**
     * 更新用户信息
     *
     * @param record 用户model
     * @return 更新记录数
     */
    int updateUser(SysUser record);

    /**
     * 添加用户
     *
     * @param record 用户model
     * @return 插入记录数
     */
    int insertUser(SysUser record);
}
