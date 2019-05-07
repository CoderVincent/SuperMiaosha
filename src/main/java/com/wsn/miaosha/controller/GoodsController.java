package com.wsn.miaosha.controller;

import com.wsn.miaosha.pojo.MiaoshaUser;
import com.wsn.miaosha.service.GoodsService;
import com.wsn.miaosha.service.MiaoshaUserService;
import com.wsn.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 张澧枫
 * @date 2019/5/6 20:26
 **/
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toList(Model model,MiaoshaUser user){
        logger.info(user.toString());
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsVos);
        return "goods_list";
    }

}
