package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, @AuthenticationPrincipal User user) {
    task.setUser(user);
    Task createdTask = taskService.createTask(task);
    return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Task>> listTasks(@AuthenticationPrincipal User user) {
    List<Task> tasks = taskService.getTasksForUser(user);
    return new ResponseEntity<>(tasks, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task, @AuthenticationPrincipal User user) {
    task.setId(id);
    task.setUser(user);
    Optional<Task> updated = taskService.updateTask(task);
    if (updated.isPresent()) {
      return new ResponseEntity<>(updated.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id, @AuthenticationPrincipal User user) {
    List<Task> tasks = taskService.getTasksForUser(user);
    boolean owned = tasks.stream().anyMatch(t -> t.getId().equals(id));
    if (!owned) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    taskService.deleteTask(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

