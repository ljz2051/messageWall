package com.springapp.mvc.service.impl;

import com.springapp.mvc.commons.interceptor.mybatis.page.Page;
import com.springapp.mvc.commons.interceptor.mybatis.page.PageParameter;
import com.springapp.mvc.dao.MessageMapper;
import com.springapp.mvc.dao.UserInfoMapper;
import com.springapp.mvc.dto.MessageToReturn;
import com.springapp.mvc.entity.Message;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.entity.UserInfo;
import com.springapp.mvc.service.IMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MessageServiceImpl implements IMessageService {
    private static final String basePath = "http://47.94.146.199:8080";
    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     *
     * @param id 用户id
     * @param pageNum
     * @param pageSize
     * @param order
     * @param sort
     * @param msg
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectByPage(int id, int pageNum, int pageSize, String order, String sort, Message msg) throws Exception{

        PageParameter pageParameter = new PageParameter(pageNum, pageSize);
        Map<String, Object> map =new HashMap<String, Object>();
        map.put("order", order);
        map.put("sort", sort);
        Page page = new Page(pageParameter, map, msg);

        List<Message> msgList = messageMapper.selectAllByPage(page);
        for(Message message : msgList){
            if(message.getAnonymous() == 0){
                String userId = message.getUserid();
                UserInfo userInfo = this.userInfoMapper.selectByOpenId(userId);
                if(userInfo == null)
                    throw new Exception();
                message.setFakenickname(userInfo.getWxnickname());
                message.setFakeavatorurl(userInfo.getWxavatarurl());
            } else{
                String avatorurl = message.getFakeavatorurl();
                if(avatorurl != null && !avatorurl.equals(""))
                    message.setFakeavatorurl(basePath + avatorurl);
            }
        }
        int totalNum = pageParameter.getTotalCount();

        List<MessageToReturn> returnList = messageHandler(msgList, id);
        Map<String, Object> reMap = new HashMap<String, Object>();
        reMap.put("totalNum", totalNum);
        reMap.put("rows", returnList);
        return reMap;
    }

    /**
     *
     * @param topn
     * @param msg
     * @param id 用户id
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectInAWeekByPage(int topn, Message msg, int id) throws Exception{
        PageParameter pageParameter = new PageParameter(1, topn);
        Map<String, Object> map =new HashMap<String, Object>();
        map.put("order", "likeNum");
        map.put("sort", "desc");
        Page page = new Page(pageParameter, map, msg);

        List<Message> msgList = this.messageMapper.selectInAWeekByPage(page);
        for(Message message : msgList){
            if(message.getAnonymous() == 0){
                String userId = message.getUserid();
                UserInfo userInfo = this.userInfoMapper.selectByOpenId(userId);
                if(userInfo == null)
                    throw new Exception();
                message.setFakenickname(userInfo.getWxnickname());
                message.setFakeavatorurl(userInfo.getWxavatarurl());
            } else{
                String avatorurl = message.getFakeavatorurl();
                if(avatorurl != null && !avatorurl.equals(""))
                    message.setFakeavatorurl(basePath + avatorurl);
            }
        }
        int totalNum = pageParameter.getTotalCount();

        List<MessageToReturn> returnList = messageHandler(msgList, id);
        Map<String, Object> reMap = new HashMap<String, Object>();
        reMap.put("totalNum", totalNum);
        reMap.put("rows", returnList);
        return reMap;
    }

    //点赞功能
    public MessageToReturn like(Integer userId, Integer messageId) throws Exception{
        Message message = this.messageMapper.selectByPrimaryKey(messageId);
        if(message == null){
            throw new Exception("id所代表的信息不存在");
        }
        message.setLikenum(message.getLikenum() + 1);
        message.addUserInLikeUserList(userId);
        this.messageMapper.updateByPrimaryKeySelective(message);

        List<Message> temp = new ArrayList<Message>();
        temp.add(message);
        return messageHandler(temp, userId).get(0);
    }
    //对List<Message>进行处理，变成List<MessageToReturn>返回前端
    public List<MessageToReturn> messageHandler(List<Message> messageList, Integer userId) throws Exception{
        List<MessageToReturn> returnList = new ArrayList<MessageToReturn>();
        for(Message message : messageList){
            MessageToReturn reMessage = new MessageToReturn();
            reMessage.setId(message.getId());
            reMessage.setLikenum(message.getLikenum());
            reMessage.setAnonymous(message.getAnonymous());
            reMessage.setCreatetime(message.getCreatetime());
            reMessage.setFakenickname(message.getFakenickname());
            reMessage.setFakeavatorurl(message.getFakeavatorurl());
            reMessage.setContent(message.getContent());
            if(message.getLikeUserList() == null){
                reMessage.setLikeFlag(false);
            }else if(message.getLikeUserList().contains(userId)){
                reMessage.setLikeFlag(true);
            }else{
                reMessage.setLikeFlag(false);
            }
            returnList.add(reMessage);
        }
        return returnList;
    }

    //取消点赞
    public MessageToReturn cancelLike(Integer userId, Integer messageId) throws Exception{
        Message message = this.messageMapper.selectByPrimaryKey(messageId);
        if(message == null){
            System.out.println("1");
            throw new Exception("id所代表的信息不存在");
        }
        if(message.deleteUserInLikeUserList(userId)){
            System.out.println("2");
            message.setLikenum(message.getLikenum() - 1);
            this.messageMapper.updateByPrimaryKeySelective(message);
            List<Message> temp = new ArrayList<Message>();
            temp.add(message);
            return messageHandler(temp, userId).get(0);
        } else{
            System.out.println("3");
            return null;
        }

    }
}
