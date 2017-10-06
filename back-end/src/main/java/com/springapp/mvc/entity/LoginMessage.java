package com.springapp.mvc.entity;

public class LoginMessage {
    private String appId;
    private String secret;
    private String jsCode;
    private String grantType;

    public LoginMessage(String code){
        this.appId = 	"wx08f5b41f4053f382";
        this.secret = "e8baac904b89a1ce3cbb4a42f1660b53";
        this.jsCode = code;
        this.grantType = "authorization_code";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
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
