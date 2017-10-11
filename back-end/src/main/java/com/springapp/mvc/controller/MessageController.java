package com.springapp.mvc.controller;

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
import java.util.Map;

@Controller
@RequestMapping(value = "MessageController/")
public class MessageController {

    @Resource
    IMessageService iMessageService;


    /**
     * 分页展示全部的信息
     */
    @RequestMapping(value = "showAllByPage")
    @ResponseBody
    public Result showAllByPage(int pageNum, int pageSize, String order, String sort) {
        Result result = new Result();
        try {
            Message message = new Message();
            Map<String, Object> reMap = iMessageService.selectByPage(pageNum, pageSize, order, sort, message);
            result.setSuccess(true);
            result.setObject(reMap);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("查询失败");
            return result;
        }
    }

    @RequestMapping(value = "showTopN")
    @ResponseBody
    public Result showTopN(int topn){
        Result result = new Result();
        try {
            Message message = new Message();
            Map<String, Object> reMap = iMessageService.selectInAWeekByPage(topn, message);
            result.setSuccess(true);
            result.setObject(reMap);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("查询失败");
            return result;
        }
    }
}


//{"session_key":"mfvj0L8aPZthL8YtT7Tf4A==","expires_in":7200,"openid":"o0v4C0SF34tSswPkPLeivUAPuumw"}