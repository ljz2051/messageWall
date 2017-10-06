package com.springapp.mvc.controller;

import com.springapp.mvc.commons.Result;
import com.springapp.mvc.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "LoginController/")
public class LoginController {
    @Autowired
    LoginServiceImpl loginService;

    @RequestMapping(value = "codeForSessionKey")
    @ResponseBody
    public String codeForSessionKey(String code, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(10 * 60);
        Result transResult = this.loginService.codeForSessionKey(code);
        if(transResult.isSuccess()){
            Object user = transResult.getObject();
            session.setAttribute(session.getId(), user);
            System.out.println("sessionId" + session.getId());
            return session.getId();
        }
        return transResult.getMsg();
    }

}
