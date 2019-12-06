package pers.can.manage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.can.manage.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("执行loadUserByUsername==");
        // 先设置假的权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 传入角色
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // 创建用户
        return new User(username, "{noop}admin", authorities);
    }
}
