package pers.can.manage.web.user.output;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息响应对象
 *
 * @author Waldron Ye
 * @date 2019/11/13 19:55
 */
@Data
public class UserInfoResp implements Serializable {
    private static final long serialVersionUID = -4229937851213799637L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private String status;

    /**
     * 部门id
     */
    private Long deptId;

}
