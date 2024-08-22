package com.example.mapper;


import com.example.entity.Collect;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectMapper {
   List<Collect> selectAll(Collect collect);
   Collect selectById(Integer id);

    int insert(Collect collect);

    int updateById(Collect collect);

    int deleteById(Integer id);
    @Select("select * from collect where user_id = #{userId} and business_id = #{businessId}")
    Collect selectByUserIdAndBusinessId(@Param("userId")Integer userId, @Param("businessId")Integer businessId);
}
