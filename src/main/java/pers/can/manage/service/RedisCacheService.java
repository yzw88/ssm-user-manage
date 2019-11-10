package pers.can.manage.service;

/**
 * redis缓存服务接口
 *
 * @author Waldron Ye
 * @date 2019/11/10 11:01
 */
public interface RedisCacheService {

    /**
     * 添加验证码到缓存中
     *
     * @param codeKey 保存缓存中的key
     * @param code    图形验证码
     */
    void addImgCode(String codeKey, String code);

    /**
     * 获取验证码
     *
     * @param codeKey 缓存中的key
     * @return 验证码
     */
    String getValidCode(String codeKey);

    /**
     * 添加用户信息到缓存中
     *
     * @param key   保存到缓存中的key
     * @param value 保存到缓存的value
     */
    void addUserInfo(String key, String value);
}
