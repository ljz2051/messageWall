package com.springapp.mvc.service.impl;

import com.springapp.mvc.dao.UserInfoMapper;
import com.springapp.mvc.entity.UserInfo;
import com.springapp.mvc.service.IMyMessageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MyMessageServiceImpl implements IMyMessageService{

    @Resource
    private UserInfoMapper userInfoMapper;

    public String StoreBackPhoto(String openid, MultipartFile file, String dirPath)throws Exception{
        String originalName = file.getOriginalFilename();
        String fileSuffix = originalName.substring(originalName.lastIndexOf('.') + 1).toLowerCase();
        if(!(fileSuffix.equals("jpg") || fileSuffix.equals("jpeg") || fileSuffix.equals("gif")
                || fileSuffix.equals("png") || fileSuffix.equals("bmp"))){
            return null;
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = dirPath + "/" + uuid + "." + fileSuffix;
        file.transferTo(new File(fileName));

        UserInfo userInfo = new UserInfo(openid);
        String fileRelativeUrl = "/static/img/" + uuid + "." + fileSuffix;
        userInfo.setBackphotourl(fileRelativeUrl);
       // System.out.println("openid:" + openid + "   fileName:" + userInfo.getBackphotourl());
        this.userInfoMapper.updateByPrimaryKeySelective(userInfo);
        return fileRelativeUrl;
    }

    public String selectBackPhoto(String openid) throws Exception{
        System.out.println("openid:" + openid);
        UserInfo userInfo  = this.userInfoMapper.selectByPrimaryKey(openid);
        return userInfo.getBackphotourl();
    }

    //上传用户的匿名头像，并修改用户信息
    public String uploadAnonymousPhoto(String openid, MultipartFile file, String dirPath) throws Exception{
        String originalName = file.getOriginalFilename();
        String fileSuffix = originalName.substring(originalName.lastIndexOf('.') + 1).toLowerCase();
        if(!(fileSuffix.equals("jpg") || fileSuffix.equals("jpeg") || fileSuffix.equals("gif")
                || fileSuffix.equals("png") || fileSuffix.equals("bmp"))){
            return null;
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = dirPath + "/" + uuid + "." + fileSuffix;
        file.transferTo(new File(fileName));

        String fileRelativeUrl = "/static/img/" + uuid + "." + fileSuffix;
        UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(openid);
        String fakeAvatarList = userInfo.getFakeavatarurllist();
        if(fakeAvatarList == null){
            fakeAvatarList = fileRelativeUrl;
        }
        else{
            fakeAvatarList = fakeAvatarList + " " + fileRelativeUrl;
        }
        userInfo.setFakeavatarurllist(fakeAvatarList);
        this.userInfoMapper.updateByPrimaryKeySelective(userInfo);
        return fakeAvatarList;
    }

    //查询openid对应用户的匿名头像列表
    public List<String>  selectAvatarList(String openid) throws Exception{
        UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(openid);
        String fakeAvatarList = userInfo.getFakeavatarurllist();
        if(fakeAvatarList == null){
            return null;
        }
        else{
            List<String> avatarList = Arrays.asList(fakeAvatarList.split(" "));
            return avatarList;
        }
    }
}
