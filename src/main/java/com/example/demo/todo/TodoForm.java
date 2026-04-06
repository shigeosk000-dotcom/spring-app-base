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
    @Size(max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private Priority priority = Priority.NORMAL;
    private boolean done;

    public Todo toTodo() {
        return Todo.builder()
            .id(id)
            .title(title)
            .description(description)
            .dueDate(dueDate)
            .priority(priority == null ? Priority.NORMAL : priority)
            .done(done)
            .build();
    }

    public static TodoForm from(Todo todo) {
        if (todo == null) {
            return new TodoForm();
        }
        TodoForm form = new TodoForm();
        form.setId(todo.getId());
        form.setTitle(todo.getTitle());
        form.setDescription(todo.getDescription());
        form.setDueDate(todo.getDueDate());
        form.setPriority(todo.getPriority());
        form.setDone(todo.isDone());
        return form;
    }
}

