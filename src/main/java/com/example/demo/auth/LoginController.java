package com.example.demo.auth;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.user.User;
import com.example.demo.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @ModelAttribute("loginForm")
    public LoginForm loginForm() {
        return new LoginForm();
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("message", "ログインID／パスワードを入力してください。");
        return "todos/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form,
            BindingResult binding,
            Model model,
            HttpSession session,
            RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            return "todos/login";
        }
        try {
            User user = userService.authenticate(form.getId(), form.getPassword());
            session.setAttribute("currentUser", user);
            String target = user.isAuthority() ? "/todos/user" : "/todos";
            return "redirect:" + target;
        } catch (IllegalArgumentException ex) {
            binding.reject("login", ex.getMessage());
            return "todos/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirect) {
        session.invalidate();
        redirect.addFlashAttribute("message", "ログアウトしました。");
        return "redirect:/todos/login";
    }
}
