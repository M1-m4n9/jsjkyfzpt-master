package com.example.springserver2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/9/18 22:00
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("/index");
    }
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("session");
        return new ModelAndView("/logout");
    }
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("session","libo");
        return new ModelAndView("login");
    }
}
