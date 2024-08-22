package com.example.controller;

import com.example.common.Result;
import com.example.entity.Business;
import com.example.entity.Collect;
import com.example.service.BusinessService;
import com.example.service.CollectService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户表前端操作接口
 **/
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private CollectService collectService;
    @Resource
    private BusinessService businessService;
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Collect collect) {
        collectService.add(collect);
        return Result.success();
    }
    @PostMapping("/saveCollect")
    public Result saveCollect(@RequestBody Collect collect) {
        collectService.saveCollect(collect);
        return Result.success();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody int id) {
        collectService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        collectService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public Result updateById(@RequestBody Collect collect) {
        collectService.updateById(collect);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Collect collect = collectService.selectById(id);
        return Result.success(collect);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Collect collect ) {
        List<Collect> collects = collectService.selectAll(collect);
        for (Collect c:collects) {
           Business business= businessService.selectById(c.getBusinessId());
            c.setBusiness(business);

        }
        return Result.success(collects);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Collect collect,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Collect> page = collectService.selectPage(collect, pageNum, pageSize);
        return Result.success(page);
    }

}