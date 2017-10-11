package com.springapp.mvc.controller;

import com.springapp.mvc.dto.Result;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.IJedisService;
import com.springapp.mvc.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "LoginController/")
public class LoginController {
    @Autowired
    private ILoginService iLoginService;

    @Autowired
    private IJedisService iJedisService;

    @RequestMapping(value = "codeForSessionKey")
    @ResponseBody
    public Result codeForSessionKey(String code, HttpServletRequest request){
        Result result = new Result();
        String sessionId = request.getSession().getId();
        Result transResult = this.iLoginService.codeForSessionKey(code);
        if(transResult.isSuccess()){
            try {
                User user = (User) transResult.getObject();
                Map<String, String> infoMap = new HashMap<String, String>();
                infoMap.put("openid",user.getOpenid());
                infoMap.put("session_key", user.getSessionKey());
                System.out.println(infoMap);
                this.iJedisService.setAttrInJedis(sessionId, infoMap, user.getExpiresIn());
                System.out.println("sessionId#" + sessionId);
                result.setSuccess(true);
                Map<String, String> object = new HashMap<String, String>();
                object.put("sessionId", sessionId);
                result.setObject(object);
                return result;

            } catch (Exception e){
                result.setSuccess(false);
                result.setMsg("插入redis失败");
                return result;
            }

        }
        else{
            result.setSuccess(false);
            result.setMsg(transResult.getMsg());
            return result;
        }
    }

    @RequestMapping(value = "uploadNickName")
    @ResponseBody
    public Result uploadNickName(String sessionId, String nickName, String avatarUrl,
                                 String rawData, String signature, HttpServletRequest request){
        Result result = new Result();
        try {
            Map<String, String> infoMap = this.iJedisService.getAttrFromJedis(sessionId);
            if(infoMap == null){
                result.setSuccess(false);
                result.setMsg("用户未登录或session过期");
                return result;
            }
            if(!this.iLoginService.checkUserInfo(infoMap.get("session_key"), rawData, signature)){
                result.setSuccess(false);
                result.setMsg("数据签名校验失败");
                return result;
            }

            this.iLoginService.storeNickNameAndAnatar(infoMap.get("openid"), nickName, avatarUrl);
            result.setSuccess(true);
            return result;

        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("上传用户昵称和头像失败");
            return result;
        }
    }

}
