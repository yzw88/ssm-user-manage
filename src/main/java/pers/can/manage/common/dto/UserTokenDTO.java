package pers.can.manage.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户token传输对象
 *
 * @author Waldron Ye
 * @date 2019/11/10 11:47
 */
@Data
public class UserTokenDTO implements Serializable {

    private static final long serialVersionUID = 3295254817904676107L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private String status;

    /**
     * redis中获取token的key
     */
    private String redisTokenKey;

    /**
     * 过期时间
     */
    private Date expireDate;
}
