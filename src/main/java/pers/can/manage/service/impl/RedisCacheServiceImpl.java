package pers.can.manage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.can.manage.service.RedisCacheService;
import pers.can.manage.util.RedisUtil;

import javax.annotation.Resource;

/**
 * redis缓存服务实现类
 *
 * @author Waldron Ye
 * @date 2019/11/10 11:01
 */
@Service
@Slf4j
public class RedisCacheServiceImpl implements RedisCacheService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void addImgCode(String codeKey, String code) {
        log.info("图形验证码key为:{}", codeKey);
        //保存redis中，10分钟失效
        this.redisUtil.setString(codeKey, code, 600);
    }

    @Override
    public String getValidCode(String codeKey) {
        return this.redisUtil.getString(codeKey);
    }

    @Override
    public void addUserInfo(String key, String value) {
        this.redisUtil.setString(key, value, 600);
    }


}
