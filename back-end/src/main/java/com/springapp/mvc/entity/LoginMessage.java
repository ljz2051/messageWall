package com.springapp.mvc.entity;

public class LoginMessage {
    public static final String appId = "wx08f5b41f4053f382";
    public static final String secret = "e8baac904b89a1ce3cbb4a42f1660b53";
    private String jsCode;
    private String grantType;

    //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx08f5b41f4053f382&secret=e8baac904b89a1ce3cbb4a42f1660b53
    public LoginMessage(String code){
        this.jsCode = code;
        this.grantType = "authorization_code";
    }

    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }

    public String getJsCode() {
        return jsCode;
    }

    public void setJsCode(String jsCode) {
        this.jsCode = jsCode;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
//https://api.weixin.qq.com/wxa/getwxacode?access_token=JmUeVYVR6soQ1nzhQ9fYDZAhPR4yU8sChCI6HVMmeo-RWvrtp5dAhLxQtkXw5bGVRMqVBPRzRQ57w9Xcj5HDER3CqK3TxCxIq0q4RewNVthivp1jvYQUvmR3uuAN8DxTBFDbAEAIMK
