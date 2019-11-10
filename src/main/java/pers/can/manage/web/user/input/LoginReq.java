package pers.can.manage.web.user.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 登录请求对象
 *
 * @author Waldron Ye
 * @date 2019/11/10 10:52
 */
@Data
public class LoginReq implements Serializable {
    private static final long serialVersionUID = 6858166295403935661L;

    /**
     * 图形验证码key
     */
    @NotNull(message = "请重新获取图形验证码")
    @Size(min = 2, max = 255, message = "请重新获取图形验证码")
    private String codeKey;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    @Size(min = 2, max = 255, message = "用户名长度非法")
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @Size(min = 2, max = 255, message = "密码长度非法")
    private String password;
    /**
     * 图形验证码
     */
    @NotNull(message = "验证码不能为空")
    @Size(min = 2, max = 255, message = "验证码长度非法")
    private String imgCode;
}
