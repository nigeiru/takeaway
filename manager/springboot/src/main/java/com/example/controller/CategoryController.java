package com.example.controller;

import com.example.common.Result;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;
    /**
     * 添加分类
     */
    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        categoryService.add(category);
        return Result.success();
    }

    /**
     * 修改分类
     */
    @PostMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success();
    }

    /**
     * 根据单个ID查询分类
     */
    @GetMapping("/selectById")
    public Result selectAll(Integer id) {
        Category category = categoryService.selectById(id);
        return Result.success(category);
    }

    /**
     * 删除分类
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody int id) {
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        System.out.println("这是接收到的删除id列表"+ids);
        categoryService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 分页条件查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Category category,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Category> pageInfo = categoryService.selectPage(category, pageNum, pageSize);
        return Result.success(pageInfo);
    }



}
