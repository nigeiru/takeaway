package com.example.mapper;


import com.example.entity.Collect;

import java.util.List;

public interface CollectMapper {
   List<Collect> selectAll(Collect collect);
   Collect selectById(Integer id);

    int insert(Collect collect);

    int updateById(Collect collect);

    int deleteById(Integer id);

}
