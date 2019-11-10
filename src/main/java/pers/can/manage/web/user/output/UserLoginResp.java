package pers.can.manage.web.user.output;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录响应对象
 *
 * @author Waldron Ye
 * @date 2019/11/10 12:05
 */
@Data
public class UserLoginResp implements Serializable {
    private static final long serialVersionUID = 6236825383806316643L;

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
}
