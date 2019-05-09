package com.wsn.miaosha.service;

import com.wsn.miaosha.dao.GoodsDao;
import com.wsn.miaosha.pojo.Goods;
import com.wsn.miaosha.pojo.MiaoshaGoods;
import com.wsn.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张澧枫
 * @date 2019/5/7 20:39
 **/
@Service
public class GoodsService {

    @Resource
    private GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public void descStock(GoodsVo goodsVo) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goodsVo.getId());
        goodsDao.reduceStock(g);
    }
}
