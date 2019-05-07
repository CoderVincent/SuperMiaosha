package com.wsn.miaosha.dao;

import com.wsn.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 张澧枫
 * @date 2019/5/7 20:39
 **/
@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.id = g.id")
    public List<GoodsVo> listGoodsVo();

}
