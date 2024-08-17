package com.example.controller;

import com.example.common.Result;
import com.example.entity.AmountDTO;
import com.example.entity.Cart;
import com.example.entity.Goods;
import com.example.service.CartService;
import com.example.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车前端操作接口
 **/
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;
    private GoodsService GoodsService;
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Cart cart) {
        cartService.add(cart);
        return Result.success();
    }
    /**
     * 计算总价
     */
    @GetMapping("/calc")
    public Result calc(@RequestParam Integer userId, @RequestParam Integer businessId) {
        System.out.println("接受的用户id: " + userId + " 商家id: " + businessId);
        AmountDTO amountDTO = cartService.calc(userId, businessId);
        return Result.success(amountDTO);
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/deleteByGoods/{goodsId}/{userId}")
    public Result deleteByGoods(@PathVariable Integer goodsId, @PathVariable Integer userId) {
        cartService.deleteByGoods(goodsId, userId);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        cartService.deleteBatch(ids);
        return Result.success();
    }

    @DeleteMapping("/deleteByBusiness/{businessID}/{userId}")
    public Result deleteByBusiness(@PathVariable Integer businessID, @PathVariable Integer userId) {
        cartService.deleteByBusiness(businessID, userId);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Cart cart) {
        cartService.updateById(cart);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Cart cart = cartService.selectById(id);
        return Result.success(cart);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Cart cart ) {
        List<Cart> list = cartService.selectAll(cart);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Cart cart,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Cart> page = cartService.selectPage(cart, pageNum, pageSize);
        return Result.success(page);
    }

}