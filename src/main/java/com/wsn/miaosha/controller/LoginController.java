package com.wsn.miaosha.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.wsn.miaosha.pojo.CodeMsg;
import com.wsn.miaosha.pojo.Result;
import com.wsn.miaosha.redis.RedisService;
import com.wsn.miaosha.service.MiaoshaUserService;
import com.wsn.miaosha.service.UserService;
import com.wsn.miaosha.util.ValidatorUtil;
import com.wsn.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author 张澧枫
 * @date 2019/4/30 9:20
 **/
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        //参数校验
        boolean result = miaoshaUserService.login(response, loginVo);
        return Result.success(result);
    }
}
