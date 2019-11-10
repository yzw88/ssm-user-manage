package pers.can.manage.web.user.input;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户编辑传输对象
 *
 * @author Waldron Ye
 * @date 2019/11/10 19:53
 */
@Data
public class UserEditReq implements Serializable {
    private static final long serialVersionUID = 6562429043224662321L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户状态
     */
    private String status;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;


}
