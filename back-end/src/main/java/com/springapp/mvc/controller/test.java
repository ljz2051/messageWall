package com.springapp.mvc.controller;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @Description:
 * @date : 2017/12/5
 **/
public class test {
    public static void main(String[] args)throws Exception{
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        for(String s : list){
            s = s + "a";
        }
        System.out.println(JSON.toJSONString(list));
    }
}
