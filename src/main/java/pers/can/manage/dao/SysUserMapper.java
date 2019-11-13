package pers.can.manage.dao;

import org.apache.ibatis.annotations.Param;
import pers.can.manage.model.SysUser;
import pers.can.manage.web.user.input.PageUserQueryReq;

import java.util.List;

/**
 * 用户dao
 *
 * @author Waldron Ye
 * @date 2019/11/9 10:44
 */
public interface SysUserMapper {

    /**
     * 通过用户主键删除
     *
     * @param userId 用户id
     * @return 删除记录数
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * 添加用户
     *
     * @param record 用户model
     * @return 插入记录数
     */
    int insertSelective(SysUser record);

    /**
     * 查询用户model
     *
     * @param userId 用户id
     * @return 用户model
     */
    SysUser selectByPrimaryKey(Long userId);

    /**
     * 更新用户信息
     *
     * @param record 用户model
     * @return 更新记录数
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 查询用户model
     *
     * @param username 用户名
     * @return 用户model
     */
    SysUser selectByUserName(@Param("username") String username);

    /**
     * 获取用户分页列表
     *
     * @param pageUserQueryReq 用户分页对象
     * @return list
     */
    List<SysUser> listPageUser(PageUserQueryReq pageUserQueryReq);

    /**
     * 更新用户状态
     *
     * @param userId 用户id
     * @param status 用户状态
     * @return 更新记录数
     */
    int updateUserStatus(@Param("userId") Long userId, @Param("status") String status);
}
