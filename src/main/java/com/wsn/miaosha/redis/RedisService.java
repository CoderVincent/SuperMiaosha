package com.wsn.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wsn.miaosha.Config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张澧枫
 * @date 2019/4/29 19:23
 **/
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * get方法
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String value = jedis.get(realKey);
            T t = stringToBean(value,clazz);
            return t;
        }finally {
            closeJedisPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String val = beanToString(value);
            if(val == null || val.length() == 0)
                return false;
            String realKey = prefix.getPrefix() + key;
            jedis.set(realKey,val);
            return true;
        }finally {
            closeJedisPool(jedis);
        }
    }

    public <T> boolean setNXEX(final KeyPrefix prefix, final String key, final T req) {
        if(req == null){
            return false;
        }
        int expireSeconds = prefix.expireSeconds();
        if(expireSeconds <= 0) {
            throw new RuntimeException("[SET EX NX]必须设置超时时间");
        }
        String realKey = prefix.getPrefix() + key;
        String value = beanToString(req);
        Jedis jc = null;
        try {
            jc = jedisPool.getResource();
            String ret =  jc.set(realKey, value, "nx", "ex", expireSeconds);
            return "OK".equals(ret);
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeJedisPool(jc);
        }
    }

    /**
     * 判断key是否存在
     * */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.exists(realKey);
        }finally {
            closeJedisPool(jedis);
        }
    }

    /**
     * 删除
     * */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            long ret =  jedis.del(realKey);
            return ret > 0;
        }finally {
            closeJedisPool(jedis);
        }
    }

    /**
     * 增加值
     * */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.incr(realKey);
        }finally {
            closeJedisPool(jedis);
        }
    }

    /**
     * 减少值
     * */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            closeJedisPool(jedis);
        }
    }

    /**
     * 删除所有key
     * @param prefix
     * @return
     */
    public boolean delete(KeyPrefix prefix) {
        if(prefix == null) {
            return false;
        }
        List<String> keys = scanKeys(prefix.getPrefix());
        if(keys==null || keys.size() <= 0) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys.toArray(new String[0]));
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 扫描key
     * @param key
     * @return
     */
    public List<String> scanKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<String>();
            String cursor = "0";
            ScanParams sp = new ScanParams();
            sp.match("*"+key+"*");
            sp.count(100);
            do{
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if(result!=null && result.size() > 0){
                    keys.addAll(result);
                }
                //再处理cursor
                cursor = ret.getStringCursor();
            }while(!cursor.equals("0"));
            return keys;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 关闭连接池
     * @param jedis
     */
    public void closeJedisPool(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

    /**
     * 字符串转对象方法
     * @param val
     * @return
     */
    public <T> T stringToBean(String val, Class<T> clazz) {
        if(val == null || val.length() == 0 || clazz == null)
            return null;
        if(clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(val);
        }else if(clazz == String.class){
            return (T)val;
        }else if(clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(val);
        }else{
            return JSONObject.toJavaObject(JSON.parseObject(val),clazz);
        }
    }

    /**
     * 对象转字符串方法
     * @param value
     * @return
     */
    public <T> String beanToString(T value){
        if(value == null)
            return null;
        Class<?> clazz = value.getClass();
        if(clazz == int.class){
            return "" + value;
        }else if(clazz == String.class){
            return (String)value;
        }else if(clazz == long.class){
            return "" + value;
        }else{
            return JSONObject.toJSONString(value);
        }
    }

}
