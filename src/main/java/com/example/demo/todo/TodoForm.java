package com.example.demo.todo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoForm {
    private Long id;

    @NotBlank
    @Size(max = 10)
    private String userId;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private Priority priority = Priority.NORMAL;
    private boolean status;

    public Todo toTodo() {
        return Todo.builder()
            .id(id)
            .userId(userId)
            .title(title)
            .description(description)
            .dueDate(dueDate)
            .priority(priority == null ? Priority.NORMAL : priority)
            .status(status)
            .build();
    }

    public static TodoForm from(Todo todo) {
        if (todo == null) {
            return new TodoForm();
        }
        TodoForm form = new TodoForm();
        form.setId(todo.getId());
        form.setUserId(todo.getUserId());
        form.setTitle(todo.getTitle());
        form.setDescription(todo.getDescription());
        form.setDueDate(todo.getDueDate());
        form.setPriority(todo.getPriority());
        form.setStatus(todo.isStatus());
        return form;
    }
}
