package com.springapp.mvc.service.impl;

import com.springapp.mvc.entity.SensitiveWordInit;
import com.springapp.mvc.service.ISensitivewordFilterService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @Description:
 * @date : 2017/11/26
 **/
@Service
public class SensitivewordFilterServiceImpl implements ISensitivewordFilterService {
    private Map sensitiveWordMap = null;
    public static int minMatchType = 1;
    public static int maxMatchType = 2;

    public SensitivewordFilterServiceImpl(){
        this.sensitiveWordMap = SensitiveWordInit.initKeyWord();
    }

    //判断文字是否包含敏感字符
    public boolean isContainSensitiveWord(String txt, int matchType){
        boolean flag = false;
        for(int i = 0; i < txt.length(); i++){
            int matchFlag = this.checkSensitiveWord(txt, i, matchType);//判断是否包含敏感字符
            if(matchFlag > 0){
                flag = true;
            }
        }
        return flag;
    }

    //获取文字中的敏感词
    public Set<String> getSensitiveWord(String txt, int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();

        for(int i = 0; i < txt.length(); i++){
            int length = checkSensitiveWord(txt, i ,matchType);
            if(length > 0){
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;
            }
        }
        return sensitiveWordList;
    }

    //替换敏感字字符
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar)throws Exception{
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while(iterator.hasNext()){
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        return resultTxt;
    }

    //获取替换字符串
    private String getReplaceChars(String replaceChar, int length){
        String resultStr = replaceChar;
        for(int i = 1; i < length; i++){
            resultStr += replaceChar;
        }

        return resultStr;
    }

    //检查文字中是否包含敏感字符,如果存在，则返回敏感词字符的长度，不存在返回0
    public int checkSensitiveWord(String txt, int beginIndex, int matchType){
        boolean flag = false;
        int matchFlag = 0;
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length(); i++){
            word = txt.charAt(i);
            nowMap = (Map)nowMap.get(word);
            if(nowMap != null){
                matchFlag++;
                if("1".equals(nowMap.get("isEnd"))){ //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;
                    if(SensitivewordFilterServiceImpl.minMatchType == matchType){//最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            } else{     //不存在，直接返回
                break;
            }
        }
        if(matchFlag < 2 || !flag){         //长度必须大于等于1，为词
            matchFlag = 0;
        }
        return matchFlag;
    }

    public static void main(String[] args)throws Exception{
        SensitivewordFilterServiceImpl filter = new SensitivewordFilterServiceImpl();
        String str = "我们是哈哈，fs4324he嘿嘿嘿ffd";
        Set<String> set = filter.getSensitiveWord(str, 1);
        for(String s : set){
            System.out.println(s);
        }
        String result = filter.replaceSensitiveWord(str, 1, "*");
        System.out.println(result);
    }
}
