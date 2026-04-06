package com.example.demo.todo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
	private Long id;
	private String title;
	private String description;
	private LocalDate dueDate;
	private Priority priority;
	private boolean done;
}
