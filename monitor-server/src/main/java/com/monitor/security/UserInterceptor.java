
package com.monitor.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author hui.zhang
 *
 */
public class UserInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getUserPrincipal() != null) {
            request.setAttribute(SimpleAuthz.USER_PRINCIPAL, request.getUserPrincipal());
            request.setAttribute("userName", request.getUserPrincipal().getName());
        }

        return true;
    }

}
