package com.polo.apollo.shiro.config;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.common.util.Utils;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author baoqianyong
 * @date 2019/07/15
 */
public class ShiroFilter extends PassThruAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            if (isAjax(WebUtils.toHttp(request))) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().write(Utils.obj2Json(Result.error()));
            }else {
                WebUtils.toHttp(response).sendRedirect("/login");
            }
            return false;
        }
    }

    public boolean isAjax(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header);
    }
}
