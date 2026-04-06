package com.example.demo.todo;

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
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService service;

    @ModelAttribute("priorities")
    public Priority[] priorities() {
        return Priority.values();
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("todoForm", new TodoForm());
        model.addAttribute("todos", service.list());
        return "todos";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("todoForm") TodoForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            model.addAttribute("todos", service.list());
            return "todos";
        }
        service.create(form.toTodo());
        redirect.addFlashAttribute("message", "ToDoを追加しました。");
        return "redirect:/todos";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Todo todo = service.findById(id);
        if (todo == null) {
            return "redirect:/todos";
        }
        model.addAttribute("todoForm", TodoForm.from(todo));
        model.addAttribute("todos", service.list());
        model.addAttribute("editingId", id);
        return "todos";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
            @Valid @ModelAttribute("todoForm") TodoForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            model.addAttribute("todos", service.list());
            model.addAttribute("editingId", id);
            return "todos";
        }
        Todo todo = form.toTodo();
        todo.setId(id);
        service.update(todo);
        redirect.addFlashAttribute("message", "ToDoを更新しました。");
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, @RequestParam boolean done) {
        service.changeDone(id, done);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("message", "ToDoを削除しました。");
        return "redirect:/todos";
    }
}
