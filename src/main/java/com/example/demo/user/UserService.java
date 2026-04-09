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
            throw new IllegalArgumentException("パスワードは必須です。");
        }
        mapper.insert(form.toUser());
    }

    public void update(UserForm form) {
        User existing = mapper.findById(form.getId());
        if (existing == null) {
            throw new IllegalArgumentException("指定されたユーザーは存在しません。");
        }
        String password = form.getPassword();
        if (password == null || password.isBlank()) {
            password = existing.getPassword();
        }
        User user = form.toUser();
        user.setPassword(password);
        mapper.update(user);
    }

    public User authenticate(String id, String password) {
        if (id == null || id.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("ログインIDとパスワードを入力してください。");
        }
        User user = mapper.findById(id);
        if (user == null || !user.isStatus()) {
            throw new IllegalArgumentException("このユーザーは利用できません。");
        }
        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("ログインIDまたはパスワードが正しくありません。");
        }
        return user;
    }

    public void changeStatus(String id, boolean status) {
        mapper.changeStatus(id, status);
    }

    public void delete(String id) {
        mapper.delete(id);
    }
}