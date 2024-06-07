package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Business;
import com.example.entity.Ids;
import com.example.exception.CustomException;
import com.example.service.BusinessService;
import com.github.pagehelper.PageInfo;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;

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
    @PutMapping("/update")
    public Result update(@RequestBody Business business) {
        businessService.updateById(business);
        return Result.success();
    }

    /**
     * 查询所有商家
     */
    @GetMapping("/selectAll")
    public Result selectAll(Business business,
                            @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Business> pageInfo = businessService.selectPage(business, pageNum, pageSize);
        return Result.success(pageInfo);
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
    public Result delete(@RequestBody Integer id) {
        businessService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestBody Ids ids) {
        businessService.deleteBatch(ids.getIds());
        return Result.success();
    }

}
