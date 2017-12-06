package com.springapp.mvc.controller;

import com.springapp.mvc.dto.MessageToReturn;
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
import java.util.concurrent.ExecutionException;

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
    public Result showAllByPage(int userId, int pageNum, int pageSize, String order, String sort) {
        Result result = new Result();
        try {
            Message message = new Message();
            Map<String, Object> reMap = iMessageService.selectByPage(userId, pageNum, pageSize, order, sort, message);
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

    /**
     * 展示点赞数量前n的信息
     * @param topn
     * @return
     */
    @RequestMapping(value = "showTopN")
    @ResponseBody
    public Result showTopN(int topn, int userId){
        Result result = new Result();
        try {
            Message message = new Message();
            Map<String, Object> reMap = iMessageService.selectInAWeekByPage(topn, message, userId);
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
    /**
     * 点赞
     */
    @RequestMapping(value = "like")
    @ResponseBody
    public Result like(Integer userId, Integer messageId){
        Result result = new Result();
        try{
            MessageToReturn message = this.iMessageService.like(userId, messageId);
            result.setSuccess(true);
            result.setObject(message);
        } catch(Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("点赞失败 " + e.getMessage());
        }
        return result;
    }
    /**
     * 取消点赞
     *
     */
    @RequestMapping(value="/cancelLike")
    @ResponseBody
    public Result cancelLike(Integer userId, Integer messageId){
        Result result = new Result();
        try{
            MessageToReturn message = this.iMessageService.cancelLike(userId,messageId);
            if(message == null){
                result.setSuccess(false);
                result.setMsg("该用户没有为该信息点赞");
            } else{
                result.setSuccess(true);
                result.setMsg("取消点赞成功");
                result.setObject(message);
            }
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("取消点赞失败");
        }
        return result;

    }
}


//{"session_key":"mfvj0L8aPZthL8YtT7Tf4A==","expires_in":7200,"openid":"o0v4C0SF34tSswPkPLeivUAPuumw"}