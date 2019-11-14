package pers.can.manage.common.constant;

/**
 * 公共常量配置
 *
 * @author Waldron Ye
 * @date 2019/11/9 19:56
 */
public class CommonConstant {

    /**
     * 用户登录页面
     */
    public static final String USER_LOGIN_URL = "user/login";

    /**
     * 用户token的key
     */
    public static final String KEY_USER_TOKEN = "AuthorizationToken";

    /**
     * 初始化盐值
     */
    public static final String INIT_SALT = "123456";

    /**
     * 初始化密码
     */
    public static final String INIT_PASSWORD = "123456";

    /**
     * 保存到session的用户信息对应的key
     */
    public static final String SESSION_USER = "session_user";

    /**
     * jsp页面请求前缀
     */
    public static final String VIEW_URL_PREFIX = "/view/";
}
