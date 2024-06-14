package com.example.service;


import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {
    @Resource
    CategoryMapper categoryMapper;
    BusinessService BusinessService;


    /**
     * 根据ID 单个查询
     */
    public Category selectById(Integer id) {
        Category params = new Category();
        params.setId(id);
        List<Category> list = this.selectAll(params);
        return list.isEmpty() ? null : list.get(0);
    }
    /**
     * 添加分类
     */
    public void add(Category category) {
        BusinessService.checkBusinessAuth();
        Account currentAccount = TokenUtils.getCurrentUser();
        if (RoleEnum.BUSINESS.name().equals(currentAccount.getRole())){
            category.setBusinessId(currentAccount.getId());
        }
        categoryMapper.insert(category);
    }

    /**
     * 查找全部分类
     */
    public List<Category> selectAll(Category category)
    {
        Account currentAccount = TokenUtils.getCurrentUser();
        if (currentAccount.getRole().equals("BUSINESS")){
            category.setBusinessId(currentAccount.getId());
        }
        return categoryMapper.selectAll(category);
    }

    /**
     * 修改分类
     */
    public void updateById(Category category) {
        BusinessService.checkBusinessAuth();
        categoryMapper.updateById(category);
    }

    /**
     * 单个删除
     */
    public void deleteById(Integer id) {
        BusinessService.checkBusinessAuth();
        categoryMapper.deleteById(id);
    }
    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        BusinessService.checkBusinessAuth();
        for (Integer id : ids) { // 增强for循环遍历所有ID
            this.deleteById(id); // 调用删除单个ID的方法
        }
    }

    /**
     * 分页条件查询
     */
    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Account currentAccount = TokenUtils.getCurrentUser();
        if (currentAccount.getRole().equals("BUSINESS")){
            category.setBusinessId(currentAccount.getId());
        }
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }
    
}