package com.example.controller;


import com.example.common.Result;
import com.example.entity.Orders;
import com.example.entity.OrdersDTO;
import com.example.service.OrdersService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private OrdersService ordersService;
    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody OrdersDTO orders) {
        ordersService.addOrder(orders);
        return Result.success();
    }
    /**
     * 添加商家
     */
    @PostMapping("/add")
    public Result add(@RequestBody Orders orders) {
        ordersService.add(orders);
        return Result.success();
    }

    /**
     * 修改商家
     */
    @PostMapping("/update")
    public Result update(@RequestBody Orders orders) {
        ordersService.updateById(orders);
        return Result.success();
    }

    /**
     * 根据单个ID查询商家
     */
    @GetMapping("/selectById")
    public Result selectAll(Integer id) {
        Orders orders = ordersService.selectById(id);
        return Result.success(orders);
    }

    /**
     * 删除商家
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody int id) {
        ordersService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        System.out.println("这是接收到的删除id列表"+ids);
        ordersService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 分页条件查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Orders orders,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {

        PageInfo<Orders> pageInfo = ordersService.selectPage(orders, pageNum, pageSize);
        return Result.success(pageInfo);
    }
    /**
     * 查询所有商家
     */
    @GetMapping("/selectAll")
    public Result selectAll(Orders orders) {
        System.out.println("接受到的参数"+orders);
         String status =orders.getStatus();

        List<Orders> list = ordersService.selectAll(orders);
        return Result.success(list);
    }



}
