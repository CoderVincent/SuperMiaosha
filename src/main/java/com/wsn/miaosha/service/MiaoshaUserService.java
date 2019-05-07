package com.wsn.miaosha.service;

import com.wsn.miaosha.Exception.GlobalException;
import com.wsn.miaosha.dao.MiaoshaUserDao;
import com.wsn.miaosha.pojo.CodeMsg;
import com.wsn.miaosha.pojo.MiaoshaUser;
import com.wsn.miaosha.redis.MiaoshaUserKey;
import com.wsn.miaosha.redis.RedisService;
import com.wsn.miaosha.util.MD5Util;
import com.wsn.miaosha.util.UUIDUtil;
import com.wsn.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 张澧枫
 * @date 2019/5/5 19:42
 **/
@Service
public class MiaoshaUserService{

    public static final String COOKIE_NAME_TOKEN = "token";

    @Resource
    private MiaoshaUserDao miaoshaUserDao;

    @Resource
    private RedisService redisService;

    public MiaoshaUser getById(long id){
        return miaoshaUserDao.getById(id);
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token){
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        if(user != null){
            addCookie(response, token, user);
        }
        return user;
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo){
        if(null == loginVo){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass,saltDB);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.generateUUID();
        addCookie(response,token,user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user){
        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
