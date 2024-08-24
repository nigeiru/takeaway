package com.example.mapper;


import com.example.entity.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
   List<Comment> selectAll(Comment comment);

    int insert(Comment comment);
    /**
     * 根据ID查询
     */
    Comment selectById(Integer id);

    int updateById(Comment comment);

    int deleteById(Integer id);

    @Select("select * from comment where business_id = #{businessId}")
    List<Comment> selectByBusinessId(Integer businessId);
}
