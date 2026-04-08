package com.example.demo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserForm {
    @NotBlank
    @Size(max = 10)
    private String id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @Size(max = 64)
    private String password;

    private boolean authority;
    private boolean status = true;

    public User toUser() {
        return User.builder()
            .id(id)
            .name(name)
            .password(password)
            .authority(authority)
            .status(status)
            .build();
    }

    public static UserForm from(User user) {
        if (user == null) {
            return new UserForm();
        }
        UserForm form = new UserForm();
        form.setId(user.getId());
        form.setName(user.getName());
        form.setAuthority(user.isAuthority());
        form.setStatus(user.isStatus());
        return form;
    }
}
