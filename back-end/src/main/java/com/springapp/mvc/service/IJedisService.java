package com.springapp.mvc.service;

import java.util.Map;

public interface IJedisService {
    public void setAttrInJedis(String sessionId, Map<String, String> userInfo, int expireTime) throws Exception;
    public Map<String, String> getAttrFromJedis(String sessionId) throws Exception;

}
