package com.monitor.common.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: TODO

 * @author: Administrator

 * @date: 2013-10-20 下午12:38:56
 */
@Component
public class BaseInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		
		request.setAttribute("ctx", request.getContextPath());
		
		return true;
	}
}
