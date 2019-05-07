package com.wsn.miaosha;

import com.wsn.miaosha.pojo.User;
import com.wsn.miaosha.redis.RedisService;
import com.wsn.miaosha.redis.UserKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaApplicationTests {

    @Autowired
    public RedisService redisService;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testRedis(){
        User user = redisService.get(UserKey.getById,""+1,User.class);
        System.out.println(user);
    }

    @Test
    public void testKeyPrefix(){
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById,""+1,user);
    }

}
