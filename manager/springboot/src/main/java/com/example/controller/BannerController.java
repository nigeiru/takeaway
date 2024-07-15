package com.example.controller;

import com.example.common.Result;
import com.example.entity.Banner;
import com.example.service.BannerService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;
    /**
     * 添加商家
     */
    @PostMapping("/add")
    public Result add(@RequestBody Banner banner) {
        bannerService.add(banner);
        return Result.success();
    }

    /**
     * 修改商家
     */
    @PostMapping("/update")
    public Result update(@RequestBody Banner banner) {
        bannerService.updateById(banner);
        return Result.success();
    }

    /**
     * 根据单个ID查询商家
     */
    @GetMapping("/selectById")
    public Result selectAll(Integer id) {
        Banner banner = bannerService.selectById(id);
        return Result.success(banner);
    }

    /**
     * 删除商家
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody int id) {
        bannerService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        System.out.println("这是接收到的删除id列表"+ids);
        bannerService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 分页条件查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Banner banner,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {

        PageInfo<Banner> pageInfo = bannerService.selectPage(banner, pageNum, pageSize);
        return Result.success(pageInfo);
    }
    /**
     * 查询所有商家
     */
    @GetMapping("/selectAll")
    public Result selectAll(Banner banner) {
        List<Banner> list = bannerService.selectAll(banner);
        return Result.success(list);
    }



}
