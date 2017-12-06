package com.springapp.mvc.controller;

import com.springapp.mvc.dto.Result;
import com.springapp.mvc.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @Description:
 * @date : 2017/12/2
 **/
@Controller
@RequestMapping(value="/CommonController")
public class CommonController {
    @Autowired
    private ICommonService iCommonService;

    @RequestMapping(value="/getAccessToken")
    @ResponseBody
    public Result getAccessToken(){
        Result result = new Result();
        try{
            String accessToken = this.iCommonService.getAccessToken();
            if(accessToken == null){
                result.setSuccess(false);
                result.setMsg("获取失败");
            }else {
                result.setSuccess(true);
                result.setMsg("获取成功");
                result.setObject(accessToken);
            }
            return result;
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("获取失败" + e.getMessage());
            return result;
        }

    }
}
