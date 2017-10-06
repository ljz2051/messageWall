package com.springapp.mvc.service.impl;

import com.springapp.mvc.dao.MessageMapper;
import com.springapp.mvc.entity.Message;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.IMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class MessageServiceImpl implements IMessageService {
    @Resource
    MessageMapper messageMapper;

    public void writeMessage(String msg, User user, Short anonymous)throws Exception{
        System.out.println("msg:" + msg);
        if(anonymous != null){
            anonymous = 1;
        }
        else{
            anonymous = 0;
        }
        Message message = new Message(user.getOpenid(), 0, anonymous, new Date(), msg);
        messageMapper.insertSelective(message);
    }
}
