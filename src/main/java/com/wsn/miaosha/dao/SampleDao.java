package com.wsn.miaosha.dao;

import com.wsn.miaosha.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 张澧枫
 * @date 2019/4/25 17:31
 **/
@Mapper
public interface SampleDao {

    @Select("select * from user where id = #{id}")
    public User selectUser(@Param("id")long id);

}
