package com.wsn.miaosha.service;

import com.wsn.miaosha.pojo.Goods;
import com.wsn.miaosha.pojo.MiaoshaUser;
import com.wsn.miaosha.pojo.OrderInfo;
import com.wsn.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 张澧枫
 * @date 2019/5/8 20:31
 **/
@Service
public class MiaoshaService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        goodsService.descStock(goodsVo);
        return orderService.createOrder(user,goodsVo);
    }
}
