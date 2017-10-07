package com.springapp.mvc.service.impl;

import com.springapp.mvc.commons.interceptor.mybatis.page.Page;
import com.springapp.mvc.commons.interceptor.mybatis.page.PageParameter;
import com.springapp.mvc.dao.MessageMapper;
import com.springapp.mvc.entity.Message;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.IMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> selectByPage(int pageNum, int pageSize, String order, String sort, Message msg) throws Exception{

        PageParameter pageParameter = new PageParameter(pageNum, pageSize);
        Map<String, Object> map =new HashMap<String, Object>();
        map.put("order", order);
        map.put("sort", sort);
        Page page = new Page(pageParameter, map, msg);

        List<Message> msgList = messageMapper.selectAllByPage(page);
        int totalNum = pageParameter.getTotalCount();
        Map<String, Object> reMap = new HashMap<String, Object>();
        reMap.put("totalNum:", totalNum);
        reMap.put("rows:", msgList);
        return reMap;
    }
}
