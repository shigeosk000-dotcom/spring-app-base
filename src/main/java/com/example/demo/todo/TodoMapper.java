
package com.example.demo.todo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TodoMapper {
    List<Todo> findAll();

    List<Todo> findByUserId(@Param( userId) String userId);

    Todo findById(@Param( id) Long id);
    Todo findByIdAndUserId(@Param( id) Long id, @Param(userId) String userId);

    int insert(Todo todo);

    int update(Todo todo);

    int delete(@Param( id) Long id);

    int changeStatus(@Param( id) Long id, @Param(status) boolean status);
}
