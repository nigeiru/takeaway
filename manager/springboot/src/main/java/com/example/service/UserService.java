package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Resource
    UserMapper userMapper;
    /**
     * 根据账号查询
     */
    public User selectByUsername(String username) {
        User params = new User();
        params.setUsername(username);
        List<User> list = this.selectAll(params);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 根据ID 单个查询
     */
    public User selectById(Integer id) {
        User params = new User();
        params.setId(id);
        List<User> list = this.selectAll(params);
        return list.isEmpty() ? null : list.get(0);
    }
    /**
     * 添加商家
     */
    public void add(User user) {
        User dbuser1 = this.selectById(user.getId());
        User dbuser2 = this.selectByUsername(user.getUsername());
        //判断用户是否存在
        if (ObjectUtil.isEmpty(dbuser1)){
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        //根据当前的商家的用户名查询数据库，如果数据库存在跟当前存在的跟新的商家一样账号的数据，那么就报错
        //数据重复
        if (dbuser2 != null && ObjectUtil.isNotEmpty(dbuser2) && !Objects.equals(dbuser2.getId(), user.getId())) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        user.setRole(RoleEnum.BUSINESS.name());
        userMapper.insert(user);
    }

    /**
     * 查找全部商家
     */
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    /**
     * 修改商家
     */
    public void updateById(User user) {
        // 先根据id查询商家是否存在，商家不存在那就返回错误信息
        User dbUser1 = selectById(user.getId());
        if (ObjectUtil.isEmpty(dbUser1)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        User dbUser2 = this.selectByUsername(user.getUsername());
        //  根据当前更新的商家的账号查询数据库  如果数据库存在跟当前更新商家一样账号的数据  那么当前的更新是不合法的  数据重复了
        if (ObjectUtil.isNotEmpty(dbUser2) && !Objects.equals(dbUser2.getId(), user.getId())) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        userMapper.updateById(user);
    }

    /**
     * 单个删除
     */
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
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
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }
}