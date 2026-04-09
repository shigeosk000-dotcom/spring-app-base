package com.example.demo.auth;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {
    @NotBlank
    private String id;

    @NotBlank
    private String password;
}
