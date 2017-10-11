package com.springapp.mvc.service;

import com.springapp.mvc.entity.Message;
import com.springapp.mvc.entity.User;

import java.util.Map;

public interface IMessageService {
    Map<String, Object> selectByPage(int pageNum, int pageSize, String order, String sort, Message msg) throws Exception;
    Map<String, Object> selectInAWeekByPage(int topn, Message message) throws Exception;
}
