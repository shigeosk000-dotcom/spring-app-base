package com.example.demo.user;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    public List<User> list() {
        return mapper.findAll();
    }

    public List<User> listActive() {
        return mapper.findActive();
    }

    public User findById(String id) {
        return mapper.findById(id);
    }

    public void create(UserForm form) {
        if (form.getPassword() == null || form.getPassword().isBlank()) {
            throw new IllegalArgumentException("パスワードを入力してください。");
        }
        mapper.insert(form.toUser());
    }

    public void update(UserForm form) {
        User existing = mapper.findById(form.getId());
        if (existing == null) {
            throw new IllegalArgumentException("指定されたユーザーが見つかりません。");
        }
        String password = form.getPassword();
        if (password == null || password.isBlank()) {
            password = existing.getPassword();
        }
        User user = form.toUser();
        user.setPassword(password);
        mapper.update(user);
    }

    public void changeStatus(String id, boolean status) {
        mapper.changeStatus(id, status);
    }

    public void delete(String id) {
        mapper.delete(id);
    }
}
