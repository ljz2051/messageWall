package com.springapp.mvc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springapp.mvc.entity.LoginMessage;
import com.springapp.mvc.service.ICommonService;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * @author
 * @Description:
 * @date : 2017/12/2
 **/
@Service
public class CommonServiceImpl implements ICommonService {
    private static String accessToken;
    private static Date expireTime;

    public String getAccessToken()throws Exception{
        String urlString = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ LoginMessage.appId + "&secret=" + LoginMessage.secret;
        if(accessToken != null && new Date().before(expireTime)){
            return accessToken;
        }
        try{
            URL url = new URL(urlString);
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setConnectTimeout(1000 * 30);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                sb.append(input);
            }
            bufferedReader.close();
            inputStreamReader.close();
            //返回{"access_token":"p5hyHWc5TfiyPgY8reckwSaux2IykOh65sq1wK_fdPCNVu17MSZv4PBL2GuenPElZwqxF4KmvftwDBBfVAiFbCPpXpluRvIYlS4zgr6F0HHcUo4LxbEjlBalaM14XmooITPaAFADOK","expires_in":7200}
            JSONObject response  = JSON.parseObject(sb.toString());
            if(response.containsKey("access_token")){
                int expires_in = response.getIntValue("expires_in");
                accessToken = response.getString("access_token");
                expireTime = new Date(new Date().getTime() + expires_in * 1000);
                return accessToken;
            }else{
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            throw  e;
        }
    }
}
