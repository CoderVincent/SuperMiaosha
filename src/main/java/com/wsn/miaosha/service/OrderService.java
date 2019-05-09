package com.wsn.miaosha.service;

import com.wsn.miaosha.dao.OrderDao;
import com.wsn.miaosha.pojo.MiaoshaOrder;
import com.wsn.miaosha.pojo.MiaoshaUser;
import com.wsn.miaosha.pojo.OrderInfo;
import com.wsn.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 张澧枫
 * @date 2019/5/8 20:25
 **/
@Service
public class OrderService {

    @Resource
    public OrderDao orderDao;

    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(long userId,long goodsId){
        return orderDao.getMiaoshaOrderByUserIdAndGoodsId(userId,goodsId);
    }

    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());

        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
