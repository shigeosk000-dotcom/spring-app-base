package com.example.demo.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/todos/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ModelAttribute("currentUser")
    public User currentUser(HttpSession session) {
        return session != null ? (User) session.getAttribute("currentUser") : null;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.list());
        return "todos/user/userlist";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("isEdit", false);
        return "todos/user/useredit";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("userForm") UserForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirect) {
        if (form.getPassword() == null || form.getPassword().isBlank()) {
            binding.rejectValue("password", "NotBlank", "パスワードは必須です。");
        }
        if (binding.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "todos/user/useredit";
        }
        try {
            userService.create(form);
        } catch (IllegalArgumentException ex) {
            binding.reject("user.create", ex.getMessage());
            model.addAttribute("isEdit", false);
            return "todos/user/useredit";
        }
        redirect.addFlashAttribute("message", "ユーザーを登録しました。");
        return "redirect:/todos/user";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model, RedirectAttributes redirect) {
        User user = userService.findById(id);
        if (user == null) {
            redirect.addFlashAttribute("message", "指定されたユーザーは存在しません。");
            return "redirect:/todos/user";
        }
        model.addAttribute("userForm", UserForm.from(user));
        model.addAttribute("isEdit", true);
        return "todos/user/useredit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
            @Valid @ModelAttribute("userForm") UserForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirect) {
        form.setId(id);
        if (binding.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "todos/user/useredit";
        }
        try {
            userService.update(form);
        } catch (IllegalArgumentException ex) {
            binding.reject("user.update", ex.getMessage());
            model.addAttribute("isEdit", true);
            return "todos/user/useredit";
        }
        redirect.addFlashAttribute("message", "ユーザー情報を更新しました。");
        return "redirect:/todos/user";
    }

    @GetMapping("/{id}/check")
    public String check(@PathVariable String id, Model model, RedirectAttributes redirect) {
        User user = userService.findById(id);
        if (user == null) {
            redirect.addFlashAttribute("message", "指定されたユーザーは存在しません。");
            return "redirect:/todos/user";
        }
        model.addAttribute("user", user);
        return "todos/user/usercheck";
    }

    @PostMapping("/{id}/toggle")
    public String toggleStatus(@PathVariable String id, @RequestParam boolean status, RedirectAttributes redirect) {
        userService.changeStatus(id, status);
        redirect.addFlashAttribute("message", "ユーザーの状態を変更しました。");
        return "redirect:/todos/user";
    }
}