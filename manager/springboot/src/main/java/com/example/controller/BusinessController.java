package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Business;

import com.example.exception.CustomException;
import com.example.service.BusinessService;
import com.github.pagehelper.PageInfo;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;
    /**
     * 添加商家
     */
    @PostMapping("/add")
    public Result add(@RequestBody Business business) {
        if (ObjectUtils.isEmpty(business.getUsername())||ObjectUtils.isEmpty(business.getPassword()))
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        businessService.add(business);
        return Result.success();
    }

    /**
     * 修改商家
     */
    @PostMapping("/update")
    public Result update(@RequestBody Business business) {
        businessService.updateById(business);
        return Result.success();
    }

    /**
     * 根据单个ID查询商家
     */
    @GetMapping("/selectById")
    public Result selectAll(Integer id) {
        Business business = businessService.selectById(id);
        return Result.success(business);
    }

    /**
     * 删除商家
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody int id) {
        businessService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        System.out.println("这是接收到的删除id列表"+ids);
        businessService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 分页条件查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Business business,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Business> pageInfo = businessService.selectPage(business, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
