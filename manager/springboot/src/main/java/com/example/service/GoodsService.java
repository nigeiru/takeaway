package com.example.service;

import com.example.common.enums.OrderStatusEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Goods;
import com.example.entity.Orders;
import com.example.entity.OrdersItem;
import com.example.mapper.GoodsMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品信息业务处理
 **/
@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private BusinessService businessService;
    @Resource
    private OrdersService ordersService;
    @Resource
    private OrdersItemService ordersItemService;

    /**
     * 新增
     */
    public void add(Goods goods) {
        // 校验权限
        businessService.checkBusinessAuth();
        goods.setBusinessId(TokenUtils.getCurrentUser().getId());
        goodsMapper.insert(goods);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        goodsMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            goodsMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Goods goods) {
        goodsMapper.updateById(goods);
    }

    /**
     * 根据ID查询
     */
    public  Goods selectById(Integer id) {
        Goods goods=goodsMapper.selectById(id);
        wrapGoods(goods);
        return goods;
    }

    /**
     * 查询所有
     */
    public List<Goods> selectAll(Goods goods) {
        String role = TokenUtils.getCurrentUser().getRole();
        if (RoleEnum.BUSINESS.name().equals(role)){
            goods.setBusinessId(businessService.selectById(TokenUtils.getCurrentUser().getId()).getId());
        }
        List<Goods> goodsList = goodsMapper.selectAll(goods);
        for (Goods g : goodsList) {
            wrapGoods(g);
        }
        return goodsList;
    }

    /**
     * 分页查询
     */
    public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        String role = TokenUtils.getCurrentUser().getRole();
        if (RoleEnum.BUSINESS.name().equals(role)){
            goods.setBusinessId(businessService.selectById(TokenUtils.getCurrentUser().getId()).getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.selectAll(goods);
        for (Goods g : list) {
            wrapGoods(g);
        }
        return PageInfo.of(list);
    }
    /**
     * 设置商品额外信息
     */
    private Goods wrapGoods(Goods goods) {
        if (goods == null) {
            return null;
        }
        BigDecimal actualPrice = goods.getPrice().multiply(BigDecimal.valueOf(goods.getDiscount())).setScale(2, RoundingMode.UP);
        goods.setActualPrice(actualPrice);
        int saleCount = 0;
        List<OrdersItem> ordersItemList = ordersItemService.selectByGoodsId(goods.getId());
        List<OrdersItem> usageOrdersItemList = new ArrayList<>();
        for (OrdersItem ordersItem : ordersItemList) {
            // 筛选出有效订单的商品销售额
            Integer orderId = ordersItem.getOrderId();
            Orders orders = ordersService.selectById(orderId);
            if (orders == null) {
                continue;
            }
            if (OrderStatusEnum.NO_COMMENT.getValue().equals(orders.getStatus()) || OrderStatusEnum.DONE.getValue().equals(orders.getStatus())) {
                usageOrdersItemList.add(ordersItem);
            }
        }
        // 聚合函数查出订单的商品数量
        saleCount += usageOrdersItemList.stream().map(OrdersItem::getNum).reduce(Integer::sum).orElse(0);
        goods.setSaleCount(saleCount);
        return goods;
    }
}