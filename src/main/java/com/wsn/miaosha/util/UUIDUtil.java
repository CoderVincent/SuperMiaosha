package com.wsn.miaosha.util;

import java.util.UUID;

/**
 * @author 张澧枫
 * @date 2019/5/6 20:00
 **/
public class UUIDUtil {

    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
