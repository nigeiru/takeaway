package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.OrderStatusEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Comment;
import com.example.entity.Orders;
import com.example.mapper.CommentMapper;
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
public class CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private BusinessService businessService;
    @Resource
    private OrdersService orderService;

    /**
     * 新增
     */
    public void add(Comment comment) {
        businessService.checkBusinessAuth();
        comment.setTime(DateUtil.now());
        Orders orders = orderService.selectById(comment.getOrderId());
        if (orders!=null){
            comment.setBusinessId(orders.getBusinessId());
            orders.setStatus(OrderStatusEnum.DONE.getValue());
            orderService.updateById(orders);

        }
        Account currentUser = TokenUtils.getCurrentUser();
        comment.setUserId(currentUser.getId());
        comment.setBusinessId(TokenUtils.getCurrentUser().getId());
        commentMapper.insert(comment);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        businessService.checkBusinessAuth();
        commentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        businessService.checkBusinessAuth();
        for (Integer id : ids) {
            commentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Comment comment) {
        businessService.checkBusinessAuth();
        commentMapper.updateById(comment);
    }

    /**
     * 根据ID查询
     */
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Comment> selectAll(Comment comment) {
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) {
            comment.setBusinessId(currentUser.getId());
        }
        return commentMapper.selectAll(comment);
    }

    /**
     * 分页查询
     */
    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) {
            comment.setBusinessId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }

    public List<Comment> selectByBusinessId(Integer businessId) {
        return commentMapper.selectByBusinessId(businessId);

    }
}