package com.wsn.miaosha.vo;

import javax.validation.constraints.NotNull;

import com.wsn.miaosha.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

/**
 * @author 张澧枫
 * @date 2019/4/30 9:29
 **/
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min=32)
    private String password;

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "LoginVo [mobile=" + mobile + ", password=" + password + "]";
    }
}

