package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.Business;
import com.example.entity.Collect;
import com.example.exception.CustomException;
import com.example.mapper.BusinessMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class BusinessService {
    @Resource
    BusinessMapper businessMapper;
    @Resource
    CollectService collectorService;
    /**
     * 修改密码
     */
    public void updatePassword(Account account) {
        Business dbBusiness = this.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbBusiness)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbBusiness.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbBusiness.setPassword(account.getNewPassword());
        this.updateById(dbBusiness);
    }

    /**
     * 商家登录
     */
    public Account login(Account account) {
        Account dbBusiness = selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbBusiness)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbBusiness.getPassword())) {   // 比较用户输入密码和数据库密码是否一致
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbBusiness.getId() + "-" + RoleEnum.BUSINESS.name();
        String token = TokenUtils.createToken(tokenData, dbBusiness.getPassword());
        dbBusiness.setToken(token);
        return dbBusiness;
    }

    /**
     * 根据账号查询
     */
    public Business selectByUsername(String username) {
        Business params = new Business();
        params.setUsername(username);
        List<Business> list = this.selectAll(params);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 根据ID 单个查询
     */
    public Business selectById(Integer id) {
        Business params = new Business();
        params.setId(id);
        List<Business> list = this.selectAll(params);
        Business business=list.size()==0 ? null : list.get(0);
        if (business!=null){
            Account currentAccount = TokenUtils.getCurrentUser();
           Collect collect = collectorService.selectByUserIdAndBusinessId(currentAccount.getId(),id);
            business.setIsCollect(collect!=null);
        }
        return business;
    }
    /**
     * 添加商家
     */
    public void add(Business business) {
        Business dbbusiness1 = this.selectById(business.getId());
        Business dbbusiness2 = this.selectByUsername(business.getUsername());
        //判断用户是否存在
        if (ObjectUtil.isEmpty(dbbusiness1)){
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        //根据当前的商家的用户名查询数据库，如果数据库存在跟当前存在的跟新的商家一样账号的数据，那么就报错
        //数据重复
        if (dbbusiness2 != null && ObjectUtil.isNotEmpty(dbbusiness2) && !Objects.equals(dbbusiness2.getId(), business.getId())) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        business.setRole(RoleEnum.BUSINESS.name());
        businessMapper.insert(business);
    }

    /**
     * 查找全部商家
     */
    public List<Business> selectAll(Business business) {
        return businessMapper.selectAll(business);
    }

    /**
     * 修改商家
     */
    public void updateById(Business business) {
        // 先根据id查询商家是否存在，商家不存在那就返回错误信息
        Business dbBusiness1 = selectById(business.getId());
        if (ObjectUtil.isEmpty(dbBusiness1)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Business dbBusiness2 = this.selectByUsername(business.getUsername());
        //  根据当前更新的商家的账号查询数据库  如果数据库存在跟当前更新商家一样账号的数据  那么当前的更新是不合法的  数据重复了
        if (ObjectUtil.isNotEmpty(dbBusiness2) && !Objects.equals(dbBusiness2.getId(), business.getId())) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        businessMapper.updateById(business);
    }

    /**
     * 单个删除
     */
    public void deleteById(Integer id) {
        businessMapper.deleteById(id);
    }
    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) { // 增强for循环遍历所有ID
            this.deleteById(id); // 调用删除单个ID的方法
        }
    }

    /**
     * 分页条件查询
     */
    public PageInfo<Business> selectPage(Business business, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Business> list = businessMapper.selectAll(business);
        return PageInfo.of(list);
    }
    /**
     * 商家注册
     */
    public void register(Account account) {
        Business business = new Business();
        BeanUtils.copyProperties(account, business);
        this.add(business);
    }

    /**
     * 检查商家的权限  看看是否可以新增数据
     */
    public void checkBusinessAuth() {
        Account currentUser = TokenUtils.getCurrentUser();  // 获取当前的用户信息
        if (RoleEnum.BUSINESS.name().equals(currentUser.getRole())) {   // 如果是商家 的话
            Business business = selectById(currentUser.getId());
            if (!"通过".equals(business.getStatus())) {   // 未审核通过的商家  不允许添加数据
                throw new CustomException(ResultCodeEnum.NO_AUTH);
            }
        }
    }
}