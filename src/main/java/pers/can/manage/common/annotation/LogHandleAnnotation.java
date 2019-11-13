

package pers.can.manage.common.annotation;

import java.lang.annotation.*;

/**
 * 日志处理注解
 *
 * @author Waldron Ye
 * @date 2019/6/9 22:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogHandleAnnotation {

    String value() default "";
}
