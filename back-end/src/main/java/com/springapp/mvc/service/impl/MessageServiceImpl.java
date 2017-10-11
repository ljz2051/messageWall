package com.springapp.mvc.service.impl;

import com.springapp.mvc.commons.interceptor.mybatis.page.Page;
import com.springapp.mvc.commons.interceptor.mybatis.page.PageParameter;
import com.springapp.mvc.dao.MessageMapper;
import com.springapp.mvc.dao.UserInfoMapper;
import com.springapp.mvc.entity.Message;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.entity.UserInfo;
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
    private MessageMapper messageMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    public Map<String, Object> selectByPage(int pageNum, int pageSize, String order, String sort, Message msg) throws Exception{

        PageParameter pageParameter = new PageParameter(pageNum, pageSize);
        Map<String, Object> map =new HashMap<String, Object>();
        map.put("order", order);
        map.put("sort", sort);
        Page page = new Page(pageParameter, map, msg);

        List<Message> msgList = messageMapper.selectAllByPage(page);
        for(Message message : msgList){
            if(message.getAnonymous() == 0){
                String userId = message.getUserid();
                UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(userId);
                if(userInfo == null)
                    throw new Exception();
                message.setFakenickname(userInfo.getWxnickname());
                message.setFakeavatorurl(userInfo.getWxavatarurl());
            }
        }
        int totalNum = pageParameter.getTotalCount();
        Map<String, Object> reMap = new HashMap<String, Object>();
        reMap.put("totalNum:", totalNum);
        reMap.put("rows:", msgList);
        return reMap;
    }

    public Map<String, Object> selectInAWeekByPage(int topn, Message msg) throws Exception{
        PageParameter pageParameter = new PageParameter(1, topn);
        Map<String, Object> map =new HashMap<String, Object>();
        map.put("order", "likeNum");
        map.put("sort", "desc");
        Page page = new Page(pageParameter, map, msg);

        List<Message> msgList = this.messageMapper.selectInAWeekByPage(page);
        for(Message message : msgList){
            if(message.getAnonymous() == 0){
                String userId = message.getUserid();
                UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(userId);
                if(userInfo == null)
                    throw new Exception();
                message.setFakenickname(userInfo.getWxnickname());
                message.setFakeavatorurl(userInfo.getWxavatarurl());
            }
        }
        int totalNum = pageParameter.getTotalCount();
        Map<String, Object> reMap = new HashMap<String, Object>();
        reMap.put("totalNum:", totalNum);
        reMap.put("rows:", msgList);
        return reMap;
    }
}
