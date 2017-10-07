package com.springapp.mvc.controller;

import com.springapp.mvc.commons.interceptor.mybatis.page.Page;
import com.springapp.mvc.commons.interceptor.mybatis.page.PageParameter;
import com.springapp.mvc.dao.MessageMapper;
import com.springapp.mvc.dto.Result;
import com.springapp.mvc.entity.Message;
import com.springapp.mvc.entity.User;

import com.springapp.mvc.service.IMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "MessageController/")
public class MessageController {

    @Resource
    IMessageService iMessageService;

    @RequestMapping(value = "writeMessage")
    @ResponseBody
    public Result writeMessage(String message, Short anonymous, HttpServletRequest request){
        Result result = new Result();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(session.getId());
        try{
            System.out.println("sessionId" + session.getId());
            //System.out.println("openid" + user.getOpenid());
            if(user != null) {
                this.iMessageService.writeMessage(message, user, anonymous);
                result.setSuccess(true);
                return result;
            }
            else{
                result.setSuccess(false);
                result.setMsg("用户未登录或session过期");
                return result;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            return result;
        }
    }

    /**
     *分页展示全部的信息
     */
    @RequestMapping(value = "showAllByPage")
    @ResponseBody
    public Result showAllByPage(int pageNum, int pageSize, String order, String sort){
        Result result = new Result();
        try {
            Message message = new Message();
            Map<String, Object> reMap = iMessageService.selectByPage(pageNum,pageSize, order, sort, message);
            result.setSuccess(true);
            result.setObject(reMap);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("查询失败");
            return result;
        }
    }
    /*
    **
    * 分页展示sessonId对应的用户的发表的信息
     */
    @RequestMapping(value = "showMineByPage")
    @ResponseBody
    public Result showMineByPage(String sessionId, int pageNum, int pageSize, String order, String sort, HttpServletRequest request){
        Result result = new Result();
        User user = (User)request.getSession().getAttribute(sessionId);
        if(user == null){
            result.setSuccess(false);
            result.setMsg("用户未登录或session过期");
        }
        try{
            Message message = new Message();
            message.setUserid(user.getOpenid());
            Map<String, Object> reMap = iMessageService.selectByPage(pageNum, pageSize, order, sort, message);
            result.setSuccess(true);
            result.setObject(reMap);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            return result;
        }
    }
}

//{"session_key":"mfvj0L8aPZthL8YtT7Tf4A==","expires_in":7200,"openid":"o0v4C0SF34tSswPkPLeivUAPuumw"}