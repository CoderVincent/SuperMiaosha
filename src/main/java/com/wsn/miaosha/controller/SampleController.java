package com.wsn.miaosha.controller;

import com.wsn.miaosha.pojo.Result;
import com.wsn.miaosha.pojo.User;
import com.wsn.miaosha.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author 张澧枫
 * @date 2019/4/25 17:00
 **/
@Controller
public class SampleController {

    @Resource
    public UserService userService;

    @RequestMapping("/sampleUse")
    public String getMessage(Model model){
        model.addAttribute("name","wangwu");
        return "hello";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public Result<User> getUser(){
        User user = userService.selectUser(1);
        return Result.success(user);
    }

}
