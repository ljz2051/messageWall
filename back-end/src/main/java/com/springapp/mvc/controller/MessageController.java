package com.springapp.mvc.controller;

import com.springapp.mvc.entity.User;

import com.springapp.mvc.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "MessageController/")
public class MessageController {

    @Resource
    IMessageService iMessageService;

    @RequestMapping(value = "writeMessage")
    public void writeMessage(String message, Short anonymous, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(session.getId());
        try{
            System.out.println("sessionId" + session.getId());
            System.out.println("openid" + user.getOpenid());
            if(user != null) {
                this.iMessageService.writeMessage(message, user, anonymous);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

//{"session_key":"mfvj0L8aPZthL8YtT7Tf4A==","expires_in":7200,"openid":"o0v4C0SF34tSswPkPLeivUAPuumw"}