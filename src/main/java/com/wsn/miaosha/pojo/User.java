package com.wsn.miaosha.pojo;

/**
 * @author 张澧枫
 * @date 2019/4/25 17:32
 **/
public class User {

    public int id ;
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
