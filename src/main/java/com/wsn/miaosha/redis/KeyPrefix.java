package com.wsn.miaosha.redis;

/**
 * @author 张澧枫
 * @date 2019/4/29 21:09
 **/
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}
