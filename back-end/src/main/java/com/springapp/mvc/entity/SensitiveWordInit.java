package com.springapp.mvc.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author
 * @Description:
 * @date : 2017/11/26
 **/
public class SensitiveWordInit {
    private  static String ENCODING = "utf-8";
    //private static String filepath = "F:\\messageWall2.0\\sensitiveWord.txt";
    private  static String  classPath = SensitiveWordInit.class.getResource("/").getPath();
    private  static String filepath = classPath + "../../../resource/sensitiveWord.txt";
    @SuppressWarnings("rawtypes")
    private static  HashMap sensitiveWordMap;

    public static Map initKeyWord(){
        try{
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            //将敏感词库加入HashMap中
            addSensitiveWordToHashMap(keyWordSet);
        } catch (Exception e){
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

   //读取敏感词库中的内容，将内容添加到set集合中
    public static Set<String> readSensitiveWordFile()throws Exception{
        Set<String> set = null;
        File file  = new File(filepath);
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
        try{
            if(file.isFile() && file.exists()){
                set = new HashSet<String>();
                BufferedReader br = new BufferedReader(read);
                String word = null;
                while((word = br.readLine()) != null && word.length() > 0){
                    set.add(word.substring(0, word.length() - 1));
                }
            }
            else{
                throw new Exception("敏感词库不存在");
            }
        }catch (Exception e){
            throw e;
        } finally {
            if(read != null){
                read.close();
            }
        }
        return set;
    }
    //将敏感词库加入HashMap中
    public static void addSensitiveWordToHashMap(Set<String> keyWordSet){
        sensitiveWordMap = new HashMap(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String, String> newWordMap = null;
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for(int i = 0; i < key.length(); i++){
                char keyChar = key.charAt(i);
                Object wordMap = nowMap.get(keyChar);

                if(wordMap != null){ //如果存在该key，直接赋值
                    nowMap = (Map)wordMap;
                } else{   //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWordMap = new HashMap<String, String>();
                    newWordMap.put("isEnd","0");
                    nowMap.put(keyChar, newWordMap);
                    nowMap = newWordMap;
                }

                if( i == key.length() - 1){
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }
    public static void main(String args[])throws Exception{
        //System.out.println(new File("").getCanonicalPath());
        System.out.println(filepath);
    }
}
