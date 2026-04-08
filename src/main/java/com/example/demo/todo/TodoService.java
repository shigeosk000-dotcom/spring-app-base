package com.example.demo.todo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoMapper mapper;

    public List<Todo> list() {
        return mapper.findAll();
    }

    public List<Todo> listByUser(String userId) {
        return mapper.findByUserId(userId);
    }

    public Todo findById(Long id) {
        return mapper.findById(id);
    }

    public void create(Todo todo) {
        mapper.insert(todo);
    }

    @Transactional
    public void update(Todo todo) {
        mapper.update(todo);
    }

    public void delete(Long id) {
        mapper.delete(id);
    }

    public void changeStatus(Long id, boolean status) {
        mapper.changeStatus(id, status);
    }
}
