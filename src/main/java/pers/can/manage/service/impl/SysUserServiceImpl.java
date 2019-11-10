package pers.can.manage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.can.manage.common.constant.RedisConstant;
import pers.can.manage.common.dto.UserTokenDTO;
import pers.can.manage.dao.SysUserMapper;
import pers.can.manage.model.SysUser;
import pers.can.manage.service.RedisCacheService;
import pers.can.manage.service.SysUserService;
import pers.can.manage.util.DateUtil;
import pers.can.manage.util.FastJsonUtil;
import pers.can.manage.util.JwtUtil;
import pers.can.manage.web.user.input.PageUserQueryReq;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户service实现类
 *
 * @author Waldron Ye
 * @date 2019/11/9 10:55
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisCacheService redisCacheService;

    @Override
    public SysUser getUserByPrimaryKey(Long userId) {
        return this.sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public SysUser selectByUserName(String username) {
        return this.sysUserMapper.selectByUserName(username);
    }

    @Override
    public String getTokenByUser(SysUser sysUser) {
        log.info("获取token的userId={}", sysUser.getUserId());
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setMobile(sysUser.getMobile());
        userTokenDTO.setStatus(sysUser.getStatus());
        userTokenDTO.setUserId(sysUser.getUserId());
        userTokenDTO.setUsername(sysUser.getUsername());

        String key = RedisConstant.KEY_USER_TOKEN + sysUser.getUserId();
        userTokenDTO.setRedisTokenKey(key);

        Date expireDate = DateUtil.add(new Date(), 12, 30);
        userTokenDTO.setExpireDate(expireDate);

        String json = FastJsonUtil.toJson(userTokenDTO);
        log.info("待生成token的json:{}", json);
        String token = JwtUtil.createToken(json);
        log.info("用户id:{},生成token:{}", sysUser.getUserId(), token);

        this.redisCacheService.addUserInfo(key, json);

        return token;
    }

    @Override
    public List<SysUser> listPageUser(PageUserQueryReq pageUserQueryReq) {
        return this.sysUserMapper.listPageUser(pageUserQueryReq);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateUser(SysUser record) {
        return this.sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertUser(SysUser record) {
        return this.sysUserMapper.insertSelective(record);
    }
}
