package pers.can.manage;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类
 *
 * @author Waldron Ye
 * @date 2019/11/9 11:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/applicationContext.xml", "classpath:config/spring-mvc.xml"})
@Slf4j
public class UserManageApplicationTest {
}
