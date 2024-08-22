package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Collect;
import com.example.mapper.CollectMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品分类业务处理
 **/
@Service
public class CollectService {

    @Resource
    private CollectMapper collectMapper;
    @Resource
    private BusinessService businessService;

    /**
     * 新增
     */
    public void add(Collect collect) {
        businessService.checkBusinessAuth();
        collectMapper.insert(collect);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        businessService.checkBusinessAuth();
        collectMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        businessService.checkBusinessAuth();
        for (Integer id : ids) {
            collectMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Collect collect) {
        businessService.checkBusinessAuth();
        collectMapper.updateById(collect);
    }

    /**
     * 根据ID查询
     */
    public Collect selectById(Integer id) {
        return collectMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Collect> selectAll(Collect collect) {
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) {
            collect.setBusinessId(currentUser.getId());
        }
        return collectMapper.selectAll(collect);
    }

    /**
     * 分页查询
     */
    public PageInfo<Collect> selectPage(Collect collect, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) {
            collect.setBusinessId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Collect> list = collectMapper.selectAll(collect);
        return PageInfo.of(list);
    }

    public Collect selectByUserIdAndBusinessId(Integer userId, Integer businessId) {

        return collectMapper.selectByUserIdAndBusinessId(userId, businessId);

    }

    public void saveCollect(Collect collect) {
        Collect dbCollect = this.selectByUserIdAndBusinessId(collect.getUserId(), collect.getBusinessId());
        if (dbCollect != null) {  // 说明收藏过了
            this.deleteById(dbCollect.getId());  //删除收藏
        } else {
            // 新的收藏
            collect.setTime(DateUtil.now());
            this.add(collect);
        }
    }
}