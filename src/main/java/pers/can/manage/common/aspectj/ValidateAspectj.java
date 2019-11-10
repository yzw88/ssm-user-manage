package pers.can.manage.common.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 校验切面类
 *
 * @author Waldron Ye
 * @date 2019/6/9 22:20
 */
@Aspect
@Component
@Slf4j
@Order(9)
public class ValidateAspectj {

    public ValidateAspectj() {
        log.info("实例化ValidateAspectj==");
    }

    @Pointcut("execution(* pers.can.manage.web.*.controller..*(..)) && @annotation(pers.can.manage.common.annotation.ValidateAnnotation)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) point.getSignature();
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();

        log.info("请求方法名称:methodName={},类名称:className={}", methodName, className);

        //执行方法
        Object result = point.proceed();
        long useTime = System.currentTimeMillis() - beginTime;
        log.info("执行时间:useTime={}", useTime);

        return result;
    }


}
