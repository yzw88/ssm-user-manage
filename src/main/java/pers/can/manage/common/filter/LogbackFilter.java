package pers.can.manage.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import pers.can.manage.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 日志过滤器
 *
 * @author Waldron Ye
 * @date 2019/6/2 13:21
 */
@Slf4j
public class LogbackFilter implements Filter {

    private static final String THREAD_ID = "threadId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 获取项目路径
        String path = httpServletRequest.getRequestURI().replace(
                httpServletRequest.getServletPath(), "");
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort() + path;
        //添加项目路径到session
        ((HttpServletRequest) request).getSession().setAttribute("path", basePath);
        boolean flag = this.insertMDC();
        try {
            chain.doFilter(request, response);
        } finally {
            if (flag) {
                MDC.remove(THREAD_ID);
            }
        }
    }

    private boolean insertMDC() {
        String uniqueId = THREAD_ID + "-" + StringUtil.getRandomCharAndNum(5);
        log.info("产生线程号:threadId={}", uniqueId);
        MDC.put(THREAD_ID, uniqueId);
        return true;
    }

    @Override
    public void destroy() {

    }
}
