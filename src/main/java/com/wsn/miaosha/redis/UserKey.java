package com.wsn.miaosha.redis;

/**
 * @author 张澧枫
 * @date 2019/4/29 21:17
 **/
public class UserKey extends BasePrefix{

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
