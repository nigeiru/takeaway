package com.example.mapper;

import com.example.entity.Business;

import java.util.List;

public interface BusinessMapper {
   List<Business> selectAll(Business business);

    int insert(Business business);

    int updateById(Business business);

    int deleteById(Integer id);

}
