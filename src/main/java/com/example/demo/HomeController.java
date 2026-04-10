package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String root() {
        return "redirect:/todo";
    }

    @GetMapping("/todo")
    public String todo() {
        return "redirect:/todos/task/tasklist";
    }
}
