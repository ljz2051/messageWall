package com.springapp.mvc.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMyMessageService {
    String StoreBackPhoto(String openid, MultipartFile file, String dirPath)throws Exception;
    String selectBackPhoto(String openid) throws Exception;
    String uploadAnonymousPhoto(String openid, MultipartFile file, String dirPath) throws Exception;
    public List<String> selectAvatarList(String openid) throws Exception;
}
