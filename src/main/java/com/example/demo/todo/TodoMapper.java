package com.example.demo.todo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TodoMapper {
    List<Todo> findAll();

    Todo findById(@Param("id") Long id);

    int insert(Todo todo);

    int update(Todo todo);

    int delete(@Param("id") Long id);

    int changeDone(@Param("id") Long id, @Param("done") boolean done);
}
