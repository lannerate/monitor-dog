
package com.heagle.monitor.controller;

import com.heagle.monitor.security.RegisterException;
import com.heagle.monitor.security.User;
import com.heagle.monitor.security.UserManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Author: hui.zhang
 */
@Controller
public class UserAction {
    private static Logger logger = LoggerFactory.getLogger(UserAction.class);
    @Resource
    private UserManager userManager;

    @RequestMapping(value = "/members/search", method = RequestMethod.GET)
    public String show(ModelMap map) {
        List<User> users = userManager.listUsers();

        map.put("users", users);
        return "";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap map) {

        return "user/login";
    }

    @RequestMapping(value = "/check")
    public String check(User user, ModelMap map,HttpServletRequest request) throws UnsupportedEncodingException {
        logger.debug("try to login by {} ",user);
        User _user = userManager.loadUserByUsername(user.getUsername());
        if (_user == null) {
            map.put("flashMsg", "user or password error");
            return "user/login";
        }
        if (!StringUtils.equals(user.getPassword(), _user.getPassword())) {
            map.put("flashMsg", "err.password_err");
            return "user/login";
        }
        if (!_user.isEnabled()) {
            return "redirect:/user/wait?username=" + URLEncoder.encode(user.getUsername(), "utf-8");
        }
//        return "redirect:/projects";
        return "redirect:/monitor/index";
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public String register(ModelMap map) {

        return "user/new";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String register(User user, HttpServletRequest request,ModelMap mm) throws UnsupportedEncodingException {
        user.setEnabled(false);
        //spring mvc can't auto set password by  ,why?
        user.setPassword(request.getParameter("password"));

        mm.put("user", user);
        try {
            userManager.registerUser(user);

            return "redirect:/user/wait?username=" + URLEncoder.encode(user.getUsername(), "utf-8");
        } catch (RegisterException e) {

            mm.put("flashMsg", e.getMessage());
            return "user/new";
        }

    }

    @RequestMapping(value = "/user/wait", method = RequestMethod.GET)
    public String wait(User user, ModelMap mm) {
        mm.put("user", user);
        return "user/wait";
    }
}
