package com.springapp.mvc.service;

/**
 * @author
 * @Description:
 * @date : 2017/11/26
 **/
public interface ISensitivewordFilterService {
    String replaceSensitiveWord(String txt, int matchType, String replaceChar)throws Exception;
}
