package com.springapp.mvc.service;

import com.springapp.mvc.entity.User;

public interface IMessageService {
    public void writeMessage(String msg, User user, Short anonymous)throws Exception;
}
