package com.springapp.mvc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springapp.mvc.commons.Result;
import com.springapp.mvc.entity.LoginMessage;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.ILoginService;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class LoginServiceImpl implements ILoginService{
    /*
    **code 换取 session_key
     */
    public Result codeForSessionKey(String code){
        Result result = new Result();
        LoginMessage loginMessage = new LoginMessage(code);
        String urlString = "https://api.weixin.qq.com/sns/jscode2session?appid=" + loginMessage.getAppId() +
                "&secret=" + loginMessage.getSecret() + "&js_code=" + loginMessage.getJsCode() +
                "&grant_type=" + loginMessage.getGrantType();
        System.out.println(urlString);
        try{
            URL url = new URL(urlString);
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setConnectTimeout(1000 * 10);
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String input;
            while((input = br.readLine()) != null){
                sb.append(input);
            }
            br.close();
            inputStreamReader.close();

            //正常返回的JSON数据包{"openid": "OPENID",   "session_key": "SESSIONKEY", "unionid": "UNIONID"}
            //错误时返回JSON数据包(示例为Code无效){"errcode": 40029,"errmsg": "invalid code"}
            User user = new User();
            System.out.println(sb.toString());
            JSONObject response = JSON.parseObject(sb.toString());
             if(response.containsKey("openid")){
                 user.setOpenid((String)response.get("openid"));
                 user.setSessionKey((String)response.get("session_key"));
                 result.setSuccess(true);
                 result.setObject(user);
                 return result;
             }
             else{
                 result.setSuccess(false);
                 result.setMsg("invalid code");
                 return result;
             }

        }
        catch (MalformedURLException e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("MalformedURLException");
            return result;
        }
        catch (IOException e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("IOException");
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            return result;
        }
    }



    public static void main(String[] args){
        String https_url = "https://www.baidu.com/";
        try {
            URL url = new URL(https_url);
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            System.out.println(conn.getResponseCode());
            System.out.println(conn.getResponseMessage());
            System.out.println("****** Content of the URL ********");
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String input;
            while((input = br.readLine()) != null){
                System.out.println(input);
            }
            br.close();
            inputStreamReader.close();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

