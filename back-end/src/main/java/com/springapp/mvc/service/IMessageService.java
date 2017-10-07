package com.springapp.mvc.service;

import com.springapp.mvc.entity.Message;
import com.springapp.mvc.entity.User;

import java.util.Map;

public interface IMessageService {
    void writeMessage(String msg, User user, Short anonymous)throws Exception;
    Map<String, Object> selectByPage(int pageNum, int pageSize, String order, String sort, Message msg) throws Exception;
}
