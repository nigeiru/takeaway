package com.example.mapper;


import com.example.entity.User;

import java.util.List;

public interface UserMapper {
   List<User> selectAll(User user);

    int insert(User user);

    int updateById(User user);

    int deleteById(Integer id);

}
