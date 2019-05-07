package com.wsn.miaosha.Exception;

import com.wsn.miaosha.pojo.CodeMsg;

/**
 * @author 张澧枫
 * @date 2019/5/6 19:30
 **/
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
