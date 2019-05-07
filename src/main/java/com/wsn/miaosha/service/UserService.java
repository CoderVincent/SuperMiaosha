package com.wsn.miaosha.service;

import com.wsn.miaosha.dao.SampleDao;
import com.wsn.miaosha.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 张澧枫
 * @date 2019/4/25 17:33
 **/
@Service
public class UserService {

    @Resource
    private SampleDao sampleDao;

    public User selectUser(long id){
        return sampleDao.selectUser(id);
    }

    public void login(){

    }
}
