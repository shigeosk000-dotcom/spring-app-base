package com.example.demo.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    List<User> findAll();

    List<User> findActive();

    User findById(@Param("id") String id);

    int insert(User user);

    int update(User user);

    int changeStatus(@Param("id") String id, @Param("status") boolean status);

    int delete(@Param("id") String id);
}
