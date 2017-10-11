package com.springapp.mvc.controller;

import com.springapp.mvc.dao.MessageMapper;
import com.springapp.mvc.dto.Result;
import com.springapp.mvc.entity.Message;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.IJedisService;
import com.springapp.mvc.service.IMessageService;
import com.springapp.mvc.service.IMyMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "MyMessageController")
public class MyMessaageController {
    private static final String basePath = "http://127.0.0.1:8080/messageWall";
    @Resource
    private IJedisService iJedisService;
    @Resource
    private IMyMessageService iMyMessageService;
    @Resource
    private IMessageService iMessageService;

    @Resource
    private MessageMapper messageMapper;

    @RequestMapping(value = "uploadBackPhoto")
    @ResponseBody
    public Result uploadBackPhoto(String sessionId, MultipartFile file, HttpServletRequest request){
        Result result = new Result();
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String dirPath = rootPath + "/static/img";
        if(!(new File(dirPath).exists())){
            File dir = new File(dirPath);
            dir.mkdirs();
        }
        try {
            Map<String, String> infoMap = this.iJedisService.getAttrFromJedis(sessionId);
            if(infoMap == null){
                result.setSuccess(false);
                result.setMsg("用户未登录或session过期");
                return result;
            }
            System.out.println("infoMap:" + infoMap);
            String filePath = this.iMyMessageService.StoreBackPhoto(infoMap.get("openid"), file, dirPath);
            if(filePath == null){
                result.setSuccess(false);
                result.setMsg("文件格式不正确");
                return result;
            }
            result.setSuccess(true);
            Map<String, String> object = new HashMap<String, String>();
            object.put("imgPath", basePath + filePath);
            result.setObject(object);
            return result;

        } catch (MaxUploadSizeExceededException e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("MaxUploadSizeExceededException");
            return result;
        } catch (IOException ioException){
            ioException.printStackTrace();
            result.setSuccess(false);
            result.setMsg("照片上传失败，IOException");
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("照片上传失败");
            return result;
        }
    }

    /**
     * 获取背景照片
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "gainBackPhoto")
    @ResponseBody
    public Result gainBackPhoto(String sessionId){
        Result result = new Result();
        try{
            Map<String, String> infoMap = this.iJedisService.getAttrFromJedis(sessionId);
            if(infoMap == null){
                result.setSuccess(false);
                result.setMsg("用户未登陆或session过期");
                return result;
            }
            String openid = infoMap.get("openid");
            String photoUrl = this.iMyMessageService.selectBackPhoto(openid);
            result.setSuccess(true);
            Map<String, String> photoUrlMap = new HashMap<String, String>();
            photoUrlMap.put("photoUrl", basePath + photoUrl);
            result.setObject(photoUrlMap);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("获取背景照片失败");
            return result;
        }
    }

    /*
   **
   * 分页展示sessonId对应的用户的发表的信息
    */
    @RequestMapping(value = "showMineByPage")
    @ResponseBody
    public Result showMineByPage(String sessionId, int pageNum, int pageSize, String order, String sort){
        Result result = new Result();
        try {
            Map<String, String> infoMap = this.iJedisService.getAttrFromJedis(sessionId);
            if(infoMap == null) {
                result.setSuccess(false);
                result.setMsg("用户未登录或session过期");
                return result;
            }
            String openid = infoMap.get("openid");
            Message message = new Message();
            message.setUserid(openid);
            Map<String, Object> reMap = iMessageService.selectByPage(pageNum, pageSize, order, sort, message);
            result.setSuccess(true);
            result.setObject(reMap);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("查询用户表白信息失败");
            return result;
        }
    }

    /**
     * 上传匿名头像
     * @param sessionId
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "uploadFakePhoto")
    @ResponseBody
    public Result uploadFakePhoto(String sessionId, MultipartFile file, HttpServletRequest request){
        Result result = new Result();
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String dirPath = rootPath + "/static/img";
        if(!(new File(dirPath).exists())){
            File dir = new File(dirPath);
            dir.mkdirs();
        }
        try {
            Map<String, String> infoMap = this.iJedisService.getAttrFromJedis(sessionId);
            if(infoMap == null){
                result.setSuccess(false);
                result.setMsg("用户未登录或session过期");
                return result;
            }
            String filePath = this.iMyMessageService.uploadAnonymousPhoto(infoMap.get("openid"), file, dirPath);
            if(filePath == null){
                result.setSuccess(false);
                result.setMsg("文件格式不正确");
                return result;
            }
            result.setSuccess(true);
            Map<String, String> object = new HashMap<String, String>();
            object.put("imgPath", basePath + filePath);
            result.setObject(object);
            return result;

        } catch (MaxUploadSizeExceededException e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("MaxUploadSizeExceededException");
            return result;
        } catch (IOException ioException){
            ioException.printStackTrace();
            result.setSuccess(false);
            result.setMsg("照片上传失败，IOException");
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("照片上传失败");
            return result;
        }
    }

    /**
     * 查询用户以前使用过的匿名头像url列表
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "selectAvatarList")
    @ResponseBody
    public Result selectAvatarList(String sessionId){
        Result result = new Result();
        try{
            Map<String, String> infoMap = this.iJedisService.getAttrFromJedis(sessionId);
            if(infoMap == null){
                result.setSuccess(false);
                result.setMsg("用户未登陆或session过期");
                return result;
            }
            String openid = infoMap.get("openid");
            List<String> avatarList = this.iMyMessageService.selectAvatarList(openid);
            for(String avatar : avatarList){
                avatar = basePath + avatar;
            }
            result.setSuccess(true);
            result.setObject(avatarList);
            return result;
        } catch (Exception e){
            result.setSuccess(false);
            result.setMsg("获取匿名头像列表失败");
            return result;
        }
    }


    @RequestMapping(value = "writeMessage")
    @ResponseBody
    public Result writeMessage(String sessionId, Short anonymous, String fakeName,
                               String fakeAvatarUrl, String content) {
        Result result = new Result();
        try{
            Map<String, String> infoMap = this.iJedisService.getAttrFromJedis(sessionId);
            if(infoMap == null){
                result.setSuccess(false);
                result.setMsg("用户未登陆或session过期");
                return result;
            }
            String openid = infoMap.get("openid");
            Message message = new Message();
            message.setUserid(openid);
            message.setAnonymous(anonymous);
            message.setFakenickname(fakeName);
            message.setFakeavatorurl(fakeAvatarUrl);
            message.setContent(content);
            message.setLikenum(0);
            message.setCreatetime(new Date());
            this.messageMapper.insertSelective(message);
            result.setSuccess(true);
            result.setObject(message);
            return result;

        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("发布表白失败");
            return result;
        }

    }

}

