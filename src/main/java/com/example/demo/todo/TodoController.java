package com.example.demo.todo;

import java.util.Objects;

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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.user.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/todos/task")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService service;
    private static final String FIND_BY_ID_QUERY_TEMPLATE = """
            SELECT t.task_id, t.user_id, u.name AS user_name, t.title, t.description, t.due_date, t.priority, t.status
            FROM todo t
            LEFT JOIN user_list u ON u.id = t.user_id
            WHERE t.task_id = %d
            """;

    @ModelAttribute("priorities")
    public Priority[] priorities() {
        return Priority.values();
    }

    @ModelAttribute("currentUser")
    public User currentUser(@SessionAttribute(name = "currentUser", required = false) User currentUser) {
        return currentUser;
    }

    @GetMapping
    public String redirectToList() {
        return "redirect:/todos/task/tasklist";
    }

    @GetMapping("/tasklist")
    public String list(Model model, @SessionAttribute(name = "currentUser", required = false) User currentUser) {
        if (currentUser == null) {
            return "redirect:/todos/login";
        }
        model.addAttribute("todos", service.listByUser(currentUser.getId()));
        return "todos/task/tasklist";
    }

    @GetMapping("/taskedit")
    public String createForm(Model model, @SessionAttribute(name = "currentUser", required = false) User currentUser) {
        if (currentUser == null) {
            return "redirect:/todos/login";
        }
        TodoForm todoForm = new TodoForm();
        todoForm.setUserId(currentUser.getId());
        model.addAttribute("todoForm", todoForm);
        model.addAttribute("editing", false);
        return "todos/task/taskedit";
    }

    @GetMapping("/taskedit/{id}")
    public String editForm(@PathVariable Long id,
            Model model,
            RedirectAttributes redirect,
            @SessionAttribute(name = "currentUser", required = false) User currentUser) {
        if (currentUser == null) {
            return "redirect:/todos/login";
        }
        Todo todo = service.findById(id);
        if (todo == null) {
            redirect.addFlashAttribute("message", "該当するタスクは存在しません。");
            return "redirect:/todos/task/tasklist";
        }
        if (!belongsToCurrentUser(todo, currentUser)) {
            addPermissionDeniedWithQuery(redirect, id);
            return "redirect:/todos/task/tasklist";
        }
        model.addAttribute("todoForm", TodoForm.from(todo));
        model.addAttribute("editing", true);
        return "todos/task/taskedit";
    }

    @PostMapping("/taskedit")
    public String create(@Valid @ModelAttribute("todoForm") TodoForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirect,
            @SessionAttribute(name = "currentUser", required = false) User currentUser) {
        if (currentUser == null) {
            return "redirect:/todos/login";
        }
        form.setUserId(currentUser.getId());
        if (binding.hasErrors()) {
            model.addAttribute("editing", false);
            return "todos/task/taskedit";
        }
        Todo saved = service.create(form.toTodo());
        redirect.addFlashAttribute("message", "タスクを登録しました。");
        return "redirect:/todos/task/taskcheck/" + saved.getId();
    }

    @PostMapping("/taskedit/{id}")
    public String update(@PathVariable Long id,
            @Valid @ModelAttribute("todoForm") TodoForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirect,
            @SessionAttribute(name = "currentUser", required = false) User currentUser) {
        if (currentUser == null) {
            return "redirect:/todos/login";
        }
        form.setUserId(currentUser.getId());
        if (binding.hasErrors()) {
            model.addAttribute("editing", true);
            return "todos/task/taskedit";
        }
        Todo todo = form.toTodo();
        todo.setId(id);
        Todo existing = service.findById(id);
        if (existing == null) {
            addPermissionDeniedWithQuery(redirect, id);
            return "redirect:/todos/task/tasklist";
        }
        if (!belongsToCurrentUser(existing, currentUser)) {
            addPermissionDeniedWithQuery(redirect, id);
            return "redirect:/todos/task/tasklist";
        }
        service.update(todo);
        redirect.addFlashAttribute("message", "タスクを更新しました。");
        return "redirect:/todos/task/taskcheck/" + id;
    }

    @GetMapping("/taskcheck/{id}")
    public String check(@PathVariable Long id,
            Model model,
            RedirectAttributes redirect,
            @SessionAttribute(name = "currentUser", required = false) User currentUser) {
        if (currentUser == null) {
            return "redirect:/todos/login";
        }
        Todo todo = service.findById(id);
        if (todo == null) {
            redirect.addFlashAttribute("message", "該当するタスクは存在しません。");
            return "redirect:/todos/task/tasklist";
        }
        if (!belongsToCurrentUser(todo, currentUser)) {
            addPermissionDeniedWithQuery(redirect, id);
            return "redirect:/todos/task/tasklist";
        }
        model.addAttribute("todo", todo);
        return "todos/task/taskcheck";
    }

    @PostMapping("/tasklist/{id}/toggle")
    public String toggle(@PathVariable Long id,
            @RequestParam boolean status,
            RedirectAttributes redirect,
            @SessionAttribute(name = "currentUser", required = false) User currentUser) {
        if (currentUser == null) {
            return "redirect:/todos/login";
        }
        Todo todo = service.findById(id);
        if (todo == null) {
            addPermissionDeniedWithQuery(redirect, id);
            return "redirect:/todos/task/tasklist";
        }
        if (!belongsToCurrentUser(todo, currentUser)) {
            addPermissionDeniedWithQuery(redirect, id);
            return "redirect:/todos/task/tasklist";
        }
        service.changeStatus(id, status);
        return "redirect:/todos/task/tasklist";
    }

    @PostMapping("/tasklist/{id}/delete")
    public String delete(@PathVariable Long id,
            RedirectAttributes redirect,
            @SessionAttribute(name = "currentUser", required = false) User currentUser) {
        if (currentUser == null) {
            return "redirect:/todos/login";
        }
        Todo todo = service.findById(id);
        if (todo == null) {
            addPermissionDeniedWithQuery(redirect, id);
            return "redirect:/todos/task/tasklist";
        }
        if (!belongsToCurrentUser(todo, currentUser)) {
            addPermissionDeniedWithQuery(redirect, id);
            return "redirect:/todos/task/tasklist";
        }
        service.delete(id);
        redirect.addFlashAttribute("message", "タスクを削除しました。");
        return "redirect:/todos/task/tasklist";
    }

    private boolean belongsToCurrentUser(Todo todo, User currentUser) {
        return todo != null && currentUser != null && Objects.equals(todo.getUserId(), currentUser.getId());
    }

    private void addPermissionDeniedWithQuery(RedirectAttributes redirect, Long id) {
        redirect.addFlashAttribute("message", "閲覧権限がありません。");
        redirect.addFlashAttribute("lastQuery", FIND_BY_ID_QUERY_TEMPLATE.formatted(id));
    }
}
