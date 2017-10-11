package com.springapp.mvc.service;

import com.springapp.mvc.dto.Result;
import com.springapp.mvc.entity.User;

public interface ILoginService {
    public Result codeForSessionKey(String code);
    public boolean checkUserInfo(String sessionKey, String rawData, String signature);
    public void storeNickNameAndAnatar(String openid, String nickName, String avatarUrl) throws Exception;
}
